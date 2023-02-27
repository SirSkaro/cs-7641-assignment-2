package edu.gatech.bchurchill.assignment2.part1;

import dist.Distribution;
import opt.ga.CrossoverFunction;
import shared.Instance;

public class HalfwaySingleCrossOver implements CrossoverFunction {
    public HalfwaySingleCrossOver() {
    }

    public Instance mate(Instance a, Instance b) {
        double[] newData = new double[a.size()];
        int point = (newData.length + 1) / 2;

        for(int i = 0; i < newData.length; ++i) {
            if (i >= point) {
                newData[i] = a.getContinuous(i);
            } else {
                newData[i] = b.getContinuous(i);
            }
        }

        return new Instance(newData);
    }
}
