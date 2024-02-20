package tn.esprit.services;

import tn.esprit.models.Food;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodService implements IService<Food>{

    private Connection connection;

    public FoodService(){
        connection = MyDatabase.getInstance().getConnection();
    }

    // CRUD operations

    @Override
    public void ajouter(Food food) throws SQLException {
        String sql = "INSERT INTO food (name, calories, protein, carbohydrates, fat, serving_size, serving_unit) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, food.getName());
        statement.setInt(2, food.getCalories());
        statement.setInt(3, food.getProtein());
        statement.setInt(4, food.getCarbohydrates());
        statement.setInt(5, food.getFat());
        statement.setDouble(6, food.getServingSize());
        statement.setString(7, food.getServingUnit());
        statement.executeUpdate();
    }

    @Override
    public void modifier(Food food) throws SQLException {
        String sql = "UPDATE food SET name = ?, calories = ?, protein = ?, carbohydrates = ?, fat = ?, serving_size = ?, serving_unit = ? WHERE IdFood = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, food.getName());
        statement.setInt(2, food.getCalories());
        statement.setInt(3, food.getProtein());
        statement.setInt(4, food.getCarbohydrates());
        statement.setInt(5, food.getFat());
        statement.setDouble(6, food.getServingSize());
        statement.setString(7, food.getServingUnit());
        statement.setInt(8, food.getId());
        statement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM food WHERE IdFood = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    @Override
    public List<Food> recuperer() throws SQLException {
        String sql = "SELECT * FROM food";
        PreparedStatement statement = connection.prepareStatement(sql);
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

    // Filter foods by calories
    public List<Food> filterByCalories(int calories) throws SQLException {
        String sql = "SELECT * FROM food WHERE calories <= ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, calories);
        ResultSet resultSet = statement.executeQuery();

        List<Food> foods = new ArrayList<>();
        while (resultSet.next()) {
            foods.add(createFoodFromResultSet(resultSet));
        }

        return foods;
    }

    // Filter foods by name
    public List<Food> filterByName(String name) throws SQLException {
        String sql = "SELECT * FROM food WHERE name = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();

        List<Food> foods = new ArrayList<>();
        while (resultSet.next()) {
            foods.add(createFoodFromResultSet(resultSet));
        }

        return foods;
    }

    // Search foods by keyword
    public List<Food> searchFoods(String keyword) throws SQLException {
        String sql = "SELECT * FROM food WHERE name LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + keyword + "%");
        ResultSet resultSet = statement.executeQuery();

        List<Food> foods = new ArrayList<>();
        while (resultSet.next()) {
            foods.add(createFoodFromResultSet(resultSet));
        }

        return foods;
    }

    // Sort foods by calories
    public List<Food> sortByCalories(boolean ascending) throws SQLException {
        String sql = "SELECT * FROM food ORDER BY calories " + (ascending ? "ASC" : "DESC");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        List<Food> foods = new ArrayList<>();
        while (resultSet.next()) {
            foods.add(createFoodFromResultSet(resultSet));
        }

        return foods;
    }

    // Sort foods by name
    public List<Food> sortByName(boolean ascending) throws SQLException {
        String sql = "SELECT * FROM food ORDER BY name " + (ascending ? "ASC" : "DESC");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        List<Food> foods = new ArrayList<>();
        while (resultSet.next()) {
            foods.add(createFoodFromResultSet(resultSet));
        }

        return foods;
    }

    private Food createFoodFromResultSet(ResultSet resultSet) throws SQLException {
        Food food = new Food();
        food.setId(resultSet.getInt("IdFood"));
        food.setName(resultSet.getString("name"));
        food.setCalories(resultSet.getInt("calories"));
        food.setProtein(resultSet.getInt("protein"));
        food.setCarbohydrates(resultSet.getInt("carbohydrates"));
        food.setFat(resultSet.getInt("fat"));
        food.setServingSize(resultSet.getDouble("serving_size"));
        food.setServingUnit(resultSet.getString("serving_unit"));
        return food;
    }

}
