package edu.gatech.bchurchill.assignment2;

import edu.gatech.bchurchill.assignment2.part1.*;
import edu.gatech.bchurchill.assignment2.part2.LetterDataSetReader;
import edu.gatech.bchurchill.assignment2.part2.NeuralNetworkProblemSet;
import shared.filt.DataSetFilter;
import shared.filt.RandomOrderFilter;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

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
                    new NQueensProblemSet(150),
                    new FourPeaksProblemSet(100),
                    new TravelingSalesmanProblemSet(25)
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
        } else if (mode.equals("export1")) {
            List<Supplier<OptimizationProblemSet>> problems = List.of(
                    () -> new NQueensProblemSet(150),
                    () ->  new FourPeaksProblemSet(100),
                    () -> new TravelingSalesmanProblemSet(25)
            );
            int runCount = 2;

            for(var problem: problems) {
                System.out.println(problem.get().getName());
                System.out.println("\tRunning Randomized Hill Climbing...");
                CsvWriter writer = new CsvWriter(problem.get().getName() + " RHC.csv");
                var results = IntStream.range(0, runCount)
                        .mapToObj(run -> problem.get())
                        .map(OptimizationProblemSet::randomizedHillClimbing);
                writer.writeToFile(results);

                System.out.println("\tRunning Simulated Annealing...");
                writer = new CsvWriter(problem.get().getName() + " SA.csv");
                results = IntStream.range(0, runCount)
                        .mapToObj(run -> problem.get())
                        .map(OptimizationProblemSet::simulatedAnnealing);
                writer.writeToFile(results);

                System.out.println("\tRunning Genetic Algorithm...");
                writer = new CsvWriter(problem.get().getName() + " GA.csv");
                results = IntStream.range(0, runCount)
                        .mapToObj(run -> problem.get())
                        .map(OptimizationProblemSet::geneticAlgorithm);
                writer.writeToFile(results);

                System.out.println("\tRunning MIMIC...");
                writer = new CsvWriter(problem.get().getName() + " MIMIC.csv");
                results = IntStream.range(0, runCount)
                        .mapToObj(run -> problem.get())
                        .map(OptimizationProblemSet::mimic);
                writer.writeToFile(results);

                System.out.println("Done");
            }
        } else {
            printIncorrectUsageMessage();
        }
    }

    private static void printIncorrectUsageMessage() {
        System.err.println("Please select a mode from {part1, export1, part2}.");
        System.err.println("\tpart1: Randomized optimization algorithms over 3 problems");
        System.err.println("\texport1: Perform the optimization problems many times and print the results to a .csv");
        System.err.println("\tpart2: Randomized optimization algorithms to train neural network");
        System.exit(1);
    }
}
