package com.julian.commerceauthsecurity.domain.valueobject;

public class Avatar {
    private String image;

    public Avatar(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static String getDefaultAvatar() {
        return "https://www.gravatar.com/avatar/";
    }
}
