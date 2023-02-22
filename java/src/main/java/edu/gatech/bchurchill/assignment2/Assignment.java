package edu.gatech.bchurchill.assignment2;

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

}
