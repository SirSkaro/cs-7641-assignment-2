package edu.gatech.bchurchill.assignment2;

import dist.DiscreteDependencyTree;
import dist.DiscretePermutationDistribution;
import dist.Distribution;
import opt.*;
import opt.example.TravelingSalesmanCrossOver;
import opt.example.TravelingSalesmanEvaluationFunction;
import opt.example.TravelingSalesmanRouteEvaluationFunction;
import opt.ga.*;
import opt.prob.GenericProbabilisticOptimizationProblem;
import opt.prob.MIMIC;
import opt.prob.ProbabilisticOptimizationProblem;

import java.util.Random;

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
        NeighborFunction neighborFunction = new SwapNeighbor();
        Distribution distribution = new DiscretePermutationDistribution(cityCount);
        HillClimbingProblem problem = new GenericHillClimbingProblem(fitnessFunction, distribution, neighborFunction);
        OptimizationAlgorithm algorithm = new SimulatedAnnealing(1E12, .95, problem);

        return solve(algorithm, fitnessFunction);
    }

    @Override
    public SolutionStatistics geneticAlgorithm() {
        Distribution distribution = new DiscretePermutationDistribution(cityCount);
        MutationFunction mutationFunction = new SwapMutation();
        CrossoverFunction crossoverFunction = new TravelingSalesmanCrossOver((TravelingSalesmanEvaluationFunction) fitnessFunction);
        GeneticAlgorithmProblem problem = new GenericGeneticAlgorithmProblem(fitnessFunction, distribution, mutationFunction, crossoverFunction);
        StandardGeneticAlgorithm algorithm = new StandardGeneticAlgorithm(200, 150, 20, problem);

        return solve(algorithm, fitnessFunction);
    }

    @Override
    public SolutionStatistics mimic() {
        Distribution distribution = new DiscretePermutationDistribution(cityCount);
        Distribution dependencyTreeDistribution = new DiscreteDependencyTree(.1);
        ProbabilisticOptimizationProblem problem = new GenericProbabilisticOptimizationProblem(fitnessFunction, distribution, dependencyTreeDistribution);
        MIMIC algorithm = new MIMIC(200, 100, problem);

        return solve(algorithm, fitnessFunction);
    }
}
