package org.example.repository;

import org.example.model.Workout;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class WorkoutRepository {

    private final LinkedList<Workout> workouts = new LinkedList<>();

    public void add(Workout workout) {
        workouts.add(workout);
    }

    public Workout getById(UUID id) {
        for (Workout workout : workouts) {
            if (workout.getId().equals(id)) {
                return workout;
            }
        }
        return null;
    }

    public List<Workout> getListOfWorkouts() {
        return workouts;
    }

    public Workout getByTitle(String title) {
        for (Workout workout : workouts) {
            if (workout.getTitle().equals(title)) {
                return workout;
            }
        }
        return null;
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

    public void save(Workout updateWorkout) {
        for (int i = 0; i < workouts.size(); i++) {
            if (workouts.get(i).getId().equals(updateWorkout.getId())) {
                workouts.set(i, updateWorkout);
                return;
            }
        }
        workouts.add(updateWorkout);
    }


}
