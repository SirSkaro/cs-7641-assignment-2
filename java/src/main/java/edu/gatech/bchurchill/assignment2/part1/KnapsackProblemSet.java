package edu.gatech.bchurchill.assignment2.part1;

import dist.DiscreteDependencyTree;
import dist.DiscreteUniformDistribution;
import dist.Distribution;
import opt.*;
import opt.example.KnapsackEvaluationFunction;
import opt.ga.*;
import opt.prob.GenericProbabilisticOptimizationProblem;
import opt.prob.MIMIC;
import opt.prob.ProbabilisticOptimizationProblem;

import java.util.Arrays;
import java.util.Random;

public class KnapsackProblemSet extends BaseProblemSet {
    private static final int COPIES_EACH = 4;
    private static final double MAX_VALUE_PER_ELEMENT = 50;
    private static final double MAX_WEIGHT_PER_ELEMENT = 50;

    private int itemCount;
    private EvaluationFunction fitnessFunction;
    private int[] ranges;

    public KnapsackProblemSet(int itemCount) {
        var random = new Random();
        this.itemCount = itemCount;
        ranges = new int[itemCount];
        Arrays.fill(ranges, COPIES_EACH + 1);

        int[] copies = new int[itemCount];
        Arrays.fill(copies, COPIES_EACH);
        double[] values = new double[itemCount];
        double[] weights = new double[itemCount];
        for (int i = 0; i < itemCount; i++) {
            values[i] = random.nextDouble() * MAX_VALUE_PER_ELEMENT;
            weights[i] = random.nextDouble() * MAX_WEIGHT_PER_ELEMENT;
        }
        
        double maxSumOfWeights = MAX_WEIGHT_PER_ELEMENT * itemCount * COPIES_EACH * .4;
        this.fitnessFunction = new KnapsackEvaluationFunction(values, weights, maxSumOfWeights, copies);
    }

    @Override
    public String getName() {
        return String.format("Knapsack w/ %d items", this.itemCount);
    }

    @Override
    public SolutionStatistics randomizedHillClimbing() {
        NeighborFunction neighborFunction = new DiscreteChangeOneNeighbor(ranges);
        Distribution distribution = new DiscreteUniformDistribution(ranges);
        HillClimbingProblem problem = new GenericHillClimbingProblem(fitnessFunction, distribution, neighborFunction);
        OptimizationAlgorithm algorithm = new RandomizedHillClimbing(problem);

        return solve(algorithm, fitnessFunction);
    }

    @Override
    public SolutionStatistics simulatedAnnealing() {
        NeighborFunction neighborFunction = new DiscreteChangeOneNeighbor(ranges);
        Distribution distribution = new DiscreteUniformDistribution(ranges);
        HillClimbingProblem problem = new GenericHillClimbingProblem(fitnessFunction, distribution, neighborFunction);
        OptimizationAlgorithm algorithm = new SimulatedAnnealing(100, .95, problem);

        return solve(algorithm, fitnessFunction);
    }

    @Override
    public SolutionStatistics geneticAlgorithm() {
        Distribution distribution = new DiscreteUniformDistribution(ranges);
        MutationFunction mutationFunction = new DiscreteChangeOneMutation(ranges);
        CrossoverFunction crossoverFunction = new UniformCrossOver();
        GeneticAlgorithmProblem problem = new GenericGeneticAlgorithmProblem(fitnessFunction, distribution, mutationFunction, crossoverFunction);
        StandardGeneticAlgorithm algorithm = new StandardGeneticAlgorithm(200, 150, 25, problem);

        return solve(algorithm, fitnessFunction);
    }

    @Override
    public SolutionStatistics mimic() {
        Distribution distribution = new DiscreteUniformDistribution(ranges);
        Distribution dependencyTreeDistribution = new DiscreteDependencyTree(.1, ranges);
        ProbabilisticOptimizationProblem problem = new GenericProbabilisticOptimizationProblem(fitnessFunction, distribution, dependencyTreeDistribution);
        MIMIC algorithm = new MIMIC(200, 100, problem);

        return solve(algorithm, fitnessFunction);
    }
}
