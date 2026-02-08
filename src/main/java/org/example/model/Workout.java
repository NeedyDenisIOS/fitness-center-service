package org.example.model;
/*
*   Тренировка (Workout):
	id (уникальный идентификатор).
	title (название тренировки, например: "Йога", "Функциональный тренинг").
	trainerName (имя тренера).
	schedule (расписание, LocalDateTime).
	maxParticipants (максимальное количество участников).
	currentParticipants (список ID записанных участников).
	status (статус: SCHEDULED, COMPLETED, CANCELLED).
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Workout {

    private final UUID id;

    private String title;
    private String trainerName;
    private LocalDateTime schedule;
    private int maxParticipants;
    private Set<UUID> currentParticipants;
    private Status status;

    public Workout(UUID id, String title, String trainerName, LocalDateTime schedule, int maxParticipants, Set<UUID> currentParticipants, Status status) {
        this.id = id == null ? UUID.randomUUID() : id;
        this.title = title;
        this.trainerName = trainerName;
        this.schedule = schedule;
        this.maxParticipants = maxParticipants;
        this.currentParticipants = currentParticipants;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public LocalDateTime getSchedule() {
        return schedule;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public Set<UUID> getCurrentParticipants() {
        return new HashSet<>(currentParticipants);
    }

    public Status getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public void setSchedule(LocalDateTime schedule) {
        this.schedule = schedule;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public void setCurrentParticipants(Set<UUID> currentParticipants) {
        this.currentParticipants = Objects.requireNonNullElseGet(currentParticipants, HashSet::new);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Workout: id=%s, title=%s, trainerName=%s, schedule=%s, maxParticipants=%d, currentParticipants=%d, status=%s",
                id, title, trainerName, schedule, maxParticipants, currentParticipants.size(), status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Workout workout = (Workout) o;
        return Objects.equals(id, workout.id) &&
                Objects.equals(title, workout.title) &&
                Objects.equals(trainerName, workout.trainerName) &&
                Objects.equals(schedule, workout.schedule) &&
                maxParticipants == workout.maxParticipants &&
                Objects.equals(currentParticipants, workout.currentParticipants) &&
                Objects.equals(status, workout.status);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (id == null ? 0 : id.hashCode());
        result = 31 * result + (title == null ? 0 : title.hashCode());
        result = 31 * result + (trainerName == null ? 0 : trainerName.hashCode());
        result = 31 * result + maxParticipants;
        result = 31 * result + (schedule == null ? 0 : schedule.hashCode());
        result = 31 * result + (currentParticipants == null ? 0 : currentParticipants.hashCode());
        result = 31 * result + (status == null ? 0 : status.hashCode());
        return result;
    }

}
