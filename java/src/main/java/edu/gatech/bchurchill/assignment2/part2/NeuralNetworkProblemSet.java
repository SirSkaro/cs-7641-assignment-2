package edu.gatech.bchurchill.assignment2.part2;

import edu.gatech.bchurchill.assignment2.BaseProblemSet;
import edu.gatech.bchurchill.assignment2.part1.SolutionStatistics;
import func.nn.OptNetworkBuilder;
import func.nn.feedfwd.FeedForwardNetwork;
import shared.DataSet;
import shared.GradientErrorMeasure;
import shared.Instance;
import shared.SumOfSquaresError;
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
                .withRHC();

        return solve(network);
    }

    @Override
    public SolutionStatistics simulatedAnnealing() {
        var network = baseBuilder()
                .withSA(1E11, .975);

        return solve(network);
    }

    @Override
    public SolutionStatistics geneticAlgorithm() {
        var network = baseBuilder()
                .withGA(200, 100, 10);

        return solve(network);
    }

    @Override
    public SolutionStatistics mimic() {
        throw new UnsupportedOperationException("Not part of the assignment");
    }

    private OptNetworkBuilder baseBuilder() {
        return new OptNetworkBuilder()
                .withActivationFunction(new ScaledExponentialLinearUnit())
                .withLayers(new int[] {inputLayerSize, 10, 10, 10, 10, 10, 10, outputLayerSize})
                .withIterations(iterations)
                .withDataSet(trainingSet, testSet);
    }

    private SolutionStatistics solve(OptNetworkBuilder builder) {
        long startTime = System.currentTimeMillis();
        var network = builder.train();
        long trainTime = System.currentTimeMillis() - startTime;

        double score = score(network);
        return new SolutionStatistics(null, trainTime, iterations, score);
    }

    private double score(FeedForwardNetwork network) {
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
