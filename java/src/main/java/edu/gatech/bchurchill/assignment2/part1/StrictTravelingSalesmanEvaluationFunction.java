package edu.gatech.bchurchill.assignment2.part1;

import opt.example.TravelingSalesmanRouteEvaluationFunction;
import shared.Instance;

public class StrictTravelingSalesmanEvaluationFunction extends TravelingSalesmanRouteEvaluationFunction {
    private int cityCount;
    private boolean printedValidSolution;

    public StrictTravelingSalesmanEvaluationFunction(double[][] points) {
        super(points);
        cityCount = points.length;
        printedValidSolution = false;
    }

    @Override
    public double value(Instance d) {
        double score = super.value(d);

        if(isValidSolution(d)) {
            score += cityCount;
            if(!printedValidSolution) {
                System.out.println("Found a valid solution!");
                this.printedValidSolution = true;
            }
        }

        return score;
    }

    private boolean isValidSolution(Instance d) {
        double startingCity = d.getContinuous(0);
        double endingCity = d.getContinuous(cityCount - 1);

        return startingCity == endingCity;
    }
}
