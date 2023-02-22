package edu.gatech.bchurchill.assignment2;

import opt.EvaluationFunction;
import opt.OptimizationAlgorithm;
import shared.Instance;

public abstract class BaseProblemSet implements OptimizationProblemSet {
    protected SolutionStatistics solve(OptimizationAlgorithm algorithm, EvaluationFunction fitnessFunction) {
        long startTime = System.currentTimeMillis();
        ConvergenceIterationTrainer trainer = new ConvergenceIterationTrainer(algorithm, 20, 50_000);
        trainer.train();
        long trainTime = System.currentTimeMillis() - startTime;

        Instance solution = algorithm.getOptimal();
        int iterations = trainer.getIterations();
        double score = fitnessFunction.value(solution);
        return new SolutionStatistics(solution, trainTime, iterations, score);
    }

}
