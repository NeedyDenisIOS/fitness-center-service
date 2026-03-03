package org.example.validation;

import org.example.model.Status;
import org.example.model.Workout;
import org.example.validation.exception.WorkoutValidationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class WorkoutValidator {

    public void validateId(UUID id) throws WorkoutValidationException {
        if (id == null) {
            throw new WorkoutValidationException("The id of the workout is required.");
        }
    }

    public void validateTitle(String title) throws WorkoutValidationException {
        if (title == null || title.trim().isEmpty()) {
            throw new WorkoutValidationException("The title of the workout is required.");
        }
    }

    public void validateTrainerName(String trainerName) throws WorkoutValidationException {
        if (trainerName == null || trainerName.trim().isEmpty()) {
            throw new WorkoutValidationException("The trainer name of the workout is required.");
        } else {
            if (trainerName.trim().length() > 100) {
                throw new WorkoutValidationException("The trainer name must not exceed 100 characters.");
            }
        }
    }

    public void validateSchedule(LocalDateTime schedule) throws WorkoutValidationException {
        if (schedule == null) {
            throw new WorkoutValidationException("The schedule of the workout is required.");
        }
    }

    public void validateScheduleNotInPast(LocalDateTime schedule) throws WorkoutValidationException {
        validateSchedule(schedule);
        if (schedule.isBefore(LocalDateTime.now())) {
            throw new WorkoutValidationException("Cannot modify workout in the past");
        }
    }

    public void validateStatus(Status status) throws WorkoutValidationException {
        if (status != Status.SCHEDULED) {
            throw new WorkoutValidationException("You cannot change an already started or cancelled workout.");
        }
    }

    public void validateMaxParticipants(int maxParticipants) throws WorkoutValidationException {
        if (maxParticipants <= 0) {
            throw new WorkoutValidationException("The number of participants must be positive");
        }
    }

    public void validateCurrentParticipants(Set<UUID> currentParticipants) throws WorkoutValidationException {
        if (!currentParticipants.isEmpty()) {
            throw new WorkoutValidationException("List of current participants must be empty for delete");
        }
    }

    public void validatorWorkoutList(List<Workout> workouts) throws WorkoutValidationException {
        if (workouts.isEmpty()) {
            throw new WorkoutValidationException("No workouts found");
        }
    }

    public void validateBasicParameters(String title, String trainerName, LocalDateTime schedule, int maxParticipants) throws WorkoutValidationException {
        validateTitle(title);
        validateTrainerName(trainerName);
        validateSchedule(schedule);
        validateMaxParticipants(maxParticipants);
    }

    public void validateMaxParticipants(int maxParticipants, int currentParticipants) throws WorkoutValidationException {
        if (currentParticipants == maxParticipants) {
            throw new WorkoutValidationException("There are no available seats for this workout.");
        }
        if (currentParticipants > maxParticipants) {
            throw new WorkoutValidationException("The number of participants is more than required.");
        }
    }

}
