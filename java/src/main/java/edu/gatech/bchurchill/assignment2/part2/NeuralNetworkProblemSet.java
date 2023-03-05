package edu.gatech.bchurchill.assignment2.part2;

import edu.gatech.bchurchill.assignment2.BaseProblemSet;
import edu.gatech.bchurchill.assignment2.ConvergenceIterationTrainer;
import edu.gatech.bchurchill.assignment2.SolutionStatistics;
import func.nn.activation.DifferentiableActivationFunction;
import func.nn.feedfwd.FeedForwardNetwork;
import opt.RandomizedHillClimbing;
import opt.SimulatedAnnealing;
import opt.ga.StandardGeneticAlgorithm;
import shared.DataSet;
import shared.Instance;
import shared.filt.TestTrainSplitFilter;

/**
 * Docs used:
 *  https://github.com/pushkar/ABAGAIL
 *  https://github.com/pushkar/ABAGAIL/blob/master/src/opt/test/AbaloneTest.java
 */
public class NeuralNetworkProblemSet extends BaseProblemSet {
    private int outputLayerSize;
    private int inputLayerSize;
    private DataSet trainingSet;
    private DataSet testSet;
    private int iterations;

    public NeuralNetworkProblemSet(DataSet dataSet, int percentTrain, int iterations) {
        TestTrainSplitFilter testTrainSplit = new TestTrainSplitFilter(percentTrain);
        testTrainSplit.filter(dataSet);
        this.trainingSet = testTrainSplit.getTrainingSet();
        this.testSet = testTrainSplit.getTestingSet();
        this.outputLayerSize = dataSet.getLabelDataSet().getDescription().getAttributeCount();
        this.inputLayerSize = dataSet.getDescription().getAttributeCount();
        this.iterations = iterations;
    }

    @Override
    public String getName() {
        return "Neural Network Weight Training";
    }

    @Override
    public SolutionStatistics randomizedHillClimbing() {
        var network = baseBuilder()
                .withAlgorithm(RandomizedHillClimbing::new)
                .withTrainer(algorithm -> new ConvergenceIterationTrainer(algorithm, 1000, iterations, 0.0000000005));

        return solve(network);
    }

    @Override
    public SolutionStatistics simulatedAnnealing() {
        var initialTemperature = 1;
        var temperatureDecay = 0.85;
        var network = baseBuilder()
                .withAlgorithm(problem -> new SimulatedAnnealing(initialTemperature, temperatureDecay, problem))
                .withTrainer(algorithm -> new ConvergenceIterationTrainer(algorithm, 1000, iterations, 0.0000000005));

        return solve(network);
    }

    @Override
    public SolutionStatistics geneticAlgorithm() {
        var populationSize = 150;
        int populationToMate = (int)(populationSize * 0.50);
        int populationToMutate = (int)(populationSize * 0.90);
        var network = baseBuilder()
                .withAlgorithm(problem -> new StandardGeneticAlgorithm(populationSize, populationToMate, populationToMutate, problem))
                .withTrainer(algorithm -> new ConvergenceIterationTrainer(algorithm, 200, 200, 0.0000005));

        return solve(network);
    }

    @Override
    public SolutionStatistics mimic() {
        throw new UnsupportedOperationException("Not part of the assignment");
    }

    private RandomizedOptimizationNetworkBuilder baseBuilder() {
        DifferentiableActivationFunction activationFunction = new ScaledExponentialLinearUnit();
        int[] layers = new int[] {inputLayerSize, 10, 10, 10, 10, 10, 10, outputLayerSize};
        return new RandomizedOptimizationNetworkBuilder(activationFunction, layers)
                .withIterations(iterations)
                .withDataSet(trainingSet, testSet);
    }

    private SolutionStatistics solve(RandomizedOptimizationNetworkBuilder builder) {
        long startTime = System.currentTimeMillis();
        var network = builder.train();
        long trainTime = System.currentTimeMillis() - startTime;

        double testError = calculateTestError(network);
        return new SolutionStatistics(null, trainTime, builder.getIterations(), testError);
    }

    private double calculateTestError(FeedForwardNetwork network) {
        double correctClassifications = 0.0;

        for(Instance testSample : testSet) {
            network.setInputValues(testSample.getData());
            network.run();
            int networkClassification = network.getOutputValues().argMax();
            int actualClassification = testSample.getLabel().getData().argMax();

            if(actualClassification == networkClassification) {
                correctClassifications++;
            }
        }

        return 1.0 - (correctClassifications / testSet.size());
    }
}
