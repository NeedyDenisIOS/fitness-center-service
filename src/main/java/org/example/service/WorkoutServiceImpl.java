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

    public WorkoutServiceImpl(WorkoutRepository workoutRepository, WorkoutValidator workoutValidator) {
        this.workoutRepository = workoutRepository;
        this.workoutValidator = workoutValidator;
    }

    @Override
    public Workout addNewWorkout(String title, String trainerName, LocalDateTime schedule, int maxParticipants) {
        workoutValidator.validateBasicParameters(title, trainerName, schedule, maxParticipants);
        Workout workout = new Workout(
                null,
                title,
                trainerName,
                schedule,
                maxParticipants,
                new HashSet<>(),
                Status.SCHEDULED
        );
        workoutRepository.add(workout);
        return workout;
    }

    @Override
    public void deleteWorkout(UUID id) {
        workoutValidator.validateId(id);

        Workout workout = workoutRepository.getById(id);

        workoutValidator.validateStatus(workout.getStatus());
        workoutValidator.validatorCurrentParticipants(workout.getCurrentParticipants());

        workoutRepository.deleteById(id);
    }

    @Override
    public Workout updateTitle(UUID id, String title) {
        workoutValidator.validateId(id);
        workoutValidator.validateTitle(title);

        Workout workout = workoutRepository.getById(id);

        workoutValidator.validatorWorkout(workout);
        workoutValidator.validateStatus(workout.getStatus());
        workoutValidator.validateScheduleNotInPast(workout.getSchedule());

        workout.setTitle(title.trim());
        workoutRepository.save(workout);
        return workout;
    }

    @Override
    public Workout updateSchedule(UUID id, LocalDateTime schedule) {
        workoutValidator.validateId(id);
        workoutValidator.validateSchedule(schedule);

        Workout workout = workoutRepository.getById(id);

        workoutValidator.validatorWorkout(workout);
        workoutValidator.validateStatus(workout.getStatus());
        workoutValidator.validateScheduleNotInPast(workout.getSchedule());

        workout.setSchedule(schedule);
        workoutRepository.save(workout);
        return workout;
    }

    @Override
    public Workout updateMaxParticipants(UUID id, int maxParticipants) {
        workoutValidator.validateId(id);
        workoutValidator.validateMaxParticipants(maxParticipants);

        Workout workout = workoutRepository.getById(id);

        workoutValidator.validatorWorkout(workout);
        workoutValidator.validateStatus(workout.getStatus());
        workoutValidator.validateScheduleNotInPast(workout.getSchedule());

        workout.setMaxParticipants(maxParticipants);
        workoutRepository.save(workout);
        return workout;
    }

    @Override
    public Workout updateWorkout(UUID id, int choice, String title, int maxParticipants, LocalDateTime schedule) {
        workoutValidator.validateId(id);

        Workout workout = workoutRepository.getById(id);

        System.out.println("The menu for changing the workout:\n");
        System.out.println("Enter the desired number to select the field you want to change.\n");
        System.out.println("1. Change the name of the workout.\n");
        System.out.println("2. Change the maximum number of training participants.\n");
        System.out.println("3. Change the training time.\n");
        System.out.println("0. If you want to go back to the main menu.\n");

        return switch (choice) {
            case 1 -> updateTitle(id, title);
            case 2 -> updateMaxParticipants(id, maxParticipants);
            case 3 -> updateSchedule(id, schedule);
            case 0 -> workout;
            default -> throw new IllegalArgumentException("Invalid choice, try again.");
        };
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