package tn.esprit.models;

public class Food {

    private int id;
    private String name;
    private int calories;
    private int protein;
    private int carbohydrates;
    private int fat;
    private double servingSize;
    private String servingUnit; // Descriptive unit (e.g., gram, ounce, cup)

    public Food(int id, String name, int calories, int protein, int carbohydrates, int fat, double servingSize, String servingUnit) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.servingSize = servingSize;
        this.servingUnit = servingUnit;
    }

    public Food(String name, int calories, int protein, int carbohydrates, int fat, double servingSize, String servingUnit) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.servingSize = servingSize;
        this.servingUnit = servingUnit;
    }

    public Food() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public double getServingSize() {
        return servingSize;
    }

    public void setServingSize(double servingSize) {
        this.servingSize = servingSize;
    }

    public String getServingUnit() {
        return servingUnit;
    }

    public void setServingUnit(String servingUnit) {
        this.servingUnit = servingUnit;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", calories=" + calories +
                ", protein=" + protein +
                ", carbohydrates=" + carbohydrates +
                ", fat=" + fat +
                ", servingSize=" + servingSize +
                ", servingUnit='" + servingUnit + '\'' +
                '}';
    }
}
