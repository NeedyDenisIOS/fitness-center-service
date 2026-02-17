package org.example.validation;

import org.example.model.Status;
import org.example.model.Workout;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class WorkoutValidator {

    public void validateId(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("The id of the workout is required.");
        }
    }

    public void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("The title of the workout is required.");
        }
    }

    public void validateTrainerName(String trainerName) {
        if (trainerName == null || trainerName.trim().isEmpty()) {
            throw new IllegalArgumentException("The trainer name of the workout is required.");
        } else {
            if (trainerName.trim().length() > 100) {
                throw new IllegalArgumentException("The trainer name must not exceed 100 characters.");
            }
        }
    }

    public void validateSchedule(LocalDateTime schedule) {
        if (schedule == null) {
            throw new IllegalArgumentException("The schedule of the workout is required.");
        }
    }

    public void validateScheduleNotInPast(LocalDateTime schedule) {
        if (schedule.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot modify workout in the past");
        }
    }

    public void validateStatus(Status status) {
        if (status != Status.SCHEDULED) {
            throw new IllegalArgumentException("You cannot change an already started or cancelled workout.");
        }
    }

    public void validateMaxParticipants(int maxParticipants) {
        if (maxParticipants <= 0) {
            throw new IllegalArgumentException("The number of participants must be positive");
        }
    }

    public void validatorCurrentParticipants(Set<UUID> currentParticipants) {
        if (!currentParticipants.isEmpty()) {
            throw new IllegalArgumentException("List of current participants must be empty for delete");
        }
    }

    public void validatorWorkout(Workout workout) {
        if (workout == null) {
            throw new IllegalArgumentException("Workout not found");
        }
    }

    public void validatorWorkoutList(List<Workout> workouts) {
        if (workouts.isEmpty()) {
            throw new IllegalArgumentException("No workouts found");
        }
    }

    public void validateBasicParameters(String title, String trainerName, LocalDateTime schedule, int maxParticipants) {
        validateTitle(title);
        validateTrainerName(trainerName);
        validateSchedule(schedule);
        validateMaxParticipants(maxParticipants);
    }
}
