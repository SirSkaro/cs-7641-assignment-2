package edu.gatech.bchurchill.assignment2.part2;

import dist.Distribution;
import func.nn.NeuralNetwork;
import opt.ContinuousAddOneNeighbor;
import opt.EvaluationFunction;
import opt.HillClimbingProblem;
import opt.NeighborFunction;
import opt.example.NeuralNetworkWeightDistribution;
import opt.ga.*;
import shared.DataSet;
import shared.ErrorMeasure;
import shared.Instance;

public class CustomNeuralNetworkOptimizationProblem implements HillClimbingProblem, GeneticAlgorithmProblem {

    private EvaluationFunction eval;
    private CrossoverFunction crossover;
    private NeighborFunction neighbor;
    private MutationFunction mutate;
    private Distribution dist;

    public CustomNeuralNetworkOptimizationProblem(DataSet examples, NeuralNetwork network, ErrorMeasure measure) {
        this.eval = new ParallelNeuralNetworkEvaluationFunction(network, examples, measure);
        this.crossover = new UniformCrossOver();
        this.neighbor = new ContinuousAddOneNeighbor();
        this.mutate = new ContinuousAddOneMutation();
        this.dist = new NeuralNetworkWeightDistribution(network.getLinks().size());
    }

    @Override
    public Instance neighbor(Instance instance) {
        return neighbor.neighbor(instance);
    }

    @Override
    public Instance mate(Instance parent1, Instance parent2) {
        return this.crossover.mate(parent1, parent2);
    }

    @Override
    public void mutate(Instance instance) {
        this.mutate.mutate(instance);
    }

    @Override
    public double value(Instance instance) {
        return this.eval.value(instance);
    }

    @Override
    public Instance random() {
        return this.dist.sample(null);
    }

    private class ScoreTuple {
        public Instance instance;
        public double score;

        ScoreTuple(Instance instance) {
            this.instance = instance;
            this.score = eval.value(instance);
        }
    }
}
