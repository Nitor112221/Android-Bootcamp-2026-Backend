package ru.sicampus.bootcamp2026.exceptions;

public class InvitationNotExist extends RuntimeException {
    public InvitationNotExist(String message) {
        super(message);
    }
}
