package tn.esprit.services;

import tn.esprit.models.Food;
import tn.esprit.models.Recipe;
import tn.esprit.models.RecipeFood;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeService implements IService<Recipe> {
    private Connection connection;

    public RecipeService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    // CRUD operations for Recipe
    @Override
    public void ajouter(Recipe recipe) throws SQLException {
        String sql = "INSERT INTO recipe (name, totalCalories, totalProtein, totalCarbs, totalFat) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, recipe.getName());
        statement.setInt(2, recipe.getTotalCalories());
        statement.setInt(3, recipe.getTotalProtein());
        statement.setInt(4, recipe.getTotalCarbs());
        statement.setInt(5, recipe.getTotalFat());
        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            recipe.setIdRecipe(generatedKeys.getInt(1)); // Set the ID on the recipe object
        } else {
            throw new SQLException("Creating recipe failed, no ID obtained.");
        }
    }

    @Override
    public void modifier(Recipe recipe) throws SQLException {
        String sql = "UPDATE recipe SET name = ?, totalCalories = ?, totalProtein = ?, totalCarbs = ?, totalFat = ? WHERE IdRecipe = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, recipe.getName());
        statement.setInt(2, recipe.getTotalCalories());
        statement.setInt(3, recipe.getTotalProtein());
        statement.setInt(4, recipe.getTotalCarbs());
        statement.setInt(5, recipe.getTotalFat());
        statement.setInt(6, recipe.getIdRecipe());
        statement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM recipe WHERE IdRecipe = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    @Override
    public List<Recipe> recuperer() throws SQLException {
        String sql = "SELECT * FROM recipe";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<Recipe> recipes = new ArrayList<>();
        while (resultSet.next()) {
            Recipe recipe = new Recipe();
            recipe.setIdRecipe(resultSet.getInt("IdRecipe"));
            recipe.setName(resultSet.getString("name"));
            recipe.setTotalCalories(resultSet.getInt("totalCalories"));
            recipe.setTotalProtein(resultSet.getInt("totalProtein"));
            recipe.setTotalCarbs(resultSet.getInt("totalCarbs"));
            recipe.setTotalFat(resultSet.getInt("totalFat"));
            recipes.add(recipe);
        }
        return recipes;
    }

    // methods to add a food to a recipe, remove a food from a recipe, and retrieve all foods in a recipe
    public void addFoodToRecipe(RecipeFood recipeFood) throws SQLException {
        // Add the food to the recipe
        String sql = "INSERT INTO recipe_food (IdRecipe, IdFood) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, recipeFood.getIdRecipe());
        statement.setInt(2, recipeFood.getIdFood());
        statement.executeUpdate();

        // Retrieve the nutritional information of the added food
        sql = "SELECT * FROM food WHERE IdFood = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, recipeFood.getIdFood());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int calories = resultSet.getInt("calories");
            int protein = resultSet.getInt("protein");
            int carbs = resultSet.getInt("carbohydrates");
            int fat = resultSet.getInt("fat");

            // Update the nutritional information of the recipe
            sql = "UPDATE recipe SET totalCalories = totalCalories + ?, totalProtein = totalProtein + ?, totalCarbs = totalCarbs + ?, totalFat = totalFat + ? WHERE IdRecipe = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, calories);
            statement.setInt(2, protein);
            statement.setInt(3, carbs);
            statement.setInt(4, fat);
            statement.setInt(5, recipeFood.getIdRecipe());
            statement.executeUpdate();
        }
    }

    public void removeFoodFromRecipe(RecipeFood recipeFood) throws SQLException {
        String sql = "DELETE FROM recipe_food WHERE IdRecipe = ? AND IdFood = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, recipeFood.getIdRecipe());
        statement.setInt(2, recipeFood.getIdFood());
        statement.executeUpdate();
    }

    public List<Food> getFoodsInRecipe(int idRecipe) throws SQLException {
        String sql = "SELECT f.* FROM food f INNER JOIN recipe_food rf ON f.IdFood = rf.IdFood WHERE rf.IdRecipe = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idRecipe);
        ResultSet resultSet = statement.executeQuery();
        List<Food> foods = new ArrayList<>();
        while (resultSet.next()) {
            Food food = new Food();
            food.setId(resultSet.getInt("IdFood"));
            food.setName(resultSet.getString("name"));
            food.setCalories(resultSet.getInt("calories"));
            food.setProtein(resultSet.getInt("protein"));
            food.setCarbohydrates(resultSet.getInt("carbohydrates"));
            food.setFat(resultSet.getInt("fat"));
            food.setServingSize(resultSet.getDouble("serving_size"));
            food.setServingUnit(resultSet.getString("serving_unit"));
            foods.add(food);
        }
        return foods;
    }
}