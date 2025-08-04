package com.example.fusmobilni.responses.events.statistics;

import java.util.ArrayList;

public class EventStatisticsResponse {
    private Long eventId;
    private String eventTitle;
    private Long ownerId;
    private String ownerName;
    private String ownerLastName;
    private int visitorsCount;
    private double totalBudget;
    private double averageGrade;
    private ArrayList<GradeStatisticsResponseDTO> gradeStatistics;

    public EventStatisticsResponse(Long eventId, String eventTitle, Long ownerId, String ownerName, String ownerLastName,
                                   int visitorsCount, double totalBudget, double averageGrade,
                                   ArrayList<GradeStatisticsResponseDTO> gradeStatistics) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.ownerLastName = ownerLastName;
        this.visitorsCount = visitorsCount;
        this.totalBudget = totalBudget;
        this.averageGrade = averageGrade;
        this.gradeStatistics = gradeStatistics;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public int getVisitorsCount() {
        return visitorsCount;
    }

    public void setVisitorsCount(int visitorsCount) {
        this.visitorsCount = visitorsCount;
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public ArrayList<GradeStatisticsResponseDTO> getGradeStatistics() {
        return gradeStatistics;
    }

    public void setGradeStatistics(ArrayList<GradeStatisticsResponseDTO> gradeStatistics) {
        this.gradeStatistics = gradeStatistics;
    }
}
