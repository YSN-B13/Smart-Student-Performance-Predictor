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
    private Map<String, List<String>> df;

    public ReadCSVFile(String filename) throws IOException {
        this.filename = filename;
        this.df = new LinkedHashMap<>();
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
                        df.put(header, new ArrayList<>());
                    }
                    isFirstLine = false;
                } else {
                    for (int i = 0; i < Math.min(values.length, headers.size()); i++) {
                        String header = headers.get(i);
                        df.get(header).add(values[i].trim());
                    }
                }
            }
        }
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

	public Map<String, List<String>> getDf() {
		return df;
	}
	
	public List<String> getColumn(String columnName) {
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
	}
	
	public void dropColumn(String columnName) {
	    if (!df.containsKey(columnName))
	        throw new IllegalArgumentException("Column '" + columnName + "' Does Not Exist");

	    df.remove(columnName);
	}
	
	public void describe() {
		System.out.printf("%-20s%-10s%-10s%-10s%-10s%-10s%n",
	            "Column", "Count", "Mean", "Std", "Min", "Max");
		
		System.out.println("--------------------------------------------------------------");
		
		for (String column : df.keySet()) {
			List<String> values = df.get(column);
			
			double sum = 0;
			double sum2 = 0;
			double min = Double.MAX_VALUE;
			double max = -Double.MAX_VALUE;
			int count = 0;
			boolean numeric = true;
			
			for (String value : values) {
				try {
					double number = Double.parseDouble(value);
					
					sum += number;
					sum2 += number * number;
					max = Math.max(max, number);
					min = Math.min(min, number);
					count++;
					
				} catch (NumberFormatException e) {
					numeric = false;
					break;
				}
			}
			
			if (numeric && count > 0) {
				double mean = sum / count;
				double std = Math.sqrt((sum2 / count) - Math.pow(mean, 2));
				
				System.out.printf("%-20s%-10d%-10.2f%-10.2f%-10.2f%-10.2f%n",
	                    column,
	                    count,
	                    mean,
	                    std,
	                    min,
	                    max);
			}
		}
	}
}
