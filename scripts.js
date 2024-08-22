document.getElementById('course-form').addEventListener('submit', function(event) {
    event.preventDefault();

    // Get form values
    const courseName = document.getElementById('course-name').value;
    const courseDays = Array.from(document.querySelectorAll('input[name="day"]:checked')).map(checkbox => checkbox.value);
    const courseStartTime = document.getElementById('course-start-time').value;
    const courseEndTime = document.getElementById('course-end-time').value;

    // Add the course to the table (display in 12-hour format) and store in Local Storage (store in 24-hour format)
    courseDays.forEach(day => {
        addCourseToTable(courseName, day, convertTo12HourFormat(courseStartTime), convertTo12HourFormat(courseEndTime));
        saveCourse(courseName, day, courseStartTime, courseEndTime);
    });

    // Clear form fields
    document.getElementById('course-form').reset();
    // Uncheck all checkboxes
    document.querySelectorAll('input[name="day"]').forEach(checkbox => checkbox.checked = false);
});

function convertTo12HourFormat(time) {
    let [hours, minutes] = time.split(':');
    hours = parseInt(hours);
    const ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12 || 12; // Convert to 12-hour format
    return `${hours}:${minutes} ${ampm}`;
}

function addCourseToTable(courseName, courseDay, courseStartTime, courseEndTime) {
    const tableBody = document.getElementById('schedule-body');
    const newRow = document.createElement('tr');

    newRow.innerHTML = `
        <td>${courseName}</td>
        <td>${courseDay}</td>
        <td>${courseStartTime} - ${courseEndTime}</td>
        <td>
            <button class="edit-btn">Edit</button>
            <button class="delete-btn">Delete</button>
        </td>
    `;

    tableBody.appendChild(newRow);

    // Add event listeners for edit and delete buttons
    newRow.querySelector('.edit-btn').addEventListener('click', function() {
        editCourse(newRow, courseName, courseDay, courseStartTime, courseEndTime);
    });

    newRow.querySelector('.delete-btn').addEventListener('click', function() {
        deleteCourse(newRow, courseName, courseDay, courseStartTime, courseEndTime);
    });
}

function editCourse(row, courseName, courseDay, courseStartTime, courseEndTime) {
    // Prefill the form with the existing course details
    document.getElementById('course-name').value = courseName;
    document.querySelectorAll('input[name="day"]').forEach(checkbox => {
        checkbox.checked = (checkbox.value === courseDay);
    });
    document.getElementById('course-start-time').value = convertTo24HourFormat(courseStartTime);
    document.getElementById('course-end-time').value = convertTo24HourFormat(courseEndTime);

    // Remove the existing row
    row.remove();

    // Remove the course from Local Storage
    removeCourseFromStorage(courseName, courseDay, courseStartTime, courseEndTime);
}

function deleteCourse(row, courseName, courseDay, courseStartTime, courseEndTime) {
    row.remove();

    // Remove the course from Local Storage
    removeCourseFromStorage(courseName, courseDay, convertTo24HourFormat(courseStartTime), convertTo24HourFormat(courseEndTime));
}

function saveCourse(courseName, courseDay, courseStartTime, courseEndTime) {
    const courses = JSON.parse(localStorage.getItem('courses')) || [];
    courses.push({ courseName, courseDay, courseStartTime, courseEndTime });
    localStorage.setItem('courses', JSON.stringify(courses));
}

function loadCourses() {
    const courses = JSON.parse(localStorage.getItem('courses')) || [];
    
    // Clear the table body before loading
    const tableBody = document.getElementById('schedule-body');
    tableBody.innerHTML = ''; // This clears all existing rows

    courses.forEach(course => {
        // Display the stored courses in 12-hour format
        addCourseToTable(course.courseName, course.courseDay, convertTo12HourFormat(course.courseStartTime), convertTo12HourFormat(course.courseEndTime));
    });
}

function removeCourseFromStorage(courseName, courseDay, courseStartTime, courseEndTime) {
    let courses = JSON.parse(localStorage.getItem('courses')) || [];
    courses = courses.filter(course => 
        course.courseName !== courseName || 
        course.courseDay !== courseDay || 
        course.courseStartTime !== courseStartTime || 
        course.courseEndTime !== courseEndTime
    );
    localStorage.setItem('courses', JSON.stringify(courses));
}

function convertTo24HourFormat(time) {
    let [timePart, modifier] = time.split(' ');
    let [hours, minutes] = timePart.split(':');
    hours = parseInt(hours);

    if (modifier === 'PM' && hours !== 12) {
        hours += 12;
    }
    if (modifier === 'AM' && hours === 12) {
        hours = 0;
    }

    return `${hours.toString().padStart(2, '0')}:${minutes}`;
}

// Load existing courses from Local Storage when the page loads
document.addEventListener('DOMContentLoaded', function() {
    loadCourses();
});