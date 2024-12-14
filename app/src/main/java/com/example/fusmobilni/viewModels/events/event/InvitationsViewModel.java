package com.example.fusmobilni.viewModels.events.event;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class InvitationsViewModel extends ViewModel {
    MutableLiveData<List<String>> _emails = new MutableLiveData<>(new ArrayList<>());

    public MutableLiveData<List<String>> getEmails() {
        return _emails;
    }

    public void addEmail(String email) {
        List<String> emails = _emails.getValue();
        emails.add(email);
        _emails.setValue(emails);
    }

    public void setEmails(List<String> _emails) {
        this._emails.setValue(_emails);
    }
}
