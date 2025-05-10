package com.abdonmorales.courseio.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Course {
    private final SimpleStringProperty courseCode = new SimpleStringProperty();
    private final SimpleStringProperty courseName = new SimpleStringProperty();
    private final SimpleStringProperty courseMeeting = new SimpleStringProperty();

    public Course(String courseCode, String courseName, String courseMeeting) {
        this.courseCode.set(courseCode);
        this.courseName.set(courseName);
        this.courseMeeting.set(courseMeeting);
    }

    public String getCourseCode() {
        return courseCode.get();
    }

    public String getCourseName() {
        return courseName.get();
    }

    public String getCourseMeeting() {
        return courseMeeting.get();
    }

    public StringProperty courseCodeProperty() {
        return courseCode;
    }

    public StringProperty courseNameProperty() {
        return courseName;
    }

    public StringProperty courseMeetingProperty() {
        return courseMeeting;
    }
}