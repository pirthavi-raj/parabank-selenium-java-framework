package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {


    public static Object[][] read(String resourcePath) {
        List<Object[]> rows = new ArrayList<>();

        try (InputStream in = CSVReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new RuntimeException("CSV file not found on the classpath: " + resourcePath);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] cells = line.split(",", -1);
                for (int i = 0; i < cells.length; i++) {
                    cells[i] = cells[i].trim();
                }
                rows.add(cells);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read CSV file: " + resourcePath, e);
        }
        return rows.toArray(new Object[0][]);
    }
}