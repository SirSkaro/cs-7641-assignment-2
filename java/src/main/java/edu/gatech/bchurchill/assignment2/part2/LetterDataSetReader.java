package edu.gatech.bchurchill.assignment2.part2;

import shared.DataSet;
import shared.DataSetDescription;
import shared.Instance;
import shared.filt.DataSetFilter;
import shared.reader.DataSetReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class LetterDataSetReader extends DataSetReader {
    private List<DataSetFilter> filters;

    public LetterDataSetReader(List<DataSetFilter> filters) {
        super("datasets/letter recognition/data");
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
        int label = ((int)elements[0].charAt(0) % 64);
        double[] attributes = Stream.of(elements)
                .skip(1)
                .mapToDouble(Double::parseDouble)
                .toArray();
        return new Instance(attributes, label);
    }

}
