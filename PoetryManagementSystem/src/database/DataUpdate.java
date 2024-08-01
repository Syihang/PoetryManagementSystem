package database;

import java.sql.*;

public class DataUpdate {

    public static void PoetryUpdate(int id, String title, String author, String dynasty, String type, String text) {
        // Check for null or empty inputs and assign default values
        if (title == null || title.isEmpty()) title = "无题";
        if (author == null || author.isEmpty()) author = "佚名";
        if (dynasty == null || dynasty.isEmpty()) dynasty = "不详";
        if (type == null || type.isEmpty()) type = "暂无归类";

        // SQL update query
        String query = "UPDATE poetry SET title = ?, author = ?, dynasty = ?, type = ?, text = ? WHERE poetry_id = ?";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, dynasty);
            stmt.setString(4, type);
            stmt.setString(5, text);
            stmt.setInt(6, id);

            // Execute the update
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Poetry updated successfully!");
            } else {
                System.out.println("No poetry found with the given id.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update poetry.");
        }
    }

    public static boolean UserUpdate(int id, String account, String password, String tel) {
        // Check for null or empty inputs and assign default values
        if (account == null || account.isEmpty()) account = "defaultAccount";
        if (password == null || password.isEmpty()) password = "defaultPassword";
        if (tel == null || tel.isEmpty()) tel = "defaultTel";

        // SQL update query
        String query = "UPDATE user SET user_account = ?, user_password = ?, user_telephone = ? WHERE user_id = ?";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters
            stmt.setString(1, account);
            stmt.setString(2, password);
            stmt.setString(3, tel);
            stmt.setInt(4, id);

            // Execute the update
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated successfully!");
            } else {
                System.out.println("No user found with the given id.");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update user.");
            return false;
        }
    }

    public static void AdminUpdate(int id, String account, String password, String name, String tel) {
        // Check for null or empty inputs and assign default values
        if (account == null || account.isEmpty()) account = "defaultAccount";
        if (password == null || password.isEmpty()) password = "defaultPassword";
        if (name == null || name.isEmpty()) name = "defaultName";
        if (tel == null || tel.isEmpty()) tel = "defaultTel";

        // SQL update query
        String query = "UPDATE admin SET admin_account = ?, admin_password = ?, admin_name = ?, telephone = ? WHERE admin_id = ?";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters
            stmt.setString(1, account);
            stmt.setString(2, password);
            stmt.setString(3, name);
            stmt.setString(4, tel);
            stmt.setInt(5, id);

            // Execute the update
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Admin updated successfully!");
            } else {
                System.out.println("No admin found with the given id.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update admin.");
        }
    }

    public static void updateUserPassword(int id, String newPassword){
        String query = "UPDATE user SET user_password = ? WHERE user_id = ?";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters
            stmt.setString(1, newPassword);
            stmt.setInt(2, id);

            // Execute the update
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated successfully!");
            } else {
                System.out.println("No user found with the given id.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update user.");
        }
    }

    public static void updateAdminPassword(int id, String newPassword){
        String query = "UPDATE admin SET admin_password = ? WHERE admin_id = ?";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters
            stmt.setString(1, newPassword);
            stmt.setInt(2, id);

            // Execute the update
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Admin updated successfully!");
            } else {
                System.out.println("No admin found with the given id.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update admin.");
        }
    }
}
