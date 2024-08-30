package com.example.recipeazy;

public class Ingredient {
    private String ingredientId;
    private String ingredient;
    private String quantity;
    private String unitMeasurement;
    public Ingredient() {
        // Default constructor required for calls to DataSnapshot.getValue(Ingredient.class)
    }

    public Ingredient(String ingredientId, String ingredient, String quantity, String unitMeasurement) {
        this.ingredientId = ingredientId;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unitMeasurement = unitMeasurement;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnitMeasurement() {
        return unitMeasurement;
    }

    public void setUnitMeasurement(String unitMeasurement) {
        this.unitMeasurement = unitMeasurement;
    }
}
