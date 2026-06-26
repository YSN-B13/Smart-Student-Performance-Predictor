package model;

import java.time.LocalDate;
import java.util.List;

public class Student {
	
	// Basic Information
	private String firstname;
	private String lastname;
	private String gender; //student's sex (binary: 'F' - female or 'M' - male)
	private int age; //student's age (numeric: from 15 to 22)
	private LocalDate dateregist; //student's registration date
	
	// Family Information
	private String location; //student's home address type (binary: 'U' - urban or 'R' - rural)
	private String famsize; //family size (binary: 'LE3' - less or equal to 3 or 'GT3' - greater than 3)
	private String pstatus; //parent's cohabitation status (binary: 'T' - living together or 'A' - apart)
	
	// Parents Education
	private String medu; //mother's education (numeric: 0 - none, 1 - primary education (4th grade), 2 - 5th to 9th grade, 3 - secondary education or 4 - higher education)
	private String fedu; //father's education (numeric: 0 - none, 1 - primary education (4th grade), 2 â€“ 5th to 9th grade, 3 â€“ secondary education or 4 â€“ higher education)
	private static final List<String> VALID_EDU = List.of(
		    "None",
		    "Primary Education (4th Grade)",
		    "5th to 9th Grade",
		    "Secondary Education",
		    "Higher Education");
	
	// School Information
	private String traveltime; //home to school travel time (numeric: 1 - <15 min., 2 - 15 to 30 min., 3 - 30 min. to 1 hour, or 4 - >1 hour)
	private String studytime; //weekly study time (numeric: 1 - <2 hours, 2 - 2 to 5 hours, 3 - 5 to 10 hours, or 4 - >10 hours)
	private int failures; //number of past class failures (numeric: n if 1<=n<3, else 4)
	
	// Supports
	private boolean schoolsup; //extra educational support (binary: yes or no)
	private boolean famsup; //family educational support (binary: yes or no)
	private boolean paid; //extra paid classes within the course subject (Math or Portuguese) (binary: yes or no)
	
	// Activities
	private boolean activities; //extra-curricular activities (binary: yes or no)
	private boolean nursery; //attended nursery school (binary: yes or no)
	private boolean higher; //wants to take higher education (binary: yes or no)
	private boolean internet; //Internet access at home (binary: yes or no)
	
	// Lifestyle
	private int famrel; //quality of family relationships (numeric: from 1 - very bad to 5 - excellent)
	private int freetime; //free time after school (numeric: from 1 - very low to 5 - very high)
	private int health; //current health status (numeric: from 1 - very bad to 5 - very good)
	
	// Academic Information
	private int absences; //number of school absences (numeric: from 0 to 93)
	private double score; // Target (numeric: from 0 to 20)
	
	public Student() {
    }

	public Student(String firstname, String lastname, String gender, int age, String location, String famsize, String pstatus,
			String medu, String fedu, String traveltime, String studytime, int failures, boolean schoolsup,
			boolean famsup, boolean paid, boolean activities, boolean nursery, boolean higher, boolean internet,
			int famrel, int freetime, int health, int absences, double score) {
		setFirstname(firstname);
		setLastname(lastname);
		setGender(gender);
		setAge(age);
		setDateregist();
		setLocation(location);
		setFamsize(famsize);
		setPstatus(pstatus);
		setMedu(medu);
		setFedu(fedu);
		setTraveltime(traveltime);
		setStudytime(studytime);
		setFailures(failures);
		setSchoolsup(schoolsup);
		setFamsup(famsup);
		setPaid(paid);
		setActivities(activities);
		setNursery(nursery);
		setHigher(higher);
		setInternet(internet);
		setFamrel(famrel);
		setFreetime(freetime);
		setHealth(health);
		setAbsences(absences);
		setScore(score);
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		if (!gender.equals("Male") && !gender.equals("Female"))
			throw new IllegalArgumentException("Gender Must be Male or Female");
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		if (age < 15 || age > 22)
			throw new IllegalArgumentException("Age Must Be Between 15 and 22");
		this.age = age;
	}
	
	public LocalDate getDateregist() {
		return dateregist;
	}

	public void setDateregist() {
		this.dateregist = LocalDate.now();
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		if (!location.equals("Urban") && !location.equals("Rural"))
            throw new IllegalArgumentException("Location Must Be Urban or Rural");
		this.location = location;
	}

	public String getFamsize() {
		return famsize;
	}

	public void setFamsize(String famsize) {
		if (!famsize.equals("Less or Equal to 3") && !famsize.equals("Greater Than 3"))
			throw new IllegalArgumentException("Family Size Must Be 'Less or Equal to 3' or 'Greater Than 3'");
		this.famsize = famsize;
	}

	public String getPstatus() {
		return pstatus;
	}

	public void setPstatus(String pstatus) {
		if (!pstatus.equals("Together") && !pstatus.equals("Apart"))
			throw new IllegalArgumentException("Parent Status Must Be 'Together' or 'Apart'");
		this.pstatus = pstatus;
	}

	public String getMedu() {
		return medu;
	}

	public void setMedu(String medu) {
		if (!VALID_EDU.contains(medu))
			throw new IllegalArgumentException("Mother Education Must Be: " + String.join(", ", VALID_EDU));
		this.medu = medu;
	}

	public String getFedu() {
		return fedu;
	}

	public void setFedu(String fedu) {
		if (!VALID_EDU.contains(fedu))
			throw new IllegalArgumentException("Father Education Must Be: " + String.join(", ", VALID_EDU));
		this.fedu = fedu;
	}

	public String getTraveltime() {
		return traveltime;
	}

	public void setTraveltime(String traveltime) {
		if (!List.of("Less Than 15 min", "15 to 30 min", "30 min to 1 hour", "Greater Than 1 hour").contains(traveltime))
			throw new IllegalArgumentException("Home to School Travel Time Must be 'Less Than 15 min', '15 to 30 min', '30 min to 1 hour' or 'Greater Than 1 hour'");
		this.traveltime = traveltime;
	}

	public String getStudytime() {
		return studytime;
	}

	public void setStudytime(String studytime) {
		if (!List.of("Less Than 2 hours", "2 to 5 hours", "5 to 10 hours", "Greater Than 10 hours").contains(studytime))
			throw new IllegalArgumentException("Weekly Study Time Must be 'Less Than 2 hours', '2 to 5 hours', '5 to 10 hours' or 'Greater Than 10 hours'");
		this.studytime = studytime;
	}

	public int getFailures() {
		return failures;
	}

	public void setFailures(int failures) {
	    if (failures < 0)
	        throw new IllegalArgumentException("Number of Past Class Failures Must Be Non-Negative");
	    this.failures = Math.min(failures, 3);
	}
	
	public boolean isSchoolsup() {
		return schoolsup;
	}

	public void setSchoolsup(boolean schoolsup) {
		this.schoolsup = schoolsup;
	}

	public boolean isFamsup() {
		return famsup;
	}

	public void setFamsup(boolean famsup) {
		this.famsup = famsup;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public boolean isActivities() {
		return activities;
	}

	public void setActivities(boolean activities) {
		this.activities = activities;
	}

	public boolean isNursery() {
		return nursery;
	}

	public void setNursery(boolean nursery) {
		this.nursery = nursery;
	}

	public boolean isHigher() {
		return higher;
	}

	public void setHigher(boolean higher) {
		this.higher = higher;
	}

	public boolean isInternet() {
		return internet;
	}

	public void setInternet(boolean internet) {
		this.internet = internet;
	}

	public int getFamrel() {
		return famrel;
	}

	public void setFamrel(int famrel) {
		if (famrel < 1 || famrel > 5)
	        throw new IllegalArgumentException("Family Relationship Quality Must Be Between 1 - Very Bad and 5 - Excellent");
	    this.famrel = famrel;
	}

	public int getFreetime() {
		return freetime;
	}

	public void setFreetime(int freetime) {
		if (freetime < 1 || freetime > 5)
	        throw new IllegalArgumentException("Free Time After School Must Be Between 1 - Very Low and 5 - Very High");
		this.freetime = freetime;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		if (health < 1 || health > 5)
	        throw new IllegalArgumentException("Current Health Status Must Be Between 1 - Very Bad and 5 - Very Good");
		this.health = health;
	}

	public int getAbsences() {
		return absences;
	}

	public void setAbsences(int absences) {
		if (absences < 0)
            throw new IllegalArgumentException("Number of School Absences Must Be Non-Negative");
		this.absences = absences;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		if (score < 0 || score > 20)
            throw new IllegalArgumentException("Score Must Be Between 0 and 20");
		this.score = score;
	}
	
	public boolean isAtRisk() {
        return score < 10;
    }

    public boolean isExcellent() {
        return score >= 16;
    }

    @Override
    public String toString() {
        return "Student{" +
               "first name='" + firstname + '\'' +
               ", last name='" + lastname + '\'' +
               ", score=" + score +
               ", absences=" + absences +
               '}';
    }
}
