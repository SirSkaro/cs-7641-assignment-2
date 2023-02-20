package edu.gatech.bchurchill.assignment2;

import java.util.List;

public class Assignment {

    public static void main(String[] args) {
        List<OptimizationProblemSet> problems = List.of(new NQueensProblemSet(8));

        for(var problem: problems) {
            System.out.println("Problem: " + problem.getName());
            problem.randomizedHillClimbing();
        }
    }

}
