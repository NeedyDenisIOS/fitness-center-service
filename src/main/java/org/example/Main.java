package org.example;

import org.example.controller.MainMenu;
import org.example.repository.MemberRepository;
import org.example.repository.WorkoutRepository;
import org.example.service.MemberServiceImpl;
import org.example.service.WorkoutServiceImpl;
import org.example.util.FileDataService;
import org.example.validation.MemberValidator;
import org.example.validation.WorkoutValidator;
import org.example.validation.exception.DataNotFoundException;
import org.example.validation.exception.MemberValidationException;
import org.example.validation.exception.WorkoutValidationException;

public class Main {

    public static void main(String[] args) throws DataNotFoundException, WorkoutValidationException, MemberValidationException {

        MemberValidator memberValidator = new MemberValidator();
        WorkoutValidator workoutValidator = new WorkoutValidator();

        MemberRepository memberRepository = MemberRepository.getInstance();
        WorkoutRepository workoutRepository = WorkoutRepository.getInstance();

        WorkoutServiceImpl workoutService = new WorkoutServiceImpl(workoutValidator);
        MemberServiceImpl memberService = new MemberServiceImpl(memberValidator, workoutValidator);


        FileDataService fileDataService = new FileDataService("D:\\Java\\fitness-center-service\\src\\main\\java\\org\\example", memberRepository, workoutRepository);

        MainMenu mainMenu = new MainMenu(memberService, workoutService);

        fileDataService.loadData();

        boolean running = true;

        while (running) {
            try {
                mainMenu.printMainMenu();

                int choice = mainMenu.getUserChoice();

                if (choice == 0) {
                    running = false;
                    System.out.println("\n Exiting...");
                } else if (choice > 0) {
                    mainMenu.handleChoice(choice);
                }

            } catch (DataNotFoundException e) {
                System.out.println("Not found: " + e.getMessage());
            } catch (WorkoutValidationException e) {
                System.out.println("Workout error: " + e.getMessage());
            } catch (MemberValidationException e) {
                System.out.println("Member error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
        fileDataService.saveData();
    }


}
