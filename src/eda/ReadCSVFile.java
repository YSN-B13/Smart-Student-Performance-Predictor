package eda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReadCSVFile {
    private String filename;
    private Map<String, List<Object>> df;
    private Map<String, Class<?>> columnTypes;

    public ReadCSVFile(String filename) throws IOException {
        this.filename = filename;
        df = new LinkedHashMap<>();
        columnTypes = new LinkedHashMap<>();
        
        Map<String, List<String>> rawData = new LinkedHashMap<>();
        List<String> headers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (isFirstLine) {
                    for (String header : values) {
                        header = header.trim();
                        headers.add(header);
                        rawData.put(header, new ArrayList<>());
                    }
                    isFirstLine = false;
                } else {
                    for (int i = 0; i < Math.min(values.length, headers.size()); i++) {
                    	rawData.get(headers.get(i)).add(values[i].trim());
                    }
                }
            }
        }
        
        for (String column : rawData.keySet()) {
        	Class<?> type = inferType(rawData.get(column));
        	columnTypes.put(column, type);
        	df.put(column, convertColumn(rawData.get(column), type));
        }
    }
    
    private Class<?> inferType(List<String> values) {
    	boolean integer = true;
    	boolean decimal = true;
    	boolean bool = true;
    	
    	 for (String value : values) {

             try {
                 Integer.parseInt(value);
             } catch (NumberFormatException e) {
                 integer = false;
             }


             try {
                 Double.parseDouble(value);
             } catch (NumberFormatException e) {
                 decimal = false;
             }

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
    
    private List<Object> convertColumn(List<String> values, Class<?> type) {
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
   
    public void head(int n) {
    	if (n <= 0) {
    	    System.out.println("Number of Rows Must Be Greater Than 0");
    	    return;
    	}
    	
    	if (df.isEmpty()) {
    		System.out.println("The CSV File is Empty");
    		return;
    	}
    	
    	int rowCount = df.values().iterator().next().size();
    	n = Math.min(n, rowCount);
    	
    	for (String key : df.keySet()) {
    	    System.out.printf("%-20s", key);
    	}
    	System.out.println();
    	
    	for(int i = 0; i < df.size(); i++) {
    		System.out.print("--------------------");
    	}
    	System.out.println();
    	
    	for (int i = 0; i < n; i++) {
    		for (String key : df.keySet()) {
    			System.out.printf("%-20s", df.get(key).get(i));
            }
            System.out.println();
    	}
    }
    
    public void tail(int n) {
    	if (n <= 0) {
    	    System.out.println("Number of Rows Must Be Greater Than 0");
    	    return;
    	}
    	
        if (df.isEmpty()) {
            System.out.println("The CSV File is Empty");
            return;
        }

        int rowCount = df.values().iterator().next().size();
        n = Math.min(n, rowCount);

        for (String key : df.keySet()) {
            System.out.printf("%-20s", key);
        }
        System.out.println();

        for (int i = 0; i < df.size(); i++) {
            System.out.print("--------------------");
        }
        System.out.println();

        for (int i = rowCount - n; i < rowCount; i++) {
            for (String key : df.keySet()) {
                System.out.printf("%-20s", df.get(key).get(i));
            }
            System.out.println();
        }
    }
    
    public String getFilename() {
		return filename;
	}
    
    public void info() {
        System.out.println("Data Columns (Total " + df.size() + " Columns):");
        System.out.printf("%-20s%-20s%-20s%n",
	            "Column", "Non-Null Count", "Dtype");
        System.out.println("-----------------------------------------------");

        for (String column : columnTypes.keySet()) {
            System.out.printf("%-20s%-20s%-20s%n",
                    column,
                    notNaColumn(column) + " Non-Null",
                    columnTypes.get(column).getSimpleName());
        }
    }
    
    public void dtypes() {
        for (String column : columnTypes.keySet()) {
            System.out.printf("%-20s%-20s%n",
                    column,
                    columnTypes.get(column).getSimpleName());
        }
    }

	public Map<String, List<Object>> getDf() {
		return df;
	}
	
	public Map<String, Class<?>> getColumnTypes() {
        return columnTypes;
    }
	
	public List<Object> getColumn(String columnName) {
        return df.get(columnName);
    }
	
	public void columns() {
	    System.out.println("['" + String.join("', '", df.keySet()) + "']");
	}
	
	public int[] getShape() {
	    if (df.isEmpty()) {
	        return new int[] {0, 0};
	    }

	    int rows = df.values().iterator().next().size();
	    int columns = df.size();

	    return new int[] {rows, columns};
	}
	
	public void renameColumn(String oldColumnName, String newColumnName) {
	    if (!df.containsKey(oldColumnName))
	        throw new IllegalArgumentException("Column '" + oldColumnName + "' Does Not Exist");

	    if (df.containsKey(newColumnName))
	        throw new IllegalArgumentException("Column '" + newColumnName + "' Already Exists");

	    df.put(newColumnName, df.remove(oldColumnName));

	    if (columnTypes.containsKey(oldColumnName)) {
	        columnTypes.put(newColumnName, columnTypes.remove(oldColumnName));
	    }
	}
	
	public void dropColumn(String columnName) {
	    if (!df.containsKey(columnName))
	        throw new IllegalArgumentException("Column '" + columnName + "' Does Not Exist");

	    df.remove(columnName);
	    columnTypes.remove(columnName);
	}
	
	public void describe() {
		System.out.printf("%-20s%-10s%-10s%-10s%-10s%-10s%n",
	            "Column", "Count", "Mean", "Std", "Min", "Max");
		
		System.out.println("-------------------------------------------------------------------------");
		
		for (String column : df.keySet()) {
			Class<?> type = columnTypes.get(column);
			if (!(type == Integer.class || type == Double.class)) {
	            continue;
	        }
			
			List<Object> values = df.get(column);
			
			double sum = 0;
			double sumSquares  = 0;
			double min = Double.MAX_VALUE;
			double max = -Double.MAX_VALUE;
			int count = values.size();
			
			for (Object obj : values) {
				double number = ((Number) obj).doubleValue();

	            sum += number;
	            sumSquares += number * number;

	            min = Math.min(min, number);
	            max = Math.max(max, number);
			}

			double mean = sum / count;

			double variance = (sumSquares / count) - (mean * mean);
	        variance = Math.max(variance, 0);
	        double std = Math.sqrt(variance);

	        System.out.printf(
	            "%-20s%-10d%-12.2f%-12.2f%-12.2f%-12.2f%n",
	            column,
	            count,
	            mean,
	            std,
	            min,
	            max
	        );
		}
	}
	
	public void isNull() {
		System.out.printf("%-20s%s%n", "Column", "Null Count");
	    System.out.println("--------------------------------");
	    
	    for (String column : df.keySet()) {
	    	int count = 0;
	    	for (Object value : df.get(column)) {
	    		if (value == null) {
	    			count++;
	    		} 
	    		else if (value instanceof String) {
	    			String s = ((String) value).trim();
	    			
	    			if (s.isEmpty()
	                        || s.equalsIgnoreCase("null")
	                        || s.equalsIgnoreCase("NaN")
	                        || s.equalsIgnoreCase("NA")) {

	                    count++;
	                }
	    		}
	    	}
	    	System.out.printf("%-20s%d%n", column, count);
	    }
	}
	
	public int notNaColumn(String columnName) {
	    if (!df.containsKey(columnName))
	        throw new IllegalArgumentException("Column '" + columnName + "' Does Not Exist");

	    int notNa = 0;

	    for (Object value : df.get(columnName)) {
	        if (value == null)
	            continue;

	        if (value instanceof String) {
	            String s = ((String) value).trim();

	            if (s.isEmpty()
	                    || s.equalsIgnoreCase("null")
	                    || s.equalsIgnoreCase("NaN")
	                    || s.equalsIgnoreCase("NA"))
	                continue;
	        }
	        notNa++;
	    }
	    return notNa;
	}
	
	public void valueCounts(String columnName, boolean ascending) {
	    if (!df.containsKey(columnName))
	        throw new IllegalArgumentException(
	                "Column '" + columnName + "' Does Not Exist");

	    Map<Object, Integer> valueCounts = new LinkedHashMap<>();

	    for (Object value : df.get(columnName)) {
	        valueCounts.put(value, valueCounts.getOrDefault(value, 0) + 1);
	    }

	    List<Map.Entry<Object, Integer>> entries =
	            new ArrayList<>(valueCounts.entrySet());

	    if (ascending)
	        entries.sort(Map.Entry.comparingByValue());
	    else
	        entries.sort(Map.Entry.<Object, Integer>comparingByValue().reversed());

	    System.out.println("Value Counts for '" + columnName + "'");
	    System.out.printf("%-20s%s%n", "Value", "Count");
	    System.out.println("--------------------------------");
	    
	    for (Map.Entry<Object, Integer> entry : entries) {
	        System.out.printf("%-20s %d%n",
	                entry.getKey(),
	                entry.getValue());
	    }
	}
}
