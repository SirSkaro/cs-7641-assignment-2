package edu.gatech.bchurchill.assignment2;

import opt.EvaluationFunction;
import opt.OptimizationAlgorithm;
import shared.Instance;

public abstract class BaseProblemSet implements OptimizationProblemSet {
    protected int iterations = 50_000;
    protected int convergenceThreshold = 20;
    protected double convergenceTolerance = 0.125;

    protected SolutionStatistics solve(OptimizationAlgorithm algorithm, EvaluationFunction fitnessFunction) {
        ConvergenceIterationTrainer trainer = new ConvergenceIterationTrainer(algorithm, convergenceThreshold, iterations, convergenceTolerance);
        return runTrainer(trainer, algorithm, fitnessFunction);
    }

    protected SolutionStatistics solve(OptimizationAlgorithm algorithm, EvaluationFunction fitnessFunction, ConvergenceSpec spec) {
        ConvergenceIterationTrainer trainer = new ConvergenceIterationTrainer(algorithm, spec.convergenceThreshold, spec.iterations, spec.convergenceTolerance);
        return runTrainer(trainer, algorithm, fitnessFunction);
    }

    private SolutionStatistics runTrainer(ConvergenceIterationTrainer trainer, OptimizationAlgorithm algorithm, EvaluationFunction fitnessFunction) {
        long startTime = System.currentTimeMillis();
        trainer.train();
        long trainTime = System.currentTimeMillis() - startTime;

        Instance solution = algorithm.getOptimal();
        int iterations = trainer.getIterations();
        double score = fitnessFunction.value(solution);
        return new SolutionStatistics(solution, trainTime, iterations, score);
    }


}
