package edu.gatech.bchurchill.assignment2.part1;

import dist.DiscreteDependencyTree;
import dist.DiscretePermutationDistribution;
import dist.Distribution;
import edu.gatech.bchurchill.assignment2.BaseProblemSet;
import edu.gatech.bchurchill.assignment2.ConvergenceSpec;
import edu.gatech.bchurchill.assignment2.SolutionStatistics;
import opt.*;
import opt.example.TravelingSalesmanCrossOver;
import opt.example.TravelingSalesmanEvaluationFunction;
import opt.example.TravelingSalesmanRouteEvaluationFunction;
import opt.ga.*;
import opt.prob.GenericProbabilisticOptimizationProblem;
import opt.prob.MIMIC;
import opt.prob.ProbabilisticOptimizationProblem;

import java.util.Random;

/**
 * Docs used:
 *  https://github.com/pushkar/ABAGAIL/blob/master/src/opt/test/TravelingSalesmanTest.java
 */
public class TravelingSalesmanProblemSet extends BaseProblemSet {

    private int cityCount;
    private EvaluationFunction fitnessFunction;
    private double[][] points;

    public TravelingSalesmanProblemSet(int cityCount) {
        var random = new Random();
        this.cityCount = cityCount;

        points = new double[cityCount][2];
        for (int i = 0; i < points.length; i++) {
            points[i][0] = random.nextDouble();
            points[i][1] = random.nextDouble();
        }

        fitnessFunction = new TravelingSalesmanRouteEvaluationFunction(points);
    }

    @Override
    public String getName() {
        return String.format("Traveling Salesman with %d cities", cityCount);
    }

    @Override
    public SolutionStatistics randomizedHillClimbing() {
        NeighborFunction neighborFunction = new SwapNeighbor();
        Distribution distribution = new DiscretePermutationDistribution(cityCount);
        HillClimbingProblem problem = new GenericHillClimbingProblem(fitnessFunction, distribution, neighborFunction);
        OptimizationAlgorithm algorithm = new RandomizedHillClimbing(problem);

        return solve(algorithm, fitnessFunction);
    }

    @Override
    public SolutionStatistics simulatedAnnealing() {
        double temperature = 1E25;
        double decay = 0.99;
        ConvergenceSpec convergenceSpec = new ConvergenceSpec(50_000, 3000, 0.0);

        NeighborFunction neighborFunction = new SwapNeighbor();
        Distribution distribution = new DiscretePermutationDistribution(cityCount);
        HillClimbingProblem problem = new GenericHillClimbingProblem(fitnessFunction, distribution, neighborFunction);
        OptimizationAlgorithm algorithm = new SimulatedAnnealing(temperature, decay, problem);

        return solve(algorithm, fitnessFunction, convergenceSpec);
    }

    @Override
    public SolutionStatistics geneticAlgorithm() {
        int populationSize = 2000;
        int populationToMate = (int)(populationSize * 1.0);
        int populationToMutate = (int)(populationSize * 0.25);
        ConvergenceSpec convergenceSpec = new ConvergenceSpec(50_000, 35, 0.0005);

        Distribution distribution = new DiscretePermutationDistribution(cityCount);
        MutationFunction mutationFunction = new SwapMutation();
        CrossoverFunction crossoverFunction = new TravelingSalesmanCrossOver((TravelingSalesmanEvaluationFunction) fitnessFunction);
        GeneticAlgorithmProblem problem = new GenericGeneticAlgorithmProblem(fitnessFunction, distribution, mutationFunction, crossoverFunction);
        StandardGeneticAlgorithm algorithm = new StandardGeneticAlgorithm(populationSize, populationToMate, populationToMutate, problem);

        return solve(algorithm, fitnessFunction, convergenceSpec);
    }

    @Override
    public SolutionStatistics mimic() {
        int samples = 5000;
        int toKeep = (int)(samples * 0.2);
        ConvergenceSpec convergenceSpec = new ConvergenceSpec(50_000, 50, 0.0);

        Distribution distribution = new DiscretePermutationDistribution(cityCount);
        Distribution dependencyTreeDistribution = new DiscreteDependencyTree(.1);
        ProbabilisticOptimizationProblem problem = new GenericProbabilisticOptimizationProblem(fitnessFunction, distribution, dependencyTreeDistribution);
        MIMIC algorithm = new MIMIC(samples, toKeep, problem);

        return solve(algorithm, fitnessFunction, convergenceSpec);
    }
}
