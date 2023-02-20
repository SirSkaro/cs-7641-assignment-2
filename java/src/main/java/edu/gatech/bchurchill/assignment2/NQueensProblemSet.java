package edu.gatech.bchurchill.assignment2;

import dist.DiscretePermutationDistribution;
import dist.Distribution;
import opt.*;
import opt.ga.NQueensFitnessFunction;
import shared.FixedIterationTrainer;
import shared.Instance;

public class NQueensProblemSet implements OptimizationProblemSet {

    private int numberQueens;
    private EvaluationFunction fitnessFunction;

    public NQueensProblemSet(int numberQueens) {
        this.numberQueens = numberQueens;
        this.fitnessFunction = new NQueensFitnessFunction();
    }

    @Override
    public String getName() {
        return String.format("%d-Queens", numberQueens);
    }

    @Override
    public void randomizedHillClimbing() {
        NeighborFunction neighborFunction = new SwapNeighbor();
        Distribution distribution = new DiscretePermutationDistribution(numberQueens);
        HillClimbingProblem problem = new GenericHillClimbingProblem(fitnessFunction, distribution, neighborFunction);
        OptimizationAlgorithm algorithm = new RandomizedHillClimbing(problem);

        long startTime = System.currentTimeMillis();
        FixedIterationTrainer fit = new FixedIterationTrainer(algorithm, 100);
        fit.train();
        System.out.println("\tTime : "+ (System.currentTimeMillis() - startTime) + "ms");

        Instance solution = algorithm.getOptimal();
        System.out.println("\tsolution: " + solution.getData());
        System.out.println("\tscore: " + fitnessFunction.value(solution));
    }

    @Override
    public void simulatedAnnealing() {

    }

    @Override
    public void geneticAlgorithm() {

    }

    @Override
    public void mimic() {

    }
}
