package com.example.fusmobilni.requests.events.invitations;

import java.util.List;

public class InvitationsCreateRequest {
    public List<InvitationCreateRequest> invitations;

    public InvitationsCreateRequest(List<InvitationCreateRequest> invitations) {
        this.invitations = invitations;
    }

    public List<InvitationCreateRequest> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<InvitationCreateRequest> invitations) {
        this.invitations = invitations;
    }
}
