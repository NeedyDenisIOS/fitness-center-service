package org.example.controller;

import org.example.model.MembershipType;
import org.example.model.Workout;
import org.example.service.MemberServiceImpl;
import org.example.service.WorkoutServiceImpl;
import org.example.service.impl.MemberService;
import org.example.service.impl.WorkoutService;
import org.example.validation.exception.DataNotFoundException;
import org.example.validation.exception.MemberValidationException;
import org.example.validation.exception.WorkoutValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MainMenu {

    private final MemberService memberService;
    private final WorkoutService workoutService;
    private final Scanner scanner;

    public MainMenu(MemberServiceImpl memberService, WorkoutServiceImpl workoutService) {
        this.memberService = memberService;
        this.workoutService = workoutService;
        this.scanner = new Scanner(System.in);
    }

    public void printMainMenu() throws DataNotFoundException, WorkoutValidationException, MemberValidationException {
        System.out.println("Welcome to the fitness center.");
        System.out.println("1. Adding a new workout to the schedule.");
        System.out.println("2. Deleting a training session by ID.");
        System.out.println("3. Editing training data.");
        System.out.println("4. Adding a new club member.");
        System.out.println("5. Deleting a club member by ID.");
        System.out.println("6. Sign up a club member for a training session.");
        System.out.println("7. Canceling a workout appointment");
        System.out.println("8. Search for workouts by name, coach, or date.");
        System.out.println("9. A list of all workouts");
        System.out.println("10. View the list of members signed up for a specific training session.");
        System.out.println("0. Exit.");
    }

    public int getUserChoice() {
        System.out.println("Please enter you're choice: ");

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Incorrect choice: " + e + " please, try again.");
            return -1;
        }
    }

    public void editWorkoutMenu(UUID id) throws WorkoutValidationException, DataNotFoundException {
        System.out.println("Changing training data: ");
        System.out.println("1. Change title.");
        System.out.println("2. Change schedule.");
        System.out.println("3. Change max participants.");
        System.out.println("0. Exit.");

        int choice = getUserChoice();

        switch (choice) {
            case 1:
                System.out.println("Enter new title: ");
                String title = scanner.nextLine();

                workoutService.updateTitle(id, title);
                System.out.println("Title updated.");
                break;

            case 2:
                System.out.println("Enter new schedule: ");
                LocalDateTime schedule = LocalDateTime.parse(scanner.nextLine());

                workoutService.updateSchedule(id, schedule);
                System.out.println("Schedule updated.");
                break;

            case 3:
                System.out.println("Enter new max participants: ");
                int maxParticipants = Integer.parseInt(scanner.nextLine());

                workoutService.updateMaxParticipants(id, maxParticipants);
                System.out.println("Max participants updated.");
                break;

            case 0:
                System.out.println("Returning to the menu.");
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    public void searchWorkoutMenu() throws WorkoutValidationException, DataNotFoundException {
        System.out.println("Search workout menu:");
        System.out.println("1. Find a workout using the title.");
        System.out.println("2. Find a workout using the trainer name.");
        System.out.println("3. Find a workout using the schedule.");
        System.out.println("0. Exit.");

        int choice = getUserChoice();

        switch (choice) {
            case 1:
                System.out.println("Enter the title: ");
                String title = scanner.nextLine();

                workoutService.findWorkoutByTitle(title);
                break;

            case 2:
                System.out.println("Enter the trainer name: ");
                String trainerName = scanner.nextLine();

                workoutService.findWorkoutByTrainerName(trainerName);
                break;

            case 3:
                System.out.println("Enter the schedule: ");
                LocalDate schedule = LocalDate.parse(scanner.nextLine());

                workoutService.findWorkoutBySchedule(schedule);
                break;

            case 0:
                System.out.println("Returning to the menu.");
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    public void displayWorkoutsMenu() throws WorkoutValidationException, DataNotFoundException {
        System.out.println("Choose a way to sort the training list: ");
        System.out.println("1. Sorted by date.");
        System.out.println("2. Sorted by title.");
        System.out.println("0. Exit.");

        int choice = getUserChoice();

        switch (choice) {
            case 1:
                List<Workout> sortedListByDate = workoutService.getWorkoutsSortedByDate(true);

                System.out.println("All workouts: ");

                for (Workout workout : sortedListByDate) {
                    System.out.println(workout.toString());
                }

                break;
            case 2:
                List<Workout> sortedListByTitle = workoutService.getWorkoutsSortedByTitle(true);

                System.out.println("All workouts: ");

                for (Workout workout : sortedListByTitle) {
                    System.out.println(workout.toString());
                }

                break;

            case 0:
                System.out.println("Returning to the menu.");
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    public void handleChoice(int choice) throws DataNotFoundException, MemberValidationException, WorkoutValidationException {
        switch (choice) {
            case 1:
                System.out.println("Title: ");
                String title = scanner.nextLine();

                System.out.println("Trainer name: ");
                String trainerName = scanner.nextLine();

                System.out.println("Schedule(yyyy-MM-ddTHH:mm): ");
                LocalDateTime schedule = LocalDateTime.parse(scanner.nextLine());

                System.out.println("Max participants: ");
                int maxParticipants = Integer.parseInt(scanner.nextLine());

                workoutService.addNewWorkout(title, trainerName, schedule, maxParticipants);
                System.out.println("Workout added.");
                break;

            case 2:
                System.out.println("Please enter the training ID that you want to delete.");
                UUID deleteWorkoutId = UUID.fromString(scanner.nextLine());

                workoutService.deleteWorkout(deleteWorkoutId);
                System.out.println("Workout was deleted.");
                break;

            case 3:
                System.out.println("Enter the training ID that you want to update.");
                UUID updateWorkoutId = UUID.fromString(scanner.nextLine());

                editWorkoutMenu(updateWorkoutId);
                break;

            case 4:
                System.out.println("Full name: ");
                String fullName = scanner.nextLine();

                System.out.println("Date of birth: ");
                LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine());

                System.out.println("Membership type: ");
                MembershipType type = MembershipType.valueOf(scanner.nextLine().toUpperCase());

                System.out.println("Membership start date: ");
                LocalDate startDate = LocalDate.parse(scanner.nextLine());

                System.out.println("Membership end date: ");
                LocalDate endDate = LocalDate.parse(scanner.nextLine());

                memberService.addNewMember(fullName, dateOfBirth, type, startDate, endDate);
                System.out.println("Member added.");
                break;

            case 5:
                System.out.println("Please enter the member ID that you want to delete.");
                UUID deleteMemberId = UUID.fromString(scanner.nextLine());

                memberService.deleteMember(deleteMemberId);
                System.out.println("Member was deleted.");
                break;

            case 6:
                System.out.println("Please enter the member and training ID for enroll.");
                UUID enrollMemberId = UUID.fromString(scanner.nextLine());
                UUID enrollWorkoutId = UUID.fromString(scanner.nextLine());

                memberService.enrollMember(enrollMemberId, enrollWorkoutId);
                System.out.println("You have successfully signed up for a training session.");
                break;

            case 7:
                System.out.println("Please enter the member and training ID for enroll.");
                UUID cancelMemberId = UUID.fromString(scanner.nextLine());
                UUID cancelWorkoutId = UUID.fromString(scanner.nextLine());

                memberService.cancelEnroll(cancelMemberId, cancelWorkoutId);
                System.out.println("You have successfully cancelled your training appointment.");
                break;

            case 8:
                searchWorkoutMenu();
                break;

            case 9:
                System.out.println("The list of all workouts: ");
                displayWorkoutsMenu();
                break;

            case 10:
                System.out.println("Please enter the workout ID where you want see the list of members.");
                UUID workoutId = UUID.fromString(scanner.nextLine());

                memberService.displayCurrentParticipants(workoutId);
                break;

            case 0:
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }
}

