package edu.gatech.bchurchill.assignment2;

public interface OptimizationProblemSet {
    String getName();
    SolutionStatistics randomizedHillClimbing();
    SolutionStatistics simulatedAnnealing();
    SolutionStatistics geneticAlgorithm();
    SolutionStatistics mimic();
}
