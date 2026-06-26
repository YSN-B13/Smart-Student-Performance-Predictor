import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReadCSVFile {

    private String filename;

    // DataFrame
    private Map<String, List<Object>> df;

    // Stores the detected type of each column
    private Map<String, Class<?>> columnTypes;

    public ReadCSVFile(String filename) throws IOException {

        this.filename = filename;

        df = new LinkedHashMap<>();
        columnTypes = new LinkedHashMap<>();

        // Read everything as String first
        Map<String, List<String>> rawData = new LinkedHashMap<>();

        List<String> headers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {

                String[] values = line.split(",");

                if (firstLine) {

                    for (String header : values) {

                        header = header.trim();

                        headers.add(header);

                        rawData.put(header, new ArrayList<>());
                    }

                    firstLine = false;

                } else {

                    for (int i = 0; i < Math.min(headers.size(), values.length); i++) {

                        rawData.get(headers.get(i)).add(values[i].trim());

                    }

                }

            }

        }

        // Infer each column type and convert it
        for (String column : rawData.keySet()) {

            Class<?> type = inferType(rawData.get(column));

            columnTypes.put(column, type);

            df.put(column, convertColumn(rawData.get(column), type));
        }

    }

    // -----------------------------
    // Detect the type of a column
    // -----------------------------
    private Class<?> inferType(List<String> values) {

        boolean integer = true;
        boolean decimal = true;
        boolean bool = true;

        for (String value : values) {

            // Integer

            try {

                Integer.parseInt(value);

            } catch (NumberFormatException e) {

                integer = false;

            }

            // Double

            try {

                Double.parseDouble(value);

            } catch (NumberFormatException e) {

                decimal = false;

            }

            // Boolean

            if (!(value.equalsIgnoreCase("true")
                    || value.equalsIgnoreCase("false")
                    || value.equalsIgnoreCase("yes")
                    || value.equalsIgnoreCase("no"))) {

                bool = false;

            }

        }

        if (integer)
            return Integer.class;

        if (decimal)
            return Double.class;

        if (bool)
            return Boolean.class;

        return String.class;

    }

    // -----------------------------
    // Convert one column
    // -----------------------------
    private List<Object> convertColumn(List<String> values,
                                       Class<?> type) {

        List<Object> result = new ArrayList<>();

        for (String value : values) {

            if (type == Integer.class) {

                result.add(Integer.parseInt(value));

            }

            else if (type == Double.class) {

                result.add(Double.parseDouble(value));

            }

            else if (type == Boolean.class) {

                result.add(value.equalsIgnoreCase("true")
                        || value.equalsIgnoreCase("yes"));

            }

            else {

                result.add(value);

            }

        }

        return result;

    }

    // -----------------------------
    // Head
    // -----------------------------
    public void head(int n) {

        for (String column : df.keySet()) {

            System.out.printf("%-20s", column);

        }

        System.out.println();

        int rows = df.values().iterator().next().size();

        n = Math.min(rows, n);

        for (int i = 0; i < n; i++) {

            for (String column : df.keySet()) {

                System.out.printf("%-20s", df.get(column).get(i));

            }

            System.out.println();

        }

    }

    // -----------------------------
    // Show detected types
    // -----------------------------
    public void printTypes() {

        System.out.println("\nDetected Types:");

        for (String column : columnTypes.keySet()) {

            System.out.printf("%-20s%s%n",
                    column,
                    columnTypes.get(column).getSimpleName());

        }

    }

    // -----------------------------
    // Getters
    // -----------------------------
    public Map<String, List<Object>> getData() {
        return df;
    }

    public Map<String, Class<?>> getColumnTypes() {
        return columnTypes;
    }

}