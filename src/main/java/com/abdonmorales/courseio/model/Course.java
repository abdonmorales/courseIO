package com.abdonmorales.courseio.model;
import com.abdonmorales.courseio.Time;
import javafx.beans.property.*;

public class Course {
    private final SimpleStringProperty courseCode = new SimpleStringProperty();
    private final SimpleStringProperty courseName = new SimpleStringProperty();
    private final SimpleObjectProperty<Time> courseMeeting = new SimpleObjectProperty<>();
    private final SimpleStringProperty courseNote = new SimpleStringProperty();

    public Course(String courseCode, String courseName, String courseNote, Time courseTime) {
        this.courseCode.set(courseCode);
        this.courseName.set(courseName);
        this.courseNote.set(courseNote);
        this.courseMeeting.set(courseTime);
    }

    public String getCourseCode() {
        return courseCode.get();
    }

    public String getCourseName() {
        return courseName.get();
    }

    public String getCourseNote() {
        return courseNote.get();
    }

    public String getCourseMeeting() {
        return courseMeeting.get().toString();
    }

    public StringProperty courseCodeProperty() {
        return courseCode;
    }

    public StringProperty courseNameProperty() {
        return courseName;
    }

    public StringProperty courseNoteProperty() {
        return courseNote;
    }

    public ReadOnlyStringProperty courseMeetingProperty() {
        return new ReadOnlyStringWrapper(getCourseMeeting());
    }
}