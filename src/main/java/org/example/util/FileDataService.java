package org.example.util;

import org.example.model.Member;
import org.example.model.Workout;
import org.example.repository.MemberRepository;
import org.example.repository.WorkoutRepository;

import java.util.List;

public class FileDataService {

    private static final String MEMBERS_FILE = "members.json";
    private static final String WORKOUTS_FILE = "workouts.json";

    private final JsonFileHandler jsonFileHandler;

    public FileDataService(String dataDirectory) {
        this.jsonFileHandler = new JsonFileHandler(dataDirectory);
    }

    public void loadData() {
        MemberRepository memberRepository = MemberRepository.getInstance();
        WorkoutRepository workoutRepository = WorkoutRepository.getInstance();

        List<Member> members = jsonFileHandler.loadList(MEMBERS_FILE, Member.class);

        for (Member member : members) {
            memberRepository.addMember(member);
        }

        List<Workout> workouts = jsonFileHandler.loadList(WORKOUTS_FILE, Workout.class);

        for (Workout workout : workouts) {
            workoutRepository.addWorkout(workout);
        }
    }

    public void saveData() {
        MemberRepository memberRepository = MemberRepository.getInstance();
        WorkoutRepository workoutRepository = WorkoutRepository.getInstance();

        List<Member> members = memberRepository.getListOfMembers();
        List<Workout> workouts = workoutRepository.getListOfWorkouts();

        jsonFileHandler.saveList(MEMBERS_FILE, members);
        jsonFileHandler.saveList(WORKOUTS_FILE, workouts);
    }
}
