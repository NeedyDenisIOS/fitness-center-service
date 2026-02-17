package org.example.service.impl;

import org.example.model.Workout;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface WorkoutService {

    Workout addNewWorkout(String title, String trainerName, LocalDateTime schedule, int maxParticipants);
    Workout updateTitle(UUID id, String title);
    Workout updateSchedule(UUID id, LocalDateTime schedule);
    Workout updateMaxParticipants(UUID id, int maxParticipants);
    Workout updateWorkout(UUID id, int choice, String title, int maxParticipants, LocalDateTime schedule);

    void deleteWorkout(UUID id);
    void displayAllFoundWorkouts(List<Workout> workouts);
    void findWorkoutByTitle(String title);
    void findWorkoutByTrainerName(String trainerName);
    void findWorkoutBySchedule(LocalDate schedule);
    void displayAllWorkouts();

    List<Workout> getWorkoutsSortedByDate(boolean ascending);
    List<Workout> getWorkoutsSortedByTitle(boolean ascending);
}
