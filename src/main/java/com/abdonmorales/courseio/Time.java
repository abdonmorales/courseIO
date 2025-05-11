package com.abdonmorales.courseio;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.Set;

/**
 * Represents a class meeting time on one or more days of the week.
 * Example: MWF 4pm–6pm would be
 *   Time slot = new Time(
 *     EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY),
 *     LocalTime.of(16, 0),
 *     LocalTime.of(18, 0));
 */
public class Time {
    private final Set<DayOfWeek> days;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public Time(Set<DayOfWeek> days, LocalTime startTime, LocalTime endTime) {
        if (days == null || days.isEmpty()) {
            throw new IllegalArgumentException("Days must not be null or empty");
        }
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start and end times must not be null");
        }
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        this.days = EnumSet.copyOf(days);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Set<DayOfWeek> getDays() {
        return EnumSet.copyOf(days);
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        // Formats days as "MONDAY/WEDNESDAY/FRIDAY" and times as "16:00–18:00"
        String daysStr = days.toString()
                .replaceAll("^\\[|\\]$", "")
                .replaceAll(", ", "/");
        return daysStr + "\n" + startTime + "–" + endTime;
    }
}