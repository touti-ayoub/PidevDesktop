package tn.esprit.test;

import tn.esprit.models.Recipe;
import tn.esprit.models.RecipeFood;
import tn.esprit.services.RecipeService;

import java.sql.SQLException;
import java.util.List;

public class recipeCrud {
    public static void main(String[] args) {
        RecipeService recipeService = new RecipeService();

        // Create a new recipe
        Recipe recipe = new Recipe();
        recipe.setName("Test Recipe");
        try {
            recipeService.ajouter(recipe);
            System.out.println("Recipe added successfully with ID: " + recipe.getIdRecipe());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*
        // ID of the existing recipe
        int existingRecipeId = 1; // Replace with your actual recipe ID
         */

        // Add first food item to the recipe
        RecipeFood recipeFood1 = new RecipeFood();
        recipeFood1.setIdRecipe(recipe.getIdRecipe());
        recipeFood1.setIdFood(9); // Assuming there is a food item with IdFood = 1
        try {
            recipeService.addFoodToRecipe(recipeFood1);
            System.out.println("Food 1 added to recipe successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Add second food item to the recipe
        RecipeFood recipeFood2 = new RecipeFood();
        recipeFood2.setIdRecipe(recipe.getIdRecipe());
        recipeFood2.setIdFood(10); // Assuming there is a food item with IdFood = 2
        try {
            recipeService.addFoodToRecipe(recipeFood2);
            System.out.println("Food 2 added to recipe successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Add third food item to the recipe
        RecipeFood recipeFood3 = new RecipeFood();
        recipeFood3.setIdRecipe(recipe.getIdRecipe());
        recipeFood3.setIdFood(12); // Assuming there is a food item with IdFood = 3
        try {
            recipeService.addFoodToRecipe(recipeFood3);
            System.out.println("Food 3 added to recipe successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retrieve the updated recipe
        try {
            List<Recipe> recipes = recipeService.recuperer();
            for (Recipe r : recipes) {
                if (r.getIdRecipe() == recipe.getIdRecipe()) {
                    System.out.println("Updated recipe: " + r);
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}