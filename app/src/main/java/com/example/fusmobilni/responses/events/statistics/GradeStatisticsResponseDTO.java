package com.example.fusmobilni.responses.events.statistics;

public class GradeStatisticsResponseDTO {
    private double grade;
    private int count;
    private double percentage;

    public GradeStatisticsResponseDTO(double grade, int count, double percentage) {
        this.grade = grade;
        this.count = count;
        this.percentage = percentage;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
