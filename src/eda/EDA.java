package eda;

import java.io.IOException;

public class EDA {
	
	public static void main(String[] args) {

        try {
            ReadCSVFile csv = new ReadCSVFile("data/train.csv");

            System.out.println("========== HEAD ==========");
            csv.head(5);

            System.out.println();
            
            System.out.println("========== TAIL ==========");
            csv.tail(2);

            System.out.println();
            
            System.out.println("========== SHAPE ==========");
            int[] shape = csv.getShape();
            System.out.println("(" + shape[0] + ", " + shape[1] + ")");
            
            System.out.println();
            
            System.out.println("========== RENAME S/N -> RollNo ==========");
            csv.renameColumn("S/N", "RollNo");
            System.out.println(csv.getColumn("RollNo"));
            
            System.out.println();
            
            System.out.println("========== COLUMNS ==========");
            csv.columns();
            
            System.out.println();
            
            System.out.println("========== DESCRIBE ==========");
            csv.describe();

        } catch (IOException e) {
            System.out.println("Error reading CSV file.");
            e.printStackTrace();
        }
    }
}
