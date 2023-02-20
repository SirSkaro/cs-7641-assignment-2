package edu.gatech.bchurchill.assignment2;

import java.util.List;

public class Assignment {

    public static void main(String[] args) {
        List<OptimizationProblem> problems = List.of(new NQueensProblem(8));

        for(var problem: problems) {
            System.out.print("Problem: " + problem.getName());
        }
    }

}
