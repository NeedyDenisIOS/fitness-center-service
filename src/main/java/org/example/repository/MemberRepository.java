package org.example.repository;

import org.example.model.Member;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MemberRepository {

    private final List<Member> members = new LinkedList<>();

    private static final MemberRepository INSTANCE = new MemberRepository();

    private MemberRepository() {
    }

    public static MemberRepository getInstance() {
        return INSTANCE;
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public Optional<Member> getById(UUID id) {
        for (Member member : members) {
            if (member.getId().equals(id)) {
                return Optional.of(member);
            }
        }
        return Optional.empty();
    }

    public List<Member> getListOfMembers() {
        return members;
    }

    public void deleteById(UUID id) {
        members.removeIf(member -> member.getId().equals(id));
    }
}
