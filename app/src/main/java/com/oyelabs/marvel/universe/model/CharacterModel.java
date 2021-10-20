package com.oyelabs.marvel.universe.model;

public class CharacterModel {

    String id;
    String name;
    String description;
    String image;
    String moreDetails;

    public CharacterModel(String id, String name, String description, String image, String moreDetails) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.moreDetails = moreDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMoreDetails() {
        return moreDetails;
    }

    public void setMoreDetails(String moreDetails) {
        this.moreDetails = moreDetails;
    }
}
