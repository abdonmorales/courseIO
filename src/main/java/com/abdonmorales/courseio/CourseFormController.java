package com.abdonmorales.courseio;

import com.abdonmorales.courseio.model.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.Notifications;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.util.EnumSet;
import com.abdonmorales.courseio.Time;

public class CourseFormController {
    @FXML
    private TextField courseCodeField;

    @FXML
    private TextField courseNameField;

    @FXML
    private TextField courseNotesField;

    @FXML private CheckBox mondayCheckBox;
    @FXML private CheckBox tuesdayCheckBox;
    @FXML private CheckBox wednesdayCheckBox;
    @FXML private CheckBox thursdayCheckBox;
    @FXML private CheckBox fridayCheckBox;

    @FXML private Spinner<Integer> startHourSpinner;
    @FXML private Spinner<Integer> startMinuteSpinner;
    @FXML private Spinner<String> startAmPmSpinner;
    @FXML private Spinner<Integer> endHourSpinner;
    @FXML private Spinner<Integer> endMinuteSpinner;
    @FXML private Spinner<String> endAmPmSpinner;

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

        courseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        startHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 12, 8));
        startHourSpinner.setEditable(false);
        startMinuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        startMinuteSpinner.setEditable(false);
        startAmPmSpinner.setValueFactory(new SpinnerValueFactory.
                ListSpinnerValueFactory<>(FXCollections.
                observableArrayList("AM", "PM")));
        startAmPmSpinner.setEditable(false);
        endHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 12,6));
        endHourSpinner.setEditable(false);
        endMinuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        endMinuteSpinner.setEditable(false);
        endAmPmSpinner.setValueFactory(new SpinnerValueFactory.
                ListSpinnerValueFactory<>(FXCollections.
                observableArrayList("AM", "PM")));
    }

    @FXML
    private void onAddCourse() {
        String courseCode = courseCodeField.getText().trim();
        String courseName = courseNameField.getText().trim();
        String courseNotes = courseNotesField.getText().trim();

        if (courseCode.isEmpty() || courseName.isEmpty()) {
            Notifications.create()
                    .title("Validation Error!")
                    .text("Course code and name are required!")
                    .showWarning();
            return;
        }

        List<String> days = new ArrayList<>();
        if (mondayCheckBox.isSelected()) {
            days.add("M");
        }
        if (tuesdayCheckBox.isSelected()) {
            days.add("T");
        }
        if (wednesdayCheckBox.isSelected()) {
            days.add("W");
        }
        if (thursdayCheckBox.isSelected()) {
            days.add("Th");
        }
        if (fridayCheckBox.isSelected()) {
            days.add("F");
        }

        String daysPart = String.join(",", days);

        int startHour = startHourSpinner.getValue();
        int startMinute = startMinuteSpinner.getValue();
        String startAmPm = startAmPmSpinner.getValue();
        int endHour = endHourSpinner.getValue();
        int endMinute = endMinuteSpinner.getValue();
        String endAmPm = endAmPmSpinner.getValue();

        // Build a set of DayOfWeek from the checkboxes
        EnumSet<DayOfWeek> daysSet = EnumSet.noneOf(DayOfWeek.class);
        if (mondayCheckBox.isSelected())    daysSet.add(DayOfWeek.MONDAY);
        if (tuesdayCheckBox.isSelected())   daysSet.add(DayOfWeek.TUESDAY);
        if (wednesdayCheckBox.isSelected()) daysSet.add(DayOfWeek.WEDNESDAY);
        if (thursdayCheckBox.isSelected())  daysSet.add(DayOfWeek.THURSDAY);
        if (fridayCheckBox.isSelected())    daysSet.add(DayOfWeek.FRIDAY);

        // Convert to 24-hour clock
        int sh24 = startAmPm.equals("AM") ? (startHour % 12) : (startHour % 12) + 12;
        int eh24 = endAmPm.equals("AM")   ? (endHour   % 12) : (endHour   % 12) + 12;

        // Create LocalTime instances
        LocalTime startTime = LocalTime.of(sh24, startMinute);
        LocalTime endTime   = LocalTime.of(eh24, endMinute);

        // Create Time object
        Time meetingTime = new Time(daysSet, startTime, endTime);

        courses.add(new Course(courseCode, courseName, courseNotes, meetingTime));
        courseCodeField.clear();
        courseNameField.clear();
        courseNotesField.clear();
        mondayCheckBox.setSelected(false);
        tuesdayCheckBox.setSelected(false);
        wednesdayCheckBox.setSelected(false);
        thursdayCheckBox.setSelected(false);
        fridayCheckBox.setSelected(false);
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