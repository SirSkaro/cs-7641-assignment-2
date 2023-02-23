package edu.gatech.bchurchill.assignment2;

import edu.gatech.bchurchill.assignment2.part1.*;
import opt.EvaluationFunction;
import opt.OptimizationAlgorithm;
import shared.Instance;

import java.util.List;

public class Assignment {

    public static void main(String[] args) {
        List<OptimizationProblemSet> problems = List.of(
            new NQueensProblemSet(8),
            new KnapsackProblemSet(8),
            new SixPeaksProblemSet(10),
            new TravelingSalesmanProblemSet(8)
        );

        for(var problem: problems) {
            System.out.println("Problem: " + problem.getName());
            System.out.println("\tRandomized Hill Climbing");
            System.out.println("\t\t"+problem.randomizedHillClimbing());
            System.out.println("\tSimulated Annealing");
            System.out.println("\t\t"+problem.simulatedAnnealing());
            System.out.println("\tGenetic Algorithm");
            System.out.println("\t\t"+problem.geneticAlgorithm());
            System.out.println("\tMIMIC");
            System.out.println("\t\t"+problem.mimic());
        }
    }

    public abstract static class BaseProblemSet implements OptimizationProblemSet {
        protected SolutionStatistics solve(OptimizationAlgorithm algorithm, EvaluationFunction fitnessFunction) {
            long startTime = System.currentTimeMillis();
            ConvergenceIterationTrainer trainer = new ConvergenceIterationTrainer(algorithm, 20, 50_000);
            trainer.train();
            long trainTime = System.currentTimeMillis() - startTime;

            Instance solution = algorithm.getOptimal();
            int iterations = trainer.getIterations();
            double score = fitnessFunction.value(solution);
            return new SolutionStatistics(solution, trainTime, iterations, score);
        }

    }
}
