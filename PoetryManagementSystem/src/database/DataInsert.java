package database;

import pojo.Poetry;
import util.SystemConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataInsert {

    // �����ղص�ʫ�ʵ�����
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

    // ������ʫ��
    public static void PoetryInsert(String title,String author, String dynasty, String type, String text) {
        if (title.isEmpty()) title = "����";
        if (author.isEmpty()) author = "����";
        if (dynasty.isEmpty()) dynasty = "����";
        if (type.isEmpty()) type = "���޹���";
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

    // �������û�
    public static boolean UserInsert(String userAccount, String userPassword, String userTelephone) {
        String query = "INSERT INTO user (user_account, user_password, user_telephone) VALUES (?, ?, ?)";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // ���ò���
            stmt.setString(1, userAccount);
            stmt.setString(2, userPassword);
            stmt.setString(3, userTelephone);

            // ִ�в�ѯ
            stmt.executeUpdate();

            System.out.println("User inserted successfully!");

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to insert user.");
            return false;
        }
    }
    // �������Ա
    public static boolean AdminInsert(String adminAccount, String adminPassword, String adminName, String telephone) {
        String query = "INSERT INTO admin (admin_account, admin_password, admin_name, telephone) VALUES (?, ?, ?, ?)";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // ���ò���
            stmt.setString(1, adminAccount);
            stmt.setString(2, adminPassword);
            stmt.setString(3, adminName);
            stmt.setString(4, telephone);

            // ִ�в�ѯ
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
