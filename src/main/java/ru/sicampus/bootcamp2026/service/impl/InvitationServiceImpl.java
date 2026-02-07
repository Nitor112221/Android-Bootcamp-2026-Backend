package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.entity.*;
import ru.sicampus.bootcamp2026.exceptions.AlreadyExistMeetingAtThisTimeException;
import ru.sicampus.bootcamp2026.exceptions.InvitationHasAlreadyResponded;
import ru.sicampus.bootcamp2026.exceptions.InvitationNotExist;
import ru.sicampus.bootcamp2026.exceptions.InvitationNotSentToYou;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UsersMeetingRepository;
import ru.sicampus.bootcamp2026.service.InvitationService;
import ru.sicampus.bootcamp2026.util.InvitationMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {
    private final InvitationRepository invitationRepository;
    private final UsersMeetingRepository usersMeetingRepository;
    private final MeetingRepository meetingRepository;

    @Override
    public List<InvitationDTO> getAllInvitation(Users user) {
        return invitationRepository.findAllByTarget(user).stream()
                .map(InvitationMapper::convertToDto)
                .toList();
    }

    @Override
    public void acceptInvitation(Users user, Long id) {
        Invitation invitation = invitationRepository.findById(id).orElseThrow(() -> new InvitationNotExist("Invitation not exist"));
        if (invitation.getTarget().getId() != user.getId()) {
            throw new InvitationNotSentToYou("This invitation was sent not for you");
        }
        if (invitation.getStatus() != InvitationStatus.AWAITS) {
            throw new InvitationHasAlreadyResponded("The invitation has already been responded to");
        }
        LocalDateTime start = invitation.getMeeting().getStart();
        LocalDateTime end = start.plusMinutes(invitation.getMeeting().getDuration());

        if (!meetingRepository.findOverlappingMeetingIds(start, end, user.getId()).isEmpty()) {
            throw new AlreadyExistMeetingAtThisTimeException("You already have meeting at this time");
        }

        UsersMeeting relation = new UsersMeeting();
        relation.setMember(user);
        relation.setMeeting(invitation.getMeeting());
        relation.setStatus(UsersMeetingStatus.GUEST);

        usersMeetingRepository.save(relation);
        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitationRepository.save(invitation);
    }

    @Override
    public void rejectInvitation(Users user, Long id) {
        Invitation invitation = invitationRepository.findById(id).orElseThrow(() -> new InvitationNotExist("Invitation not exist"));
        if (invitation.getTarget().getId() != user.getId()) {
            throw new InvitationNotSentToYou("This invitation was sent not for you");
        }
        if (invitation.getStatus() != InvitationStatus.AWAITS) {
            throw new InvitationHasAlreadyResponded("The invitation has already been responded to");
        }
        invitation.setStatus(InvitationStatus.REJECTED);
        invitationRepository.save(invitation);
    }
}
