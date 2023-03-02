package edu.gatech.bchurchill.assignment2.part1;

import edu.gatech.bchurchill.assignment2.SolutionStatistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.stream.Stream;

public class CsvWriter {

    private String filename;

    public CsvWriter(String filename) {
        this.filename = filename;
    }

    // Repurposed code from https://www.baeldung.com/java-csv
    public File writeToFile(Stream<SolutionStatistics> stats) throws FileNotFoundException {
        File result = new File(filename);
        try (PrintWriter writer = new PrintWriter(result)) {
            stats.map(this::formatStat)
                    .forEach(writer::println);
        }

        return result;
    }

    private String formatStat(SolutionStatistics stats) {
        return String.format("%d,%d,%f", stats.trainTimeInMs, stats.iterations, stats.score);
    }

}
