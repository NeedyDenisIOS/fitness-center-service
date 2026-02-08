package org.example.model;
/*
*   Член клуба (Member):
	id (уникальный идентификатор, например UUID).
	fullName (полное имя).
	dateOfBirth (дата рождения, LocalDate).
	membershipType (тип абонемента: SINGLE, MONTHLY, YEARLY).
	membershipStartDate (дата начала действия абонемента).
	membershipEndDate (дата окончания действия абонемента).
	activeTrainings (список ID записанных тренировок).
 */
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Member {

    private final UUID id;

    private String fullName;
    private LocalDate dateOfBirth;
    private MembershipType type;
    private LocalDate membershipStartDate;
    private LocalDate membershipEndDate;
    private Set<UUID> activeTrainings;

    public Member(UUID id, String fullName, LocalDate dateOfBirth,
                  MembershipType type, LocalDate membershipStartDate,
                  LocalDate membershipEndDate, Set<UUID> activeTrainings) {
        this.id = id == null ? UUID.randomUUID() : id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.type = type;
        this.membershipStartDate = membershipStartDate;
        this.membershipEndDate = membershipEndDate;
        this.activeTrainings = activeTrainings;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public MembershipType getType() {
        return type;
    }

    public LocalDate getMembershipStartDate() {
        return membershipStartDate;
    }

    public LocalDate getMembershipEndDate() {
        return membershipEndDate;
    }

    public Set<UUID> getActiveTrainings() {
        return new HashSet<>(activeTrainings);
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setType(MembershipType type) {
        this.type = type;
    }

    public void setMembershipStartDate(LocalDate membershipStartDate) {
        this.membershipStartDate = membershipStartDate;
    }

    public void setMembershipEndDate(LocalDate membershipEndDate) {
        this.membershipEndDate = membershipEndDate;
    }

    public void setActiveTrainings(Set<UUID> activeTrainings) {
        if (activeTrainings == null) {
            this.activeTrainings = new HashSet<>();
        } else {
            this.activeTrainings = new HashSet<>(activeTrainings);
        }
    }

    @Override
    public String toString() {
        return String.format("Member: id=%s, fullName=%s, dateOfBirth=%s, type=%s, membershipStartDate=%s, membershipEndDate=%s, activeTrainings=%d",
                id, fullName, dateOfBirth, type, membershipStartDate, membershipEndDate, activeTrainings.size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(id, member.id) &&
                Objects.equals(fullName, member.fullName) &&
                Objects.equals(dateOfBirth, member.dateOfBirth) &&
                Objects.equals(type, member.type) &&
                Objects.equals(membershipStartDate, member.membershipStartDate) &&
                Objects.equals(membershipEndDate, member.membershipEndDate) &&
                Objects.equals(activeTrainings, member.activeTrainings);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (id == null ? 0 : id.hashCode());
        result = 31 * result + (fullName == null ? 0 : fullName.hashCode());
        result = 31 * result + (dateOfBirth == null ? 0 : dateOfBirth.hashCode());
        result = 31 * result + (type == null ? 0 : type.hashCode());
        result = 31 * result + (membershipStartDate == null ? 0 : membershipStartDate.hashCode());
        result = 31 * result + (membershipEndDate == null ? 0 : membershipEndDate.hashCode());
        result = 31 * result + (activeTrainings == null ? 0 : activeTrainings.hashCode());
        return result;
    }

}

