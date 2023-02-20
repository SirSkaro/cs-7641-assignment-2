package edu.gatech.bchurchill.assignment2;

import opt.EvaluationFunction;
import opt.OptimizationAlgorithm;
import shared.FixedIterationTrainer;
import shared.Instance;
import shared.Trainer;

public abstract class BaseProblemSet implements OptimizationProblemSet {
    protected SolutionStatistics solve(OptimizationAlgorithm algorithm, EvaluationFunction fitnessFunction) {
        long startTime = System.currentTimeMillis();
        Trainer fit = new FixedIterationTrainer(algorithm, 100);
        fit.train();
        long trainTime = System.currentTimeMillis() - startTime;

        Instance solution = algorithm.getOptimal();
        double score = fitnessFunction.value(solution);
        return new SolutionStatistics(solution, trainTime, score);
    }
}
