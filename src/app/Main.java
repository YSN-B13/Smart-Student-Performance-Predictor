package app;

import model.Student;

public class Main {

    public static void main(String[] args) {

        try {

            Student student = new Student(
                    "Yassine",
                    "Moutawakkil",
                    "Male",
                    20,
                    "Urban",
                    "Greater Than 3",
                    "Together",
                    "Higher Education",
                    "Secondary Education",
                    "15 to 30 min",
                    "2 to 5 hours",
                    2,
                    true,
                    true,
                    false,
                    true,
                    true,
                    true,
                    true,
                    5,
                    4,
                    5,
                    3,
                    17.5
            );

            System.out.println("===== STUDENT INFORMATION =====");

            System.out.println("First Name : " + student.getFirstname());
            System.out.println("Last Name  : " + student.getLastname());
            System.out.println("Gender     : " + student.getGender());
            System.out.println("Age        : " + student.getAge());
            System.out.println("Registered : " + student.getDateregist());

            System.out.println();

            System.out.println("Location   : " + student.getLocation());
            System.out.println("Family Size: " + student.getFamsize());
            System.out.println("Parent Stat: " + student.getPstatus());

            System.out.println();

            System.out.println("Mother Edu : " + student.getMedu());
            System.out.println("Father Edu : " + student.getFedu());

            System.out.println();

            System.out.println("Travel Time: " + student.getTraveltime());
            System.out.println("Study Time : " + student.getStudytime());
            System.out.println("Failures   : " + student.getFailures());

            System.out.println();

            System.out.println("School Support : " + student.isSchoolsup());
            System.out.println("Family Support : " + student.isFamsup());
            System.out.println("Paid Classes   : " + student.isPaid());

            System.out.println();

            System.out.println("Activities : " + student.isActivities());
            System.out.println("Nursery    : " + student.isNursery());
            System.out.println("Higher Edu : " + student.isHigher());
            System.out.println("Internet   : " + student.isInternet());

            System.out.println();

            System.out.println("Family Relationship : " + student.getFamrel());
            System.out.println("Free Time          : " + student.getFreetime());
            System.out.println("Health             : " + student.getHealth());

            System.out.println();

            System.out.println("Absences : " + student.getAbsences());
            System.out.println("Score    : " + student.getScore());

            System.out.println();

            System.out.println("At Risk?     : " + student.isAtRisk());
            System.out.println("Excellent?   : " + student.isExcellent());

            System.out.println();

            System.out.println(student);

        } catch (IllegalArgumentException e) {

            System.out.println("Validation Error:");
            System.out.println(e.getMessage());

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
