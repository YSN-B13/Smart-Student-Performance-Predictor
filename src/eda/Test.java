package eda;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Test {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            // Create dataset
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            dataset.addValue(10, "Sales", "January");
            dataset.addValue(15, "Sales", "February");
            dataset.addValue(25, "Sales", "March");
            dataset.addValue(20, "Sales", "April");
            dataset.addValue(30, "Sales", "May");

            // Create chart
            JFreeChart chart = ChartFactory.createBarChart(
                    "Monthly Sales", // Chart title
                    "Month",         // X-axis label
                    "Sales",         // Y-axis label
                    dataset
            );

            // Create panel
            ChartPanel chartPanel = new ChartPanel(chart);

            // Create frame
            JFrame frame = new JFrame("JFreeChart Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setContentPane(chartPanel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}