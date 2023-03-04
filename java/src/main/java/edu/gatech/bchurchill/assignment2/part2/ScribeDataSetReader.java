package edu.gatech.bchurchill.assignment2.part2;

import shared.DataSet;
import shared.DataSetDescription;
import shared.Instance;
import shared.filt.DataSetFilter;
import shared.reader.DataSetReader;
import util.linalg.DenseVector;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class ScribeDataSetReader extends DataSetReader {
    private static int NUM_CLASSES = 12;
    private List<DataSetFilter> filters;

    public ScribeDataSetReader(List<DataSetFilter> filters) {
        super("datasets/scribe recognition/data");
        this.filters = filters;
    }

    @Override
    public DataSet read() throws Exception {
        var instances = parseInstances();
        var result = new DataSet(instances);
        for(var filter : filters) {
            filter.filter(result);
        }
        result.setDescription(new DataSetDescription((result)));
        return result;
    }

    private Instance[] parseInstances() throws URISyntaxException, IOException {
        return getLinesFromFile()
                .map(this::toInstance)
                .toArray(Instance[]::new);
    }

    private Stream<String> getLinesFromFile() throws URISyntaxException, IOException {
        URL uri = getClass().getClassLoader().getResource(this.file);
        return Files.lines(Path.of(uri.toURI()));
    }

    private Instance toInstance(String row) {
        String[] elements = row.split(",");
        int label = getLabel(elements);
        double[] attributes = Stream.of(elements)
                .limit(10)
                .mapToDouble(Double::parseDouble)
                .toArray();

        double[] classes = new double[NUM_CLASSES];
        classes[label] = 1.0;
        return new Instance(new DenseVector(attributes), new Instance(classes));
    }

    private int getLabel(String[] elements) {
        switch(elements[10].charAt(0)) {
            case 'A':
                return 0;
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 3;
            case 'E':
                return 4;
            case 'F':
                return 5;
            case 'G':
                return 6;
            case 'H':
                return 7;
            case 'I':
                return 8;
            case 'W':
                return 9;
            case 'X':
                return 10;
            case 'Y':
                return 11;
            default: throw new IllegalArgumentException("Unknown label");
        }
    }

}
