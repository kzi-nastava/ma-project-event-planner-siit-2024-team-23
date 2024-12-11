package com.example.fusmobilni.viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.model.CategoryProposal;
import com.example.fusmobilni.requests.proposals.GetProposalResponse;
import com.example.fusmobilni.requests.proposals.ModifyItemProposalRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryProposalViewModel extends ViewModel {

    private MutableLiveData<String> name = new MutableLiveData<>("");
    private MutableLiveData<String> description = new MutableLiveData<>("");
    private MutableLiveData<String> itemName = new MutableLiveData<>("");
    private MutableLiveData<String> itemDescription = new MutableLiveData<>("");

    private MutableLiveData<String> existingCategory = new MutableLiveData<>("");

    public Long existingCategoryId;

    private Long proposalId;

    private MutableLiveData<Boolean> isExistingCategoryChosen = new MutableLiveData<>(false);

    public void setName(String name) {
        this.name.setValue(name);
    }

    public void setDescription(String description) {
        this.description.setValue(description);
    }
    public void setItemName(String itemName) {
        this.itemName.setValue(itemName);
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription.setValue(itemDescription);
    }

    public void setIsExistingCategoryChosen(Boolean chosen) {
        this.isExistingCategoryChosen.setValue(chosen);
    }



    public MutableLiveData<String> getName() {
        return this.name;
    }

    public MutableLiveData<String> getDescription() {
        return this.description;
    }
    public MutableLiveData<String> getItemName() {
        return this.itemName;
    }

    public MutableLiveData<String> getItemDescription() {
        return this.itemDescription;
    }

    public MutableLiveData<Boolean> getIsExistingCategoryChosen() {return this.isExistingCategoryChosen; }

    public void populate(GetProposalResponse proposal) {
        proposalId = proposal.getId();
        this.name.setValue(proposal.getName());
        this.description.setValue(proposal.getDescription());
        this.itemName.setValue(proposal.getItemName());
        this.itemDescription.setValue(proposal.getItemDescription());
    }

    public void cleanUp() {
        this.name.setValue("");
        this.description.setValue("");
        this.itemDescription.setValue("");
        this.itemName.setValue("");
        this.isExistingCategoryChosen.setValue(false);
        this.existingCategory.setValue("");
        proposalId = null;
    }

    public MutableLiveData<String> getExistingCategory() {
        return existingCategory;
    }

    public void setExistingCategory(String existingCategory) {
        this.existingCategory.setValue(existingCategory);
    }

    public void submit() {
        ModifyItemProposalRequest request = new ModifyItemProposalRequest(
                name.getValue(), description.getValue(), isExistingCategoryChosen.getValue(),
                existingCategoryId
        );

        Call<Void> call = ClientUtils.proposalService.modify(proposalId, request);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
}
