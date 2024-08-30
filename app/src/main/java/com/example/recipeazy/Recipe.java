package com.example.recipeazy;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {

    private String recipeTitle;
    private String recipeId;
    private List<Ingredient> ingredients;
    private List<String> instructions;
    private int preparationTime;
    private int servingSize;
    private boolean isVegan;
    private boolean isGlutenFree;
    private boolean isKeto;
    private boolean isVegetarian;
    private String category;
    private String imageUrl;

    public Recipe(String recipeTitle, String recipeId, List<Ingredient> ingredients, List<String> instructions, int preparationTime, int servingSize, boolean isVegan, boolean isGlutenFree, boolean isKeto, boolean isVegetarian, String category, String imageUrl) {
        this.recipeId = recipeId;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.preparationTime = preparationTime;
        this.servingSize = servingSize;
        this.isVegan = isVegan;
        this.isGlutenFree = isGlutenFree;
        this.isKeto = isKeto;
        this.isVegetarian = isVegetarian;
        this.category = category;
        this.imageUrl = imageUrl;
        this.recipeTitle = recipeTitle;
    }

    public Recipe() {
        // Default constructor required for calls to DataSnapshot.getValue(Recipe.class)
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getServingSize() {
        return servingSize;
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }

    public boolean isGlutenFree() {
        return isGlutenFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        isGlutenFree = glutenFree;
    }

    public boolean isKeto() {
        return isKeto;
    }

    public void setKeto(boolean keto) {
        isKeto = keto;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
