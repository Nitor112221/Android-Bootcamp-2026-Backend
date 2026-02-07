package ru.sicampus.bootcamp2026.exceptions;

public class InvitationNotSentToYou extends RuntimeException {
    public InvitationNotSentToYou(String message) {
        super(message);
    }
}
