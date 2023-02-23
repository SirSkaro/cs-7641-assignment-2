package edu.gatech.bchurchill.assignment2.part1;

import dist.DiscreteDependencyTree;
import dist.DiscreteUniformDistribution;
import dist.Distribution;
import opt.*;
import opt.example.SixPeaksEvaluationFunction;
import opt.ga.*;
import opt.prob.GenericProbabilisticOptimizationProblem;
import opt.prob.MIMIC;
import opt.prob.ProbabilisticOptimizationProblem;

import java.util.Arrays;

public class SixPeaksProblemSet extends BaseProblemSet {

    private EvaluationFunction fitnessFunction;
    private int[] ranges;

    public SixPeaksProblemSet(int N) {
        int T = N / 5;
        this.fitnessFunction = new SixPeaksEvaluationFunction(T);

        ranges = new int[N];
        Arrays.fill(ranges, 2);
    }

    @Override
    public String getName() {
        return "Six Peaks";
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
        OptimizationAlgorithm algorithm = new SimulatedAnnealing(1E11, .95, problem);

        return solve(algorithm, fitnessFunction);
    }

    @Override
    public SolutionStatistics geneticAlgorithm() {
        Distribution distribution = new DiscreteUniformDistribution(ranges);
        MutationFunction mutationFunction = new DiscreteChangeOneMutation(ranges);
        CrossoverFunction crossoverFunction = new SingleCrossOver();
        GeneticAlgorithmProblem problem = new GenericGeneticAlgorithmProblem(fitnessFunction, distribution, mutationFunction, crossoverFunction);
        StandardGeneticAlgorithm algorithm = new StandardGeneticAlgorithm(200, 100, 10, problem);

        return solve(algorithm, fitnessFunction);
    }

    @Override
    public SolutionStatistics mimic() {
        Distribution distribution = new DiscreteUniformDistribution(ranges);
        Distribution dependencyTreeDistribution = new DiscreteDependencyTree(.1, ranges);
        ProbabilisticOptimizationProblem problem = new GenericProbabilisticOptimizationProblem(fitnessFunction, distribution, dependencyTreeDistribution);
        MIMIC algorithm = new MIMIC(200, 20, problem);

        return solve(algorithm, fitnessFunction);
    }
}
