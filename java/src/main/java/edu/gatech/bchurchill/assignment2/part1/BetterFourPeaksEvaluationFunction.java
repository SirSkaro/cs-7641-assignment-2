package edu.gatech.bchurchill.assignment2.part1;

import opt.EvaluationFunction;
import shared.Instance;
import util.linalg.Vector;

public class BetterFourPeaksEvaluationFunction implements EvaluationFunction {
    private int fEvals = 0;
    private int t;
    private boolean printedReward = false;

    public BetterFourPeaksEvaluationFunction(int t) {
        this.t = t;
    }

    public double value(Instance d) {
        ++this.fEvals;
        Vector data = d.getData();

        int i;
        for(i = 0; i < data.size() && data.get(i) == 1.0; ++i) {
        }

        int head = i;

        for(i = data.size() - 1; i >= 0 && data.get(i) == 0.0; --i) {
        }

        int tail = data.size() - 1 - i;
        int r = 0;
        if (head > this.t && tail > this.t) {
            if(!printedReward) {
                System.out.println("Got the reward!");
                printedReward = true;
            }
            r = 100;
        }

        return (Math.max(tail, head) + r);
    }

    public int getFunctionEvaluations() {
        return this.fEvals;
    }

    public void resetFunctionEvaluationCount() {
        this.fEvals = 0;
    }
}
