package org.example.service.impl;

import org.example.model.Member;
import org.example.model.MembershipType;
import org.example.validation.MemberValidationException;
import org.example.validation.WorkoutValidationException;

import java.time.LocalDate;
import java.util.UUID;

public interface MemberService {

    Member addNewMember(String fullName, LocalDate dateOfBirth, MembershipType type, LocalDate membershipStartDate, LocalDate membershipEndDate) throws MemberValidationException;

    void deleteMember(UUID id) throws MemberValidationException;
    void enrollMember(UUID memberId, UUID workoutId) throws WorkoutValidationException, MemberValidationException;
    void cancelEnroll(UUID memberId, UUID workoutId) throws WorkoutValidationException, MemberValidationException;
    void checkSubscription(UUID memberId) throws MemberValidationException;
    void displayCurrentParticipants(UUID workoutId) throws WorkoutValidationException;
}
