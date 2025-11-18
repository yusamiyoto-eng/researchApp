package com.mycompany.mavenproject1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    public static void insert(Coefficients coeff) throws SQLException{
        try(Connection connection = App.getConnection()) {
            String sql = "INSERT INTO coefficients(matherial, b0, b1, b2, b3, b4, b5, b6, b7, b8)"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, coeff.getMaterial());
            ps.setDouble(2, coeff.getB0());
            ps.setDouble(3, coeff.getB1());
            ps.setDouble(4, coeff.getB2());
            ps.setDouble(5, coeff.getB3());
            ps.setDouble(6, coeff.getB4());
            ps.setDouble(7, coeff.getB5());
            ps.setDouble(8, coeff.getB6());
            ps.setDouble(9, coeff.getB7());
            ps.setDouble(10, coeff.getB8());
            
            ps.executeUpdate();
            
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                coeff.setId(keys.getInt(1));
            }
        }
    }

    public static Coefficients selectByMaterial(String material) throws SQLException{
        try(Connection connection = App.getConnection()) {
            String sql = "SELECT * FROM coefficients WHERE matherial=?";
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, material);
            
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new Coefficients(
                        resultSet.getInt("ID"),
                        resultSet.getString("matherial"),
                        resultSet.getDouble("b0"),
                        resultSet.getDouble("b1"),
                        resultSet.getDouble("b2"),
                        resultSet.getDouble("b3"),
                        resultSet.getDouble("b4"),
                        resultSet.getDouble("b5"),
                        resultSet.getDouble("b6"),
                        resultSet.getDouble("b7"),
                        resultSet.getDouble("b8")
                );
            }
            return null;
        }
    }
    
    public static List<Coefficients> selectAll() throws SQLException{
        try(Connection connection = App.getConnection()) {
            String sql = "SELECT * FROM coefficients";
            
            Statement s = connection.createStatement();
            List<Coefficients> coeffList = new ArrayList<>();
            
            ResultSet resultSet = s.executeQuery(sql);
            while (resultSet.next()) {
                coeffList.add(new Coefficients(
                        resultSet.getInt("ID"),
                        resultSet.getString("matherial"),
                        resultSet.getDouble("b0"),
                        resultSet.getDouble("b1"),
                        resultSet.getDouble("b2"),
                        resultSet.getDouble("b3"),
                        resultSet.getDouble("b4"),
                        resultSet.getDouble("b5"),
                        resultSet.getDouble("b6"),
                        resultSet.getDouble("b7"),
                        resultSet.getDouble("b8")
                )); 
            }
            
            return coeffList;
        }
    }
    
    public static void update(Coefficients coeff) throws SQLException{
        try(Connection connection = App.getConnection()) {
            String sql = "UPDATE coefficients SET matherial=?, b0=?, b1=?, b2=?, b3=?, b4=?, b5=?, b6=?, b7=?, b8=? WHERE ID=?";
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, coeff.getMaterial());
            ps.setDouble(2, coeff.getB0());
            ps.setDouble(3, coeff.getB1());
            ps.setDouble(4, coeff.getB2());
            ps.setDouble(5, coeff.getB3());
            ps.setDouble(6, coeff.getB4());
            ps.setDouble(7, coeff.getB5());
            ps.setDouble(8, coeff.getB6());
            ps.setDouble(9, coeff.getB7());
            ps.setDouble(10, coeff.getB8());
            ps.setInt(11, coeff.getId());
            
            ps.executeUpdate();
          
        }
    }
    
    public static void delete(Coefficients coeff) throws SQLException{
        try(Connection connection = App.getConnection()) { //conn.close();
            String sql = "DELETE FROM coefficients WHERE ID=?";
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, coeff.getId());
            
            ps.executeUpdate();
          
        }
    }
}
