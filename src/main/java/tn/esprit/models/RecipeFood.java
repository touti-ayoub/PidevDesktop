package tn.esprit.models;

public class RecipeFood {
    private int idRecipe;
    private int idFood;

    // constructors, getters, setters

    public RecipeFood(int idRecipe, int idFood) {
        this.idRecipe = idRecipe;
        this.idFood = idFood;
    }

    public RecipeFood() {
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public int getIdFood() {
        return idFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
    }
}