package database;
import util.SystemConstants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataDelete {

    // 删除收藏的数据
    public static void StarDelete(int poetryID) {
        String deleteStar = "DELETE FROM star_table WHERE poetry_id = ? AND user_id = ?";
        try (Connection con = DataControl.openData();
             PreparedStatement pstmt = con.prepareStatement(deleteStar)) {
            pstmt.setInt(1, poetryID);
            pstmt.setInt(2, SystemConstants.currentID);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully deleted the star record.");
            } else {
                System.out.println("Failed to delete the star record.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while deleting the star record: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 删除诗词
    public static void PoetryDelete(int id) {
        String query = "DELETE FROM poetry WHERE poetry_id = ?";
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            // Open database connection
            con = DataControl.openData();

            // Prepare the DELETE statement
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);

            // Execute the DELETE statement
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Poetry with id " + id + " deleted successfully.");
            } else {
                System.out.println("No poetry found with id " + id);
            }

        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
        } finally {
            // Close the statement and connection
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Failed to close statement: " + e.getMessage());
            }

            DataControl.closeData(con);
        }
    }
        // 删除用户
    public static void UserDelete(int id) {
        String query = "DELETE FROM user WHERE user_id = ?";
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            // Open database connection
            con = DataControl.openData();

            // User the DELETE statement
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);

            // Execute the DELETE statement
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User with id " + id + " deleted successfully.");
            } else {
                System.out.println("No user found with id " + id);
            }

        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
        } finally {
            // Close the statement and connection
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Failed to close statement: " + e.getMessage());
            }

            DataControl.closeData(con);
        }
    }
}
