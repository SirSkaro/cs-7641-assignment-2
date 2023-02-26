package edu.gatech.bchurchill.assignment2.part1;

import dist.DiscreteDependencyTree;
import dist.DiscretePermutationDistribution;
import dist.Distribution;
import edu.gatech.bchurchill.assignment2.BaseProblemSet;
import edu.gatech.bchurchill.assignment2.ConvergenceSpec;
import edu.gatech.bchurchill.assignment2.SolutionStatistics;
import opt.*;
import opt.ga.*;
import opt.prob.GenericProbabilisticOptimizationProblem;
import opt.prob.MIMIC;
import opt.prob.ProbabilisticOptimizationProblem;

/**
 * Docs used:
 *  https://github.com/pushkar/ABAGAIL/blob/master/src/opt/test/NQueensTest.java
 */
public class NQueensProblemSet extends BaseProblemSet {

    private int numberQueens;
    private EvaluationFunction fitnessFunction;

    public NQueensProblemSet(int numberQueens) {
        this.numberQueens = numberQueens;
        this.fitnessFunction = new BetterNQueensFitnessFunction();
    }

    @Override
    public String getName() {
        return String.format("%d-Queens", numberQueens);
    }

    @Override
    public SolutionStatistics randomizedHillClimbing() {
        NeighborFunction neighborFunction = new SwapNeighbor();
        Distribution distribution = new DiscretePermutationDistribution(numberQueens);
        HillClimbingProblem problem = new GenericHillClimbingProblem(fitnessFunction, distribution, neighborFunction);
        OptimizationAlgorithm algorithm = new RandomizedHillClimbing(problem);

        return solve(algorithm, fitnessFunction);
    }

    @Override
    public SolutionStatistics simulatedAnnealing() {
        double temperature = 10_000;
        double decay = 0.95;
        ConvergenceSpec convergenceSpec = new ConvergenceSpec(50_000, 3000, 0.0);

        NeighborFunction neighborFunction = new SwapNeighbor();
        Distribution distribution = new DiscretePermutationDistribution(numberQueens);
        HillClimbingProblem problem = new GenericHillClimbingProblem(fitnessFunction, distribution, neighborFunction);
        OptimizationAlgorithm algorithm = new SimulatedAnnealing(temperature, decay, problem);

        return solve(algorithm, fitnessFunction, convergenceSpec);
    }

    @Override
    public SolutionStatistics geneticAlgorithm() {
        

        Distribution distribution = new DiscretePermutationDistribution(numberQueens);
        MutationFunction mutationFunction = new SwapMutation();
        CrossoverFunction crossoverFunction = new SingleCrossOver();
        GeneticAlgorithmProblem problem = new GenericGeneticAlgorithmProblem(fitnessFunction, distribution, mutationFunction, crossoverFunction);
        StandardGeneticAlgorithm algorithm = new StandardGeneticAlgorithm(200, 0, 10, problem);

        return solve(algorithm, fitnessFunction);
    }

    @Override
    public SolutionStatistics mimic() {
        Distribution distribution = new DiscretePermutationDistribution(numberQueens);
        Distribution dependencyTreeDistribution = new DiscreteDependencyTree(.1);
        ProbabilisticOptimizationProblem problem = new GenericProbabilisticOptimizationProblem(fitnessFunction, distribution, dependencyTreeDistribution);
        MIMIC algorithm = new MIMIC(200, 10, problem);

        return solve(algorithm, fitnessFunction);
    }
}
