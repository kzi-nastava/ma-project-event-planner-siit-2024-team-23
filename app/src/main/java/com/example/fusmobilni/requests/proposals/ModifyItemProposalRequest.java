package com.example.fusmobilni.requests.proposals;

public class ModifyItemProposalRequest {

    private String name;
    private String description;
    private boolean existingCategoryChosen;
    private Long existingCategoryId;

    public ModifyItemProposalRequest(String name, String description, boolean existingCategoryChosen, Long existingCategoryId) {
        this.name = name;
        this.description = description;
        this.existingCategoryChosen = existingCategoryChosen;
        this.existingCategoryId = existingCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getExistingCategoryId() {
        return existingCategoryId;
    }

    public void setExistingCategoryId(Long existingCategoryId) {
        this.existingCategoryId = existingCategoryId;
    }

    public boolean isExistingCategoryChosen() {
        return existingCategoryChosen;
    }

    public void setExistingCategoryChosen(boolean existingCategoryChosen) {
        this.existingCategoryChosen = existingCategoryChosen;
    }

    @Override
    public String toString() {
        return "ModifyItemProposalRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", existingCategoryChosen=" + existingCategoryChosen +
                ", existingCategoryId=" + existingCategoryId +
                '}';
    }
}
