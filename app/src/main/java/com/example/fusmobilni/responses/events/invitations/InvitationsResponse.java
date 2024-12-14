package com.example.fusmobilni.responses.events.invitations;

import java.util.List;

public class InvitationsResponse {
    public List<InvitationResponse> list;

    public InvitationsResponse(List<InvitationResponse> list) {
        this.list = list;
    }

    public List<InvitationResponse> getList() {
        return list;
    }

    public void setList(List<InvitationResponse> list) {
        this.list = list;
    }
}
