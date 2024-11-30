package com.example.fusmobilni.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.model.CategoryProposal;

public class CategoryProposalViewModel extends ViewModel {

    private MutableLiveData<String> name = new MutableLiveData<>("");
    private MutableLiveData<String> description = new MutableLiveData<>("");

    public void setName(String name) {
        this.name.setValue(name);
    }

    public void setDescription(String description) {
        this.description.setValue(description);
    }


    public MutableLiveData<String> getName() {
        return this.name;
    }

    public MutableLiveData<String> getDescription() {
        return this.description;
    }

    public void populate(CategoryProposal category) {
        this.name.setValue(category.getName());
        this.description.setValue(category.getDescription());
    }

    public void cleanUp() {
        this.name.setValue("");
        this.description.setValue("");
    }
}
