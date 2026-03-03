package org.example.validation;

import org.example.model.Member;
import org.example.model.MembershipType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class MemberValidator {

    public void validateId(UUID id) throws MemberValidationException {
        if (id == null) {
            throw new MemberValidationException("The id of the member is required.");
        }
    }

    public void validateFullName(String fullName) throws MemberValidationException {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new MemberValidationException("The full name of the member is required.");
        }
    }

    public void validateDateOfBirth(LocalDate dateOfBirth) throws MemberValidationException {
        if (dateOfBirth == null) {
            throw new MemberValidationException("The date of birth of the member is required.");
        }
    }

    public void validateFullNameNotInFuture(LocalDate dateOfBirth) throws MemberValidationException {
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new MemberValidationException("Date of birth can't be in future.");
        }
    }

    public void validateType(MembershipType type) throws MemberValidationException {
        if (type == null) {
            throw new MemberValidationException("The type of the member is required.");
        }
    }

    public void validateMembershipDate(LocalDate startDate, LocalDate endDate) throws MemberValidationException {
        if (startDate == null || endDate == null) {
            throw new MemberValidationException("Membership dates cannot be null.");
        }

        if (startDate.isBefore(LocalDate.now())) {
            throw new MemberValidationException("Start date cannot be in past.");
        }

        if (endDate.isBefore(startDate)) {
            throw new MemberValidationException("End date must be after start date.");
        }
    }

    public void activeTrainingsValidator(List<Member> members) throws MemberValidationException {
        if (members.isEmpty()) {
            throw new MemberValidationException("No members found");
        }
    }

    public Member memberValidator(Optional<Member> member) throws MemberValidationException {
        if (member.isEmpty()) {
            throw new MemberValidationException("Member not found");
        }
        return member.get();
    }

    public void validateBasicParameters(String fullName, LocalDate dateOfBirth, MembershipType type, LocalDate membershipStartDate, LocalDate membershipEndDate) throws MemberValidationException {
        validateFullName(fullName);
        validateDateOfBirth(dateOfBirth);
        validateType(type);
        validateMembershipDate(membershipStartDate, membershipEndDate);
    }

    public void validateActiveTrainings(Set<UUID> activeTrainings) throws MemberValidationException {
        if (!activeTrainings.isEmpty()) {
            throw new MemberValidationException("List of active trainings must be empty for delete.");
        }
    }

    public void validateSubscriptionActivity(LocalDate membershipEndDate) throws MemberValidationException {
        if (membershipEndDate == null) {
            throw new MemberValidationException("Membership end date is required");
        }
        long result = ChronoUnit.DAYS.between(LocalDate.now(), membershipEndDate);
        if (result < 0) {
            throw new MemberValidationException("The subscription period has expired.");
        }
    }

    public void validateNotAlreadyEnrolled(Set<UUID> activeTrainings,UUID workoutId) throws MemberValidationException {
        if (activeTrainings.contains(workoutId)) {
            throw new MemberValidationException("Member already enrolled in workout.");
        }
    }

    public void validateMemberIsEnrolled(Set<UUID> activeTrainings,UUID workoutId) throws MemberValidationException {
        if (!activeTrainings.contains((workoutId))) {
            throw new MemberValidationException("You can't cancel enroll which not exist.");
        }
    }

}
