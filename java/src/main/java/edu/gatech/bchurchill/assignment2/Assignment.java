package edu.gatech.bchurchill.assignment2;

import edu.gatech.bchurchill.assignment2.part1.*;
import edu.gatech.bchurchill.assignment2.part2.LetterDataSetReader;
import edu.gatech.bchurchill.assignment2.part2.NeuralNetworkProblemSet;
import shared.filt.DataSetFilter;
import shared.filt.RandomOrderFilter;

import java.util.List;

public class Assignment {

    public static void main(String[] args) throws Exception {
        String mode = null;
        try {
            mode = args[0];
        } catch(ArrayIndexOutOfBoundsException e) {
            printIncorrectUsageMessage();
        }

        if(mode.equals("part1")) {
            List<OptimizationProblemSet> problems = List.of(
//                    new NQueensProblemSet(150),
//                    new KnapsackProblemSet(40)
                    new FourPeaksProblemSet(100)
//                    new TravelingSalesmanProblemSet(20)
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
        } else if(mode.equals("part2")) {
            List<DataSetFilter> filters = List.of(new RandomOrderFilter());
            var dataset = new LetterDataSetReader(filters).read();
            int percentTraining = 85;
            int iterations = 10;
            var problem = new NeuralNetworkProblemSet(dataset, percentTraining, iterations);

            System.out.println("Problem: " + problem.getName());
            System.out.println("\tRandomized Hill Climbing");
            System.out.println("\t\t"+problem.randomizedHillClimbing());
            System.out.println("\tSimulated Annealing");
            System.out.println("\t\t"+problem.simulatedAnnealing());
            System.out.println("\tGenetic Algorithm");
            System.out.println("\t\t"+problem.geneticAlgorithm());
        } else {
            printIncorrectUsageMessage();
        }
    }

    private static void printIncorrectUsageMessage() {
        System.err.println("Please select a mode from {part1, part2}.");
        System.err.println("\tpart1: Randomized optimization algorithms over 3 problems");
        System.err.println("\tpart2: Randomized optimization algorithms to train neural network");
        System.exit(1);
    }
}
