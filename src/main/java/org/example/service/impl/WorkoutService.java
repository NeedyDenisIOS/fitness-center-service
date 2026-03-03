package org.example.service.impl;

import org.example.model.Workout;
import org.example.validation.WorkoutValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface WorkoutService {

    Workout addNewWorkout(String title, String trainerName, LocalDateTime schedule, int maxParticipants);
    Workout updateTitle(UUID id, String title) throws WorkoutValidationException;
    Workout updateSchedule(UUID id, LocalDateTime schedule) throws WorkoutValidationException;
    Workout updateMaxParticipants(UUID id, int maxParticipants) throws WorkoutValidationException;

    void deleteWorkout(UUID id) throws WorkoutValidationException;
    void displayAllFoundWorkouts(List<Workout> workouts);
    void findWorkoutByTitle(String title);
    void findWorkoutByTrainerName(String trainerName);
    void findWorkoutBySchedule(LocalDate schedule);
    void displayAllWorkouts();

    List<Workout> getWorkoutsSortedByDate(boolean ascending);
    List<Workout> getWorkoutsSortedByTitle(boolean ascending);
}
