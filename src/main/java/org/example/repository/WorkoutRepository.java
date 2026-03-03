package org.example.repository;

import org.example.model.Workout;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class WorkoutRepository {

    private final List<Workout> workouts = new LinkedList<>();

    private static final WorkoutRepository INSTANCE = new WorkoutRepository();

    private WorkoutRepository() {}

    public static WorkoutRepository getInstance() {
        return INSTANCE;
    }

    public void add(Workout workout) {
        workouts.add(workout);
    }

    public Optional<Workout> getById(UUID id) {
        for (Workout workout : workouts) {
            if (workout.getId().equals(id)) {
                return Optional.of(workout);
            }
        }
        return Optional.empty();
    }

    public List<Workout> getListOfWorkouts() {
        return workouts;
    }

    public Optional<Workout> getByTitle(String title) {
        for (Workout workout : workouts) {
            if (workout.getTitle().equals(title)) {
                return Optional.of(workout);
            }
        }
        return Optional.empty();
    }

    public List<Workout> findByTitleContaining(String title) {
        String searchTerm = title.trim().toLowerCase();

        return workouts.stream()
                .filter(workout -> workout.getTitle().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    public List<Workout> findByTrainerNameContaining(String trainerName) {
        String searchTerm = trainerName.trim().toLowerCase();

        return workouts.stream()
                .filter(workout -> workout.getTrainerName().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    public List<Workout> findByScheduleContaining(LocalDate schedule) {
        return workouts.stream()
                .filter(workout -> workout.getSchedule().toLocalDate().equals(schedule))
                .collect(Collectors.toList());
    }

    public void deleteById(UUID id) {
        workouts.removeIf(workout -> workout.getId().equals(id));
    }
}
