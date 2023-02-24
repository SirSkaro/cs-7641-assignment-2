package edu.gatech.bchurchill.assignment2;

import edu.gatech.bchurchill.assignment2.part1.SolutionStatistics;

public interface OptimizationProblemSet {
    String getName();
    SolutionStatistics randomizedHillClimbing();
    SolutionStatistics simulatedAnnealing();
    SolutionStatistics geneticAlgorithm();
    SolutionStatistics mimic();
}
