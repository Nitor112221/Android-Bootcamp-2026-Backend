package ru.sicampus.bootcamp2026.exceptions;

public class InvitationHasAlreadyResponded extends RuntimeException {
    public InvitationHasAlreadyResponded(String message) {
        super(message);
    }
}
