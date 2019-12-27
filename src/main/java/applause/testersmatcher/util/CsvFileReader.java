package applause.testersmatcher.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public abstract class CsvFileReader<T> {
    private static final Logger logger = Logger.getLogger(CsvFileReader.class.getName());

    private static final String COMMA_DELIMITER = ",";

    public final List<T> readFile(String fileName) {
        List<List<String>> records = new ArrayList<>();
        try (InputStream inputStream = getClass().getResourceAsStream("/" + fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = br.readLine(); // skip first line with column names
            while ((line = br.readLine()) != null) {
                if (line.length() > 0) {
                    String[] values = line.split(COMMA_DELIMITER);
                    records.add(Arrays.stream(values)
                            .map(value -> value.replaceAll("\"", ""))
                            .collect(Collectors.toList()));
                }
            }
            return mapToModel(records);
        } catch (IOException exception) {
            logger.log(Level.SEVERE, exception.getLocalizedMessage());
            return List.of();
        }
    }

    protected abstract List<T> mapToModel(List<List<String>> records);
}
