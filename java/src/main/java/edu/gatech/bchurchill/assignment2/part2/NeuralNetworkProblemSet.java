package edu.gatech.bchurchill.assignment2.part2;

import edu.gatech.bchurchill.assignment2.BaseProblemSet;
import edu.gatech.bchurchill.assignment2.part1.SolutionStatistics;
import func.nn.OptNetworkBuilder;
import func.nn.feedfwd.FeedForwardNetwork;
import shared.DataSet;
import shared.filt.TestTrainSplitFilter;

public class NeuralNetworkProblemSet extends BaseProblemSet {
    private int outputLayerSize;
    private DataSet data;
    private int percentTrain;
    private int iterations;

    public NeuralNetworkProblemSet(DataSet dataSet, int percentTrain, int iterations) {
        this.data = dataSet;
        this.percentTrain = percentTrain;
        this.outputLayerSize = data.getLabelDataSet().getDescription().getAttributeCount();
        this.iterations = iterations;
    }

    @Override
    public String getName() {
        return "Neural Network Weight Training";
    }

    @Override
    public SolutionStatistics randomizedHillClimbing() {
        FeedForwardNetwork network = baseBuilder()
                .withRHC()
                .train();

        return null; // TODO get statistics
    }

    @Override
    public SolutionStatistics simulatedAnnealing() {
        return null;
    }

    @Override
    public SolutionStatistics geneticAlgorithm() {
        return null;
    }

    @Override
    public SolutionStatistics mimic() {
        return null;
    }

    private OptNetworkBuilder baseBuilder() {
        TestTrainSplitFilter testTrainSplit = new TestTrainSplitFilter(percentTrain);
        testTrainSplit.filter(data);
        var train = testTrainSplit.getTrainingSet();
        var test= testTrainSplit.getTestingSet();

        return new OptNetworkBuilder()
                .withActivationFunction(new ScaledExponentialLinearUnit())
                .withLayers(new int[] {10, 10, 10, 10, 10, 10, outputLayerSize})
                .withIterations(iterations)
                .withDataSet(train, test);
    }
}
