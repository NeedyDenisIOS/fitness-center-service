package org.example.service;

import org.example.model.Member;
import org.example.model.Workout;
import org.example.model.MembershipType;
import org.example.repository.MemberRepository;
import org.example.repository.WorkoutRepository;
import org.example.service.impl.MemberService;
import org.example.validation.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/*
	Добавление нового члена клуба.
	Удаление члена клуба по ID (с проверкой на активные записи).
	Запись члена клуба на тренировку (проверка срока действия абонемента,
    свободных мест в maxParticipants,
    добавление ID тренировки в список члена и ID члена в список тренировки).
    Отмена записи на тренировку (удаление ID из соответствующих списков).
    Проверка актуальности абонементов у членов клуба (автоматическая проверка membershipEndDate).
    Просмотр списка членов, записанных на конкретную тренировку.
 */
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final WorkoutRepository workoutRepository;
    private final MemberValidator memberValidator;
    private final WorkoutValidator workoutValidator;

    public MemberServiceImpl(MemberValidator memberValidator, WorkoutValidator workoutValidator) {
        this.memberRepository = MemberRepository.getInstance();
        this.workoutRepository = WorkoutRepository.getInstance();
        this.memberValidator = memberValidator;
        this.workoutValidator = workoutValidator;
    }

    @Override
    public Member addNewMember(String fullName, LocalDate dateOfBirth, MembershipType type, LocalDate membershipStartDate, LocalDate membershipEndDate) throws MemberValidationException {
        memberValidator.validateBasicParameters(fullName, dateOfBirth, type, membershipStartDate, membershipEndDate);
        Member member = new Member(
                fullName,
                dateOfBirth,
                type,
                membershipStartDate,
                membershipEndDate
        );
        memberRepository.addMember(member);
        return member;
    }

    @Override
    public void deleteMember(UUID memberId) throws MemberValidationException {
        memberValidator.validateId(memberId);

        Member member = memberRepository.getById(memberId)
                .orElseThrow(() -> new DataNotFoundException("Member", memberId));

        memberValidator.validateActiveTrainings(member.getActiveTrainings());
        memberRepository.deleteById(memberId);
    }

    @Override
    public void enrollMember(UUID memberId, UUID workoutId) throws WorkoutValidationException, MemberValidationException {
        memberValidator.validateId(memberId);
        workoutValidator.validateId(workoutId);

        Workout workout = workoutRepository.getById(workoutId)
                .orElseThrow(() -> new DataNotFoundException("Workout", workoutId));

        Member member = memberRepository.getById(memberId)
                        .orElseThrow(() -> new DataNotFoundException("Member", memberId));

        memberValidator.validateSubscriptionActivity(member.getMembershipEndDate());
        workoutValidator.validateMaxParticipants(workout.getMaxParticipants(), workout.getParticipantsCount());

        memberValidator.validateNotAlreadyEnrolled(member.getActiveTrainings(), workoutId);
        member.addTraining(workoutId);
        workout.addMember(memberId);
    }

    @Override
    public void cancelEnroll(UUID memberId, UUID workoutId) throws WorkoutValidationException, MemberValidationException {
        memberValidator.validateId(memberId);
        workoutValidator.validateId(workoutId);

        Workout workout = workoutRepository.getById(workoutId)
                .orElseThrow(() -> new DataNotFoundException("Workout", workoutId));

        Member member = memberRepository.getById(memberId)
                .orElseThrow(() -> new DataNotFoundException("Member", memberId));

        memberValidator.validateMemberIsEnrolled(member.getActiveTrainings(), workoutId);

        member.deleteTraining(workoutId);
        workout.deleteMember(memberId);
    }

    @Override
    public void checkSubscription(UUID memberId) throws MemberValidationException {
        memberValidator.validateId(memberId);

        Member member = memberRepository.getById(memberId)
                .orElseThrow(() -> new DataNotFoundException("Member", memberId));

        memberValidator.validateSubscriptionActivity(member.getMembershipEndDate());

        System.out.println("Your subscription is valid until " + member.getMembershipEndDate());
    }

    @Override
    public void displayCurrentParticipants(UUID workoutId) throws WorkoutValidationException {
        workoutValidator.validateId(workoutId);

        Workout workout = workoutRepository.getById(workoutId)
                .orElseThrow(() -> new DataNotFoundException("Workout", workoutId));

        Set<UUID> currentParticipants = workout.getCurrentParticipants();

        currentParticipants.stream()
                .map(memberRepository::getById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(member -> System.out.println(member.toString()));
    }
}
