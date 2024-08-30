package com.example.recipeazy;

import java.util.List;

public class User {

    private String userId;
    private Boolean isAdmin;
    private Boolean isVerified;
    private List<String> followedByUserIds;
    private List<String> followsUserIds;
    private String email;
    private String username;
    private String country;
    private int age;
    private List<String> shoppingList;
    private List<String> likedRecipes;
    private String profilePictureUrl;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userId, Boolean isAdmin, Boolean isVerified, List<String> followedByUserIds, List<String> followsUserIds, String email, String username, String country, int age, List<String> shoppingList, List<String> likedRecipes, String profilePictureUrl) {
        this.userId = userId;
        this.isAdmin = isAdmin;
        this.isVerified = isVerified;
        this.followedByUserIds = followedByUserIds;
        this.followsUserIds = followsUserIds;
        this.email = email;
        this.username = username;
        this.country = country;
        this.age = age;
        this.shoppingList = shoppingList;
        this.likedRecipes = likedRecipes;
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public List<String> getFollowedByUserIds() {
        return followedByUserIds;
    }

    public void setFollowedByUserIds(List<String> followedByUserIds) {
        this.followedByUserIds = followedByUserIds;
    }

    public List<String> getFollowsUserIds() {
        return followsUserIds;
    }

    public void setFollowsUserIds(List<String> followsUserIds) {
        this.followsUserIds = followsUserIds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<String> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public List<String> getLikedRecipes() {
        return likedRecipes;
    }

    public void setLikedRecipes(List<String> likedRecipes) {
        this.likedRecipes = likedRecipes;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
}
