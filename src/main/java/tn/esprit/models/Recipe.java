package tn.esprit.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Recipe {
    private int idRecipe;
    private String name;
    private int totalCalories;
    private int totalProtein;
    private int totalCarbs;
    private int totalFat;

    // constructors, getters, setters, toString method


    public Recipe(int idRecipe, String name, int totalCalories, int totalProtein, int totalCarbs, int totalFat) {
        this.idRecipe = idRecipe;
        this.name = name;
        this.totalCalories = totalCalories;
        this.totalProtein = totalProtein;
        this.totalCarbs = totalCarbs;
        this.totalFat = totalFat;
    }

    public Recipe(String name, int totalCalories, int totalProtein, int totalCarbs, int totalFat) {
        this.name = name;
        this.totalCalories = totalCalories;
        this.totalProtein = totalProtein;
        this.totalCarbs = totalCarbs;
        this.totalFat = totalFat;
    }

    public Recipe() {
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    public int getTotalProtein() {
        return totalProtein;
    }

    public void setTotalProtein(int totalProtein) {
        this.totalProtein = totalProtein;
    }

    public int getTotalCarbs() {
        return totalCarbs;
    }

    public void setTotalCarbs(int totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public int getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(int totalFat) {
        this.totalFat = totalFat;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "idRecipe=" + idRecipe +
                ", name='" + name + '\'' +
                ", totalCalories=" + totalCalories +
                ", totalProtein=" + totalProtein +
                ", totalCarbs=" + totalCarbs +
                ", totalFat=" + totalFat +
                '}';
    }
}