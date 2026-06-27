package eda;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.List;
import java.util.Map;

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
		
//		public static void Countplot(Map<String, List<Object>> df, String x) {}
		
//		public static void Lineplot(Map<String, List<Object>> df, String x, String y) {}
		
	}
}
