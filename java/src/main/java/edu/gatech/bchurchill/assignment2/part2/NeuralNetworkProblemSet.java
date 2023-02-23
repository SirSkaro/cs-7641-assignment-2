package edu.gatech.bchurchill.assignment2.part2;

import edu.gatech.bchurchill.assignment2.Assignment;
import edu.gatech.bchurchill.assignment2.part1.SolutionStatistics;
import func.nn.OptNetworkBuilder;
import func.nn.feedfwd.FeedForwardNetwork;

public class NeuralNetworkProblemSet extends Assignment.BaseProblemSet {
    int outputLayerSize = 26;

    public NeuralNetworkProblemSet() {

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

        return null;
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
        return new OptNetworkBuilder()
                .withActivationFunction(new ScaledExponentialLinearUnit())
                .withLayers(new int[] {10, 10, 10, 10, 10, 10, outputLayerSize})
                .withIterations(10_000)
                //.withDataSet()
                ;
    }
}
