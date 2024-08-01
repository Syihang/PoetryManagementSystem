package database;

import pojo.Poetry;
import util.SystemConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataInsert {

    // 插入收藏的诗词的数据
    public static void StarInsert(int poetryID) {
        String query = "INSERT INTO star_table (poetry_id, user_id) VALUES (?, ?)";
        try (Connection con = DataControl.openData();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, poetryID);
            pstmt.setInt(2, SystemConstants.currentID);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully inserted the star record.");
            } else {
                System.out.println("Failed to insert the star record.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 插入新诗词
    public static void PoetryInsert(String title,String author, String dynasty, String type, String text) {
        if (title.isEmpty()) title = "无题";
        if (author.isEmpty()) author = "佚名";
        if (dynasty.isEmpty()) dynasty = "不详";
        if (type.isEmpty()) type = "暂无归类";
        String query = "INSERT INTO poetry (title, author, dynasty, type, text) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, dynasty);
            stmt.setString(4, type);
            stmt.setString(5, text);

            // Execute the query
            stmt.executeUpdate();

            System.out.println("Poetry inserted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to insert poetry.");
        }
    }

    // 插入新用户
    public static boolean UserInsert(String userAccount, String userPassword, String userTelephone) {
        String query = "INSERT INTO user (user_account, user_password, user_telephone) VALUES (?, ?, ?)";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // 设置参数
            stmt.setString(1, userAccount);
            stmt.setString(2, userPassword);
            stmt.setString(3, userTelephone);

            // 执行查询
            stmt.executeUpdate();

            System.out.println("User inserted successfully!");

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to insert user.");
            return false;
        }
    }
    // 插入管理员
    public static boolean AdminInsert(String adminAccount, String adminPassword, String adminName, String telephone) {
        String query = "INSERT INTO admin (admin_account, admin_password, admin_name, telephone) VALUES (?, ?, ?, ?)";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // 设置参数
            stmt.setString(1, adminAccount);
            stmt.setString(2, adminPassword);
            stmt.setString(3, adminName);
            stmt.setString(4, telephone);

            // 执行查询
            stmt.executeUpdate();

            System.out.println("Admin inserted successfully!");

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to insert admin.");
            return false;
        }
    }
}
