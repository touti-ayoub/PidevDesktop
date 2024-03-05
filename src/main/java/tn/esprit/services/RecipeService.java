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
        // Calculate the total nutritional information of the recipe
        int totalCalories = 0;
        int totalProtein = 0;
        int totalCarbs = 0;
        int totalFat = 0;
        for (Food food : recipe.getFoods()) {
            totalCalories += food.getCalories();
            totalProtein += food.getProtein();
            totalCarbs += food.getCarbohydrates();
            totalFat += food.getFat();
        }
        recipe.setTotalCalories(totalCalories);
        recipe.setTotalProtein(totalProtein);
        recipe.setTotalCarbs(totalCarbs);
        recipe.setTotalFat(totalFat);

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

        // Add each selected food to the recipe
        for (Food food : recipe.getFoods()) {
            RecipeFood recipeFood = new RecipeFood();
            recipeFood.setIdRecipe(recipe.getIdRecipe());
            recipeFood.setIdFood(food.getId());
            // Add the food to the recipe
            sql = "INSERT INTO recipe_food (IdRecipe, IdFood) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, recipeFood.getIdRecipe());
            statement.setInt(2, recipeFood.getIdFood());
            statement.executeUpdate();
        }
    }

    @Override
    public int modifier(Recipe recipe) throws SQLException {
        String sql = "UPDATE recipe SET name = ?, totalCalories = ?, totalProtein = ?, totalCarbs = ?, totalFat = ? WHERE IdRecipe = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, recipe.getName());
        statement.setInt(2, recipe.getTotalCalories());
        statement.setInt(3, recipe.getTotalProtein());
        statement.setInt(4, recipe.getTotalCarbs());
        statement.setInt(5, recipe.getTotalFat());
        statement.setInt(6, recipe.getIdRecipe());
        statement.executeUpdate();

        // Remove all foods from the recipe in the database
        sql = "DELETE FROM recipe_food WHERE IdRecipe = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, recipe.getIdRecipe());
        statement.executeUpdate();

        // Add each food from the foods list of the recipe to the recipe in the database
        for (Food food : recipe.getFoods()) {
            RecipeFood recipeFood = new RecipeFood();
            recipeFood.setIdRecipe(recipe.getIdRecipe());
            recipeFood.setIdFood(food.getId());
            // Add the food to the recipe
            sql = "INSERT INTO recipe_food (IdRecipe, IdFood) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, recipeFood.getIdRecipe());
            statement.setInt(2, recipeFood.getIdFood());
            statement.executeUpdate();
        }

        return 0;
    }

    @Override
    public boolean supprimer(int id) throws SQLException {
        String sql = "DELETE FROM recipe WHERE IdRecipe = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0;
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

            // Fetch the foods for this recipe
            List<Food> foods = getFoodsInRecipe(recipe.getIdRecipe());
            recipe.setFoods(foods); // Assuming you have a setter method in your Recipe class

            recipes.add(recipe);
        }
        return recipes;
    }

    public Recipe getRecipeById(int id) throws SQLException {
        String sql = "SELECT * FROM recipe WHERE IdRecipe = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Recipe recipe = new Recipe();
            recipe.setIdRecipe(resultSet.getInt("IdRecipe"));
            recipe.setName(resultSet.getString("name"));
            recipe.setTotalCalories(resultSet.getInt("totalCalories"));
            recipe.setTotalProtein(resultSet.getInt("totalProtein"));
            recipe.setTotalCarbs(resultSet.getInt("totalCarbs"));
            recipe.setTotalFat(resultSet.getInt("totalFat"));
            return recipe;
        } else {
            throw new SQLException("No recipe found with ID: " + id);
        }
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

            // Update the nutritional information of the recipe in the database
            sql = "UPDATE recipe SET totalCalories = totalCalories + ?, totalProtein = totalProtein + ?, totalCarbs = totalCarbs + ?, totalFat = totalFat + ? WHERE IdRecipe = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, calories);
            statement.setInt(2, protein);
            statement.setInt(3, carbs);
            statement.setInt(4, fat);
            statement.setInt(5, recipeFood.getIdRecipe());
            statement.executeUpdate();

            // Update the nutritional information of the recipe in memory
            Recipe recipe = getRecipeById(recipeFood.getIdRecipe());
            recipe.setTotalCalories(recipe.getTotalCalories() + calories);
            recipe.setTotalProtein(recipe.getTotalProtein() + protein);
            recipe.setTotalCarbs(recipe.getTotalCarbs() + carbs);
            recipe.setTotalFat(recipe.getTotalFat() + fat);
        }
    }

    public void removeFoodFromRecipe(RecipeFood recipeFood) throws SQLException {
        // Retrieve the nutritional information of the removed food
        String sql = "SELECT * FROM food WHERE IdFood = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, recipeFood.getIdFood());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int calories = resultSet.getInt("calories");
            int protein = resultSet.getInt("protein");
            int carbs = resultSet.getInt("carbohydrates");
            int fat = resultSet.getInt("fat");

            // Update the nutritional information of the recipe in the database
            sql = "UPDATE recipe SET totalCalories = totalCalories - ?, totalProtein = totalProtein - ?, totalCarbs = totalCarbs - ?, totalFat = totalFat - ? WHERE IdRecipe = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, calories);
            statement.setInt(2, protein);
            statement.setInt(3, carbs);
            statement.setInt(4, fat);
            statement.setInt(5, recipeFood.getIdRecipe());
            statement.executeUpdate();

            // Update the nutritional information of the recipe in memory
            Recipe recipe = getRecipeById(recipeFood.getIdRecipe());
            recipe.setTotalCalories(recipe.getTotalCalories() - calories);
            recipe.setTotalProtein(recipe.getTotalProtein() - protein);
            recipe.setTotalCarbs(recipe.getTotalCarbs() - carbs);
            recipe.setTotalFat(recipe.getTotalFat() - fat);
        }

        // Remove the food from the recipe in the database
        sql = "DELETE FROM recipe_food WHERE IdRecipe = ? AND IdFood = ?";
        statement = connection.prepareStatement(sql);
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

    private Recipe createRecipeFromResultSet(ResultSet resultSet) throws SQLException {
        Recipe recipe = new Recipe();
        recipe.setIdRecipe(resultSet.getInt("idRecipe"));
        recipe.setName(resultSet.getString("name"));
        recipe.setTotalCalories(resultSet.getInt("totalCalories"));
        recipe.setTotalProtein(resultSet.getInt("totalProtein"));
        recipe.setTotalCarbs(resultSet.getInt("totalCarbs"));
        recipe.setTotalFat(resultSet.getInt("totalFat"));
        return recipe;
    }

    // Filter recipes by ingredient
    public List<Recipe> filterByIngredient(String ingredient) throws SQLException {
        String sql = "SELECT * FROM recipe JOIN recipe_food ON recipe.idRecipe = recipe_food.idRecipe WHERE recipe_food.ingredient = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, ingredient);
        ResultSet resultSet = statement.executeQuery();

        List<Recipe> recipes = new ArrayList<>();
        while (resultSet.next()) {
            recipes.add(createRecipeFromResultSet(resultSet));
        }

        return recipes;
    }

    // Sort recipes by calories
    public List<Recipe> sortByCalories(boolean ascending) throws SQLException {
        String sql = "SELECT * FROM recipe ORDER BY totalCalories " + (ascending ? "ASC" : "DESC");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        List<Recipe> recipes = new ArrayList<>();
        while (resultSet.next()) {
            recipes.add(createRecipeFromResultSet(resultSet));
        }

        return recipes;
    }

    // Search recipes by keyword
    public List<Recipe> searchByKeyword(String keyword) throws SQLException {
        String sql = "SELECT * FROM recipe WHERE name LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + keyword + "%");
        ResultSet resultSet = statement.executeQuery();

        List<Recipe> recipes = new ArrayList<>();
        while (resultSet.next()) {
            recipes.add(createRecipeFromResultSet(resultSet));
        }

        return recipes;
    }

}