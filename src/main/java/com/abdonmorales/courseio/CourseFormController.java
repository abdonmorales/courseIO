package com.abdonmorales.courseio;

import com.abdonmorales.courseio.model.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.Notifications;
//import javafx.scene.control.Label;

public class CourseFormController {
    @FXML
    private TextField courseCodeField;

    @FXML
    private TextField courseNameField;

    @FXML
    private TextField courseMeetingField;

    @FXML
    private TableView<Course> courseTable;

    @FXML
    private TableColumn<Course, String> codeColumn;

    @FXML
    private TableColumn<Course, String> nameColumn;

    @FXML
    private TableColumn<Course, String> meetingColumn;

    private final ObservableList<Course> courses = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        codeColumn.setCellValueFactory(cell -> cell.getValue().courseCodeProperty());
        nameColumn.setCellValueFactory(cell -> cell.getValue().courseNameProperty());
        meetingColumn.setCellValueFactory(cell -> cell.getValue().courseMeetingProperty());
        courseTable.setItems(courses);
    }

    @FXML
    private void onAddCourse() {
        String courseCode = courseCodeField.getText().trim();
        String courseName = courseNameField.getText().trim();
        String courseMeeting = courseMeetingField.getText().trim();

        if (courseCode.isEmpty() || courseName.isEmpty()) {
            Notifications.create()
                    .title("Validation Error!")
                    .text("Course code and name are required!")
                    .showWarning();
            return;
        }

        courses.add(new Course(courseCode, courseName, courseMeeting));
        courseCodeField.clear();
        courseNameField.clear();
        courseMeetingField.clear();
    }

    @FXML
    private void onRemoveSelectedCourse() {
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            courses.remove(selectedCourse);
        } else {
            Notifications.create()
                    .title("Nothing selected.")
                    .text("Please select a course to remove!")
                    .showInformation();
        }
    }
}