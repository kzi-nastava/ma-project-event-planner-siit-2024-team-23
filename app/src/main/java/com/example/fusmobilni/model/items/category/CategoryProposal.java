package com.example.fusmobilni.model.items.category;

public class CategoryProposal {
    private int id;
    private String name;
    private String description;
    private String itemName;
    private String itemDescription;

    public CategoryProposal(int id, String name, String description, String itemName, String itemDescription) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.itemDescription = itemDescription;
        this.itemName = itemName;
    }

    public int getId() {
        return id;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
