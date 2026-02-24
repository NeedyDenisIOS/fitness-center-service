package org.example.service;

import org.example.model.Status;
import org.example.model.Workout;
import org.example.repository.WorkoutRepository;
import org.example.service.impl.WorkoutService;
import org.example.validation.WorkoutValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/*
    Добавление новой тренировки в расписание.
    Удаление тренировки по ID (с проверкой на наличие записанных участников).
    Редактирование данных о тренировке (например, изменение времени или максимального количества участников).
    Поиск тренировок по названию, тренеру или дате.
    Список всех тренировок, отсортированных по различным критериям (дата, название, тренер).
 */
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final WorkoutValidator workoutValidator;

    public WorkoutServiceImpl(WorkoutValidator workoutValidator) {
        this.workoutRepository = WorkoutRepository.getInstance();
        this.workoutValidator = workoutValidator;
    }

    @Override
    public Workout addNewWorkout(String title, String trainerName, LocalDateTime schedule, int maxParticipants) {
        workoutValidator.validateBasicParameters(title, trainerName, schedule, maxParticipants);
        Workout workout = new Workout(
                title,
                trainerName,
                schedule,
                maxParticipants,
                Status.SCHEDULED
        );
        workoutRepository.add(workout);
        return workout;
    }

    @Override
    public void deleteWorkout(UUID id) {
        workoutValidator.validateId(id);

        Workout workout = workoutValidator.validatorWorkout(workoutRepository.getById(id));

        workoutValidator.validateStatus(workout.getStatus());
        workoutValidator.validateCurrentParticipants(workout.getCurrentParticipants());

        workoutRepository.deleteById(id);
    }

    @Override
    public Workout updateTitle(UUID id, String title) {
        workoutValidator.validateId(id);
        workoutValidator.validateTitle(title);

        Workout workout = workoutValidator.validatorWorkout(workoutRepository.getById(id));

        workoutValidator.validateStatus(workout.getStatus());
        workoutValidator.validateScheduleNotInPast(workout.getSchedule());

        workout.setTitle(title.trim());

        return workout;
    }

    @Override
    public Workout updateSchedule(UUID id, LocalDateTime schedule) {
        workoutValidator.validateId(id);
        workoutValidator.validateSchedule(schedule);

        Workout workout = workoutValidator.validatorWorkout(workoutRepository.getById(id));

        workoutValidator.validateStatus(workout.getStatus());
        workoutValidator.validateScheduleNotInPast(workout.getSchedule());

        workout.setSchedule(schedule);

        return workout;
    }

    @Override
    public Workout updateMaxParticipants(UUID id, int maxParticipants) {
        workoutValidator.validateId(id);
        workoutValidator.validateMaxParticipants(maxParticipants);

        Workout workout = workoutValidator.validatorWorkout(workoutRepository.getById(id));

        workoutValidator.validateStatus(workout.getStatus());
        workoutValidator.validateScheduleNotInPast(workout.getSchedule());

        workout.setMaxParticipants(maxParticipants);

        return workout;
    }

    @Override
    public void displayAllFoundWorkouts(List<Workout> workouts) {
        System.out.println("Found: " + workouts.size() + " workouts.");
        for (Workout workout : workouts) {
            System.out.println(workout.toString());
        }
    }

    @Override
    public void findWorkoutByTitle(String title) {
        workoutValidator.validateTitle(title);


        List<Workout> workouts = workoutRepository.findByTitleContaining(title);

        workoutValidator.validatorWorkoutList(workouts);

        displayAllFoundWorkouts(workouts);
    }

    @Override
    public void findWorkoutByTrainerName(String trainerName) {
        workoutValidator.validateTrainerName(trainerName);

        List<Workout> workouts = workoutRepository.findByTrainerNameContaining(trainerName);

        workoutValidator.validatorWorkoutList(workouts);

        displayAllFoundWorkouts(workouts);
    }

    @Override
    public void findWorkoutBySchedule(LocalDate schedule) {
        List<Workout> workouts = workoutRepository.findByScheduleContaining(schedule);

        workoutValidator.validatorWorkoutList(workouts);

        displayAllFoundWorkouts(workouts);
    }

    @Override
    public void displayAllWorkouts() {
        List<Workout> workouts = workoutRepository.getListOfWorkouts();

        for (Workout workout : workouts) {
            System.out.println(workout.toString());
        }
    }

    @Override
    public List<Workout> getWorkoutsSortedByDate(boolean ascending) {
        List<Workout> workouts = workoutRepository.getListOfWorkouts();

        if (ascending) {
            return workouts.stream()
                    .sorted(Comparator.comparing(Workout::getSchedule))
                    .collect(Collectors.toList());
        } else {
            return workouts.stream()
                    .sorted(Comparator.comparing(Workout::getSchedule).reversed())
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Workout> getWorkoutsSortedByTitle(boolean ascending) {
        List<Workout> workouts = workoutRepository.getListOfWorkouts();
        return workouts.stream()
                .sorted(Comparator.comparing(Workout::getTitle))
                .collect(Collectors.toList());
    }
}