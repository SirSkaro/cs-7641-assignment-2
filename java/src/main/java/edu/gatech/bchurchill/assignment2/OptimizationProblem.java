package edu.gatech.bchurchill.assignment2;

public interface OptimizationProblem {
    String getName();
    void randomizedHillClimbing();
    void simulatedAnnealing();
    void geneticAlgorithm();
    void mimic();
}
