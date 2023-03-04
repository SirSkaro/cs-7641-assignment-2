package edu.gatech.bchurchill.assignment2.part2;

import edu.gatech.bchurchill.assignment2.ConvergenceIterationTrainer;
import func.nn.NetworkBuilder;
import func.nn.activation.ActivationFunction;
import func.nn.activation.DifferentiableActivationFunction;
import func.nn.feedfwd.FeedForwardNetwork;
import func.nn.feedfwd.FeedForwardNeuralNetworkFactory;
import opt.OptimizationAlgorithm;
import shared.*;

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
    private ConvergenceIterationTrainer trainer;

    public RandomizedOptimizationNetworkBuilder(DifferentiableActivationFunction activationFunction, int[] layers) {
        this.layers = layers;
        this.activationFunction = activationFunction;
        this.errorMeasure = new SumOfSquaresError();

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

    public RandomizedOptimizationNetworkBuilder withAlgorithm(Function<CustomNeuralNetworkOptimizationProblem, OptimizationAlgorithm> algorithmSupplier) {
        CustomNeuralNetworkOptimizationProblem problem = new CustomNeuralNetworkOptimizationProblem(this.set, this.network, this.errorMeasure);
        this.algorithm = algorithmSupplier.apply(problem);
        return this;
    }

    public RandomizedOptimizationNetworkBuilder withTrainer(Function<OptimizationAlgorithm, ConvergenceIterationTrainer> trainerSupplier) {
        this.trainer = trainerSupplier.apply(this.algorithm);
        return this;
    }

    public FeedForwardNetwork train() {
        if(trainer == null) {
            trainer = new ConvergenceIterationTrainer(algorithm, 20, iterations, 0.0);
        }

        trainer.train();
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

    public int getIterations() {
        return trainer.getIterations();
    }
}
