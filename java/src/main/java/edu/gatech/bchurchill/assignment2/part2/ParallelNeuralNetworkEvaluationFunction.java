package edu.gatech.bchurchill.assignment2.part2;

import func.nn.NeuralNetwork;
import opt.EvaluationFunction;
import shared.DataSet;
import shared.ErrorMeasure;
import shared.Instance;
import util.linalg.Vector;

import java.util.stream.Stream;

public class ParallelNeuralNetworkEvaluationFunction implements EvaluationFunction {
    private NeuralNetwork network;
    private DataSet examples;
    private ErrorMeasure measure;

    public ParallelNeuralNetworkEvaluationFunction(NeuralNetwork network, DataSet examples, ErrorMeasure measure) {
        this.network = network;
        this.examples = examples;
        this.measure = measure;
    }

    public double value(Instance d) {
        Vector weights = d.getData();
        this.network.setWeights(weights);

        double error = Stream.of(examples.getInstances())
                .parallel()
                .mapToDouble(this::errorForInstance)
                .sum();

        return 1.0 / error;
    }

    private double errorForInstance(Instance instance) {
        this.network.setInputValues(instance.getData());
        this.network.run();
        return this.measure.value(new Instance(this.network.getOutputValues()), instance);
    }
}
