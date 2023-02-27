package edu.gatech.bchurchill.assignment2.part1;

import dist.DiscreteDependencyTree;
import dist.DiscreteUniformDistribution;
import dist.Distribution;
import edu.gatech.bchurchill.assignment2.BaseProblemSet;
import edu.gatech.bchurchill.assignment2.ConvergenceSpec;
import edu.gatech.bchurchill.assignment2.SolutionStatistics;
import opt.*;
import opt.example.FourPeaksEvaluationFunction;
import opt.ga.*;
import opt.prob.GenericProbabilisticOptimizationProblem;
import opt.prob.MIMIC;
import opt.prob.ProbabilisticOptimizationProblem;

import java.util.Arrays;

/**
 * Docs used:
 *  https://github.com/pushkar/ABAGAIL/blob/master/src/opt/test/SixPeaksTest.java
 */
public class FourPeaksProblemSet extends BaseProblemSet {

    private EvaluationFunction fitnessFunction;
    private int[] ranges;

    public FourPeaksProblemSet(int N) {
        int T = N / 5;
        this.fitnessFunction = new BetterFourPeaksEvaluationFunction(T);

        ranges = new int[N];
        Arrays.fill(ranges, 2);
    }

    @Override
    public String getName() {
        return "Four Peaks";
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
        double temperature = 1E11;
        double decay = 0.95;
        ConvergenceSpec convergenceSpec = new ConvergenceSpec(50_000, 3000, 0.0);

        NeighborFunction neighborFunction = new DiscreteChangeOneNeighbor(ranges);
        Distribution distribution = new DiscreteUniformDistribution(ranges);
        HillClimbingProblem problem = new GenericHillClimbingProblem(fitnessFunction, distribution, neighborFunction);
        OptimizationAlgorithm algorithm = new SimulatedAnnealing(temperature, decay, problem);

        return solve(algorithm, fitnessFunction, convergenceSpec);
    }

    @Override
    public SolutionStatistics geneticAlgorithm() {
        int populationSize = 1000;
        int populationToMate = (int)(populationSize * 1.0);
        int populationToMutate = (int)(populationSize * 0.1);
        ConvergenceSpec convergenceSpec = new ConvergenceSpec(50_000, 10, 0.0);

        Distribution distribution = new DiscreteUniformDistribution(ranges);
        MutationFunction mutationFunction = new DiscreteChangeOneMutation(ranges);
        CrossoverFunction crossoverFunction = new SingleCrossOver();
        GeneticAlgorithmProblem problem = new GenericGeneticAlgorithmProblem(fitnessFunction, distribution, mutationFunction, crossoverFunction);
        StandardGeneticAlgorithm algorithm = new StandardGeneticAlgorithm(populationSize, populationToMate, populationToMutate, problem);

        return solve(algorithm, fitnessFunction, convergenceSpec);
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
