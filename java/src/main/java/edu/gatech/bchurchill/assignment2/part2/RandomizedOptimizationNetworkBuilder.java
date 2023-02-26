package edu.gatech.bchurchill.assignment2.part2;

import func.nn.NetworkBuilder;
import func.nn.activation.ActivationFunction;
import func.nn.activation.DifferentiableActivationFunction;
import func.nn.activation.HyperbolicTangentSigmoid;
import func.nn.feedfwd.FeedForwardNetwork;
import func.nn.feedfwd.FeedForwardNeuralNetworkFactory;
import opt.OptimizationAlgorithm;
import opt.example.NeuralNetworkOptimizationProblem;
import shared.*;

import javax.naming.OperationNotSupportedException;
import java.util.function.Function;

public class RandomizedOptimizationNetworkBuilder implements NetworkBuilder {
    private int[] layers;
    private FeedForwardNetwork network;
    private DifferentiableActivationFunction activationFunction;
    private ErrorMeasure errorMeasure;
    private DataSet set;
    private DataSet testSet;
    private int iterations = 1000;
    private OptimizationAlgorithm algorithm;

    public RandomizedOptimizationNetworkBuilder(DifferentiableActivationFunction activationFunction, int[] layers) {
        this.layers = layers;
        this.activationFunction = activationFunction;
        errorMeasure = new SumOfSquaresError();

        network = new FeedForwardNeuralNetworkFactory().createClassificationNetwork(this.layers, this.activationFunction);
    }

    @Override
    public RandomizedOptimizationNetworkBuilder withActivationFunction(ActivationFunction activationFunction) {
        return this;
    }

    @Override
    public RandomizedOptimizationNetworkBuilder withLayers(int[] layers) {
        return this;
    }

    @Override
    public RandomizedOptimizationNetworkBuilder withDataSet(DataSet train, DataSet test) {
        this.set = train;
        this.testSet = test;
        return this;
    }

    public RandomizedOptimizationNetworkBuilder withIterations(int iterations) {
        this.iterations = iterations;
        return this;
    }

    @Override
    public RandomizedOptimizationNetworkBuilder withErrorMeasure(ErrorMeasure errorMeasure) {
        this.errorMeasure = errorMeasure;
        return this;
    }

    public RandomizedOptimizationNetworkBuilder withAlgorithm(Function<NeuralNetworkOptimizationProblem, OptimizationAlgorithm> algorithmSupplier) {
        NeuralNetworkOptimizationProblem problem = new NeuralNetworkOptimizationProblem(this.set, this.network, this.errorMeasure);
        this.algorithm = algorithmSupplier.apply(problem);
        return this;
    }

    public FeedForwardNetwork train() {
        for(int iteration = 0; iteration < this.iterations; iteration++) {
            algorithm.train();
            double trainScore = this.calculateTrainScore();
            double testScore = this.calculateTestScore();
            System.out.println(String.format("Iteration %d | Train score: %f | Test score: %f", iteration + 1, trainScore, testScore));
        }

        return this.network;
    }

    private double calculateTrainScore() {
        double error = 0.0;

        for(int j = 0; j < this.set.getInstances().length; ++j) {
            this.network.setInputValues(this.set.getInstances()[j].getData());
            this.network.run();
            Instance output = this.set.getInstances()[j].getLabel();
            Instance example = new Instance(this.network.getOutputValues());
            example.setLabel(new Instance(this.network.getOutputValues()));
            error += this.errorMeasure.value(output, example);
        }

        error /= this.set.getInstances().length;

        return error;
    }

    private double calculateTestScore() {
        double error = 0.0;

        for(int i = 0; i < testSet.size(); ++i) {
            Instance pattern = testSet.get(i);
            network.setInputValues(pattern.getData());
            network.run();
            Instance output = new Instance(network.getOutputValues());
            error += errorMeasure.value(output, pattern);
        }

        return error / (double)testSet.size();
    }

}
