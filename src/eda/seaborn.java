package eda;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class seaborn {
	
	public static void Barplot(Map<String, List<Object>> df, String x, String y) {
		if (!df.containsKey(x) || !df.containsKey(y))
	        throw new IllegalArgumentException("Column Not Found");
		
		SwingUtilities.invokeLater(() -> {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			
			int rowCount = df.values().iterator().next().size();
					
			for (int i = 0; i < rowCount; i++) {
			    Object rawValue = df.get(y).get(i);
			    Object rawCategory = df.get(x).get(i);
			    
			    if (rawValue == null || rawCategory == null) continue;
			    
			    Number value;
			    if (rawValue instanceof Number) {
			        value = (Number) rawValue;
			    } else {
			        value = Double.parseDouble(rawValue.toString());
			    }
			    
			    String category = rawCategory.toString();
			    
			    dataset.addValue(value, y, category);
			}
			
			if (dataset.getRowCount() == 0) {
			    throw new IllegalStateException("No Valid Data to Plot");
			}
			
			String xLabel = x.substring(0, 1).toUpperCase() + x.substring(1);
			String yLabel = y.substring(0, 1).toUpperCase() + y.substring(1);
			String title = yLabel + " by " + xLabel;
			
			JFreeChart chart = ChartFactory.createBarChart(
                    title,     // Chart title
                    x,         // X-axis label
                    y,         // Y-axis label
                    dataset
            );
			
			ChartPanel chartPanel = new ChartPanel(chart);
			
			JFrame frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setContentPane(chartPanel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
			
		});
	}
	
//	public static void Countplot(Map<String, List<Object>> df, String x) {}
	
	public static void Lineplot(Map<String, List<Object>> df, String x, String y) {
	    if (!df.containsKey(x) || !df.containsKey(y))
	        throw new IllegalArgumentException("Column Not Found");

	    if (!(df.get(x).get(0) instanceof Number) ||
	        !(df.get(y).get(0) instanceof Number)) {
	        throw new IllegalArgumentException("Both Columns Must Be Numeric.");
	    }

	    SwingUtilities.invokeLater(() -> {
	        Map<Double, List<Double>> groups = new TreeMap<>();

	        int rowCount = df.values().iterator().next().size();

	        for (int i = 0; i < rowCount; i++) {
	            Object rawX = df.get(x).get(i);
	            Object rawY = df.get(y).get(i);

	            if (rawX == null || rawY == null) continue;

	            if (rawX instanceof String &&
	                (((String) rawX).trim().isEmpty() ||
	                 ((String) rawX).equalsIgnoreCase("NaN") ||
	                 ((String) rawX).equalsIgnoreCase("null")))
	                continue;

	            if (rawY instanceof String &&
	                (((String) rawY).trim().isEmpty() ||
	                 ((String) rawY).equalsIgnoreCase("NaN") ||
	                 ((String) rawY).equalsIgnoreCase("null")))
	                continue;

	            double xv, yv;
	            try {
	                xv = (rawX instanceof Number) ? ((Number) rawX).doubleValue()
	                                              : Double.parseDouble(rawX.toString());
	                yv = (rawY instanceof Number) ? ((Number) rawY).doubleValue()
	                                              : Double.parseDouble(rawY.toString());
	            } catch (NumberFormatException e) {
	                continue;
	            }

	            groups.computeIfAbsent(xv, k -> new ArrayList<>()).add(yv);
	        }

	        if (groups.isEmpty()) {
	            throw new IllegalStateException("No Valid Data to Plot");
	        }

	        XYSeries series = new XYSeries("Mean " + y);

	        for (Map.Entry<Double, List<Double>> entry : groups.entrySet()) {
	            double mean = entry.getValue().stream()
	                             .mapToDouble(Double::doubleValue)
	                             .average()
	                             .orElse(0);
	            series.add(Double.valueOf(entry.getKey()), Double.valueOf(mean));
	        }

	        XYSeriesCollection dataset = new XYSeriesCollection(series);

	        String xLabel = x.substring(0, 1).toUpperCase() + x.substring(1);
	        String yLabel = y.substring(0, 1).toUpperCase() + y.substring(1);
	        String title = yLabel + " by " + xLabel;

	        JFreeChart chart = ChartFactory.createXYLineChart(
	            title,
	            x,
	            "Mean " + y,
	            dataset
	        );

	        ChartPanel panel = new ChartPanel(chart);
	        panel.setMouseWheelEnabled(true);

	        JFrame frame = new JFrame(title);
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        frame.setSize(600, 600);
	        frame.setContentPane(panel);
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	    });
	}
	
}
