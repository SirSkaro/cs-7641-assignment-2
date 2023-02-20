package edu.gatech.bchurchill.assignment2;

import opt.EvaluationFunction;
import opt.ga.NQueensFitnessFunction;

public class NQueensProblem implements OptimizationProblem {

    private int numberQueens;
    private EvaluationFunction fitnessFunction;

    public NQueensProblem(int numberQueens) {
        this.numberQueens = numberQueens;
        this.fitnessFunction = new NQueensFitnessFunction();
    }

    @Override
    public String getName() {
        return String.format("%d-Queens", numberQueens);
    }

    @Override
    public void randomizedHillClimbing() {

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
