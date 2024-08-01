package database;

import pojo.Administrator;
import pojo.Poetry;
import pojo.User;
import util.SystemConstants;

import java.sql.*;
import java.util.ArrayList;

public class DataSelece {

    // ��������ʫ��
    public static ArrayList<Poetry> getAllPoetrys() {
        ArrayList<Poetry> poetries = new ArrayList<>();
        Connection con = null;
        Statement sta = null;
        ResultSet rs = null;

        try {
            con = DataControl.openData();
            sta = con.createStatement();
            String query = "SELECT * FROM poetry";
            rs = sta.executeQuery(query);

            while (rs.next()) {
                int poetry_id = rs.getInt("poetry_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String dynasty = rs.getString("dynasty");
                int star = getStarNumber(poetry_id);
                String type = rs.getString("type");
                String text = rs.getString("text");
                poetries.add(new Poetry(poetry_id, title, author, dynasty, star, type, text));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // ����ҵ�����󣬾����Ƿ���Ҫ���ؿ��б�������������
            // ����ѡ�񷵻ؿ��б���ӡ�쳣��Ϣ
        } finally {
            // �ر� ResultSet, Statement �� Connection
            try {
                if (rs != null) {
                    rs.close();
                }
                if (sta != null) {
                    sta.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return poetries;
    }

    // ���ҵ�ǰ�û��ղص�����ʫ��
    public static ArrayList<Poetry> getAllStarPoetrys(int userID) throws SQLException {
        Connection con = DataControl.openData();
        Statement sta = con.createStatement();
        String query = "SELECT * FROM poetrydatabase.star_table AS s" +
                "JOIN poetrydatabase.poetry AS p ON s.poetry_id = p.poetry_id" +
                "WHERE  s.user_id = " + userID;
        ResultSet rs = sta.executeQuery(query);

        ArrayList<Poetry> poetries = new ArrayList<>();

        try {
            while (rs.next()) {
                int poetry_id = rs.getInt("poetry_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String dynasty = rs.getString("dynasty");
                int star = getStarNumber(poetry_id);
                String type = rs.getString("type");
                String text = rs.getString("text");
                poetries.add(new Poetry(poetry_id, title, author, dynasty, star, type, text));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // ����ѡ���׳��쳣���߷��ؿ��б��������ҵ������������
            throw e; // �׳��쳣֪ͨ�ϲ�����ߴ���
            // poetries ���Է��ؿյ� ArrayList<Poetry>()��
        } finally {
            // �ر� ResultSet, Statement �� Connection
            if (rs != null) {
                rs.close();
            }
            if (sta != null) {
                sta.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return poetries;
    }

    // ͨ��ʫ��ID����ʫ��
    public static Poetry IDtoPoetry(int Id) {
        Connection con = null;
        Statement sta = null;
        ResultSet rs = null;

        try {
            con = DataControl.openData();
            sta = con.createStatement();
            String query = "SELECT * FROM poetry WHERE poetry_id = " + Id;
            rs = sta.executeQuery(query);

            if (rs.next()) {
                int poetry_id = rs.getInt("poetry_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String dynasty = rs.getString("dynasty");
                int star = getStarNumber(Id);
                String type = rs.getString("type");
                String text = rs.getString("text");
                return new Poetry(poetry_id, title, author, dynasty, star, type, text);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // ������Ը������������־��¼�������쳣����
            return null;
        } finally {
            // ȷ����Դ�ر�
            try {
                if (rs != null) rs.close();
                if (sta != null) sta.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // �鿴�ղر����Ƿ��и�����
    public static boolean isExitCollect(int poetryID) throws SQLException {
        String query = "SELECT * FROM star_table WHERE poetry_id = ? AND user_id = ?";
        try (Connection con = DataControl.openData();
            PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, poetryID);
            pstmt.setInt(2, SystemConstants.currentID);
//            System.out.println(poetryID + " " + SystemConstants.currentID); // debug
            ResultSet rs = pstmt.executeQuery();

            return rs.next();
        }
    }

    // ����ĳһʫ�ʱ��ղصĴ���
    public static int getStarNumber(int poetryID) {
        String query = "SELECT COUNT(*) AS star_count FROM poetrydatabase.star_table WHERE poetry_id = ?";
        int starCount = 0;

        try (Connection con = DataControl.openData();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, poetryID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                starCount = rs.getInt("star_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return starCount;
    }
    // ���ҵ�ǰ�û��ղص�����ʫ��
    public static ArrayList<Poetry> getAllStarPoetry(int userId) {
        ArrayList<Poetry> poetries = new ArrayList<>();

        String query = "SELECT p.* " +
                "FROM poetrydatabase.star_table st " +
                "JOIN poetrydatabase.poetry p ON st.poetry_id = p.poetry_id " +
                "WHERE st.user_id = ?";

        try (Connection con = DataControl.openData();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // ����ʫ�ʱ�������Щ��
                int poetryId = rs.getInt("poetry_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String dynasty = rs.getString("dynasty");
                int star = getStarNumber(poetryId);
                String type = rs.getString("type");
                String text = rs.getString("text");
                poetries.add(new Poetry(poetryId, title, author, dynasty, star, type, text));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return poetries;
    }
    // ͨ���û�ID ��������
    public static String getPassword(String nameID, boolean isUser) {
        String password = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DataControl.openData();
            String query;
            if (isUser){
                query  = "SELECT user_password, user_id FROM poetrydatabase.user WHERE user_account = ?";
            } else {
                query = "SELECT admin_password, admin_id FROM poetrydatabase.admin WHERE admin_account = ?";
            }

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nameID);

            resultSet = preparedStatement.executeQuery();

            if (isUser && resultSet.next()) {
                password = resultSet.getString("user_password");
                SystemConstants.currentID = resultSet.getInt("user_id");
            } else if (resultSet.next()) {
                password = resultSet.getString("admin_password");
                SystemConstants.currentID = resultSet.getInt("admin_id");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return password;

    }
    // ͨ��ID��������
    public static String getPasswordByID(int ID, boolean isUser) {
        String password = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DataControl.openData();
            String query;
            if (isUser){
                query  = "SELECT user_password FROM poetrydatabase.user WHERE user_id = ?";
            } else {
                query = "SELECT admin_password FROM poetrydatabase.admin WHERE admin_id = ?";
            }

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ID);

            resultSet = preparedStatement.executeQuery();

            if (isUser && resultSet.next()) {
                password = resultSet.getString("user_password");
            } else if (resultSet.next()) {
                password = resultSet.getString("admin_password");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return password;

    }
    // ������ģ������ʫ��
    public static ArrayList<Poetry> FindByCriteria(String newId, String newTitle, String newDynast, String newAuthor,
                                                   String newType, String newText) {
        ArrayList<Poetry> poetries = new ArrayList<>();

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM poetry WHERE 1 = 1");

        if (newId != null && !newId.isEmpty()) {
            queryBuilder.append(" AND poetry_id = ?");
        }

        if (newTitle != null && !newTitle.isEmpty()) {
            queryBuilder.append(" AND title LIKE ?");
        }

        if (newDynast != null && !newDynast.isEmpty()) {
            queryBuilder.append(" AND dynasty LIKE ?");
        }

        if (newAuthor != null && !newAuthor.isEmpty()) {
            queryBuilder.append(" AND author LIKE ?");
        }

        if (newType != null && !newType.isEmpty()) {
            queryBuilder.append(" AND type LIKE ?");
        }

        if (newText != null && !newText.isEmpty()) {
            queryBuilder.append(" AND text LIKE ?");
        }

        try (Connection con = DataControl.openData();
             PreparedStatement pstmt = con.prepareStatement(queryBuilder.toString())) {

            int paramIndex = 1;

            if (newId != null && !newId.isEmpty()) {
                pstmt.setInt(paramIndex++, Integer.parseInt(newId));
            }

            if (newTitle != null && !newTitle.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + newTitle + "%");
            }

            if (newDynast != null && !newDynast.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + newDynast + "%");
            }

            if (newAuthor != null && !newAuthor.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + newAuthor + "%");
            }

            if (newType != null && !newType.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + newType + "%");
            }

            if (newText != null && !newText.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + newText + "%");
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int poetryId = rs.getInt("poetry_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String dynasty = rs.getString("dynasty");
                int star = getStarNumber(poetryId);
                String type = rs.getString("type");
                String text = rs.getString("text");
                poetries.add(new Poetry(poetryId, title, author, dynasty, star, type, text));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return poetries;
    }
    // ������ģ�������ղص�ʫ��
    public static ArrayList<Poetry> FindByStarCriteria(String newId, String newTitle, String newDynasty, String newAuthor,
                                                   String newType, String newText) {
        ArrayList<Poetry> poetries = new ArrayList<>();

        StringBuilder queryBuilder = new StringBuilder(
                "SELECT p.* FROM poetry p " +
                        "JOIN star_table s ON p.poetry_id = s.poetry_id " +
                        "WHERE s.user_id = ?"
        );

        if (newId != null && !newId.isEmpty()) {
            queryBuilder.append(" AND p.poetry_id = ?");
        }

        if (newTitle != null && !newTitle.isEmpty()) {
            queryBuilder.append(" AND p.title LIKE ?");
        }

        if (newDynasty != null && !newDynasty.isEmpty()) {
            queryBuilder.append(" AND p.dynasty LIKE ?");
        }

        if (newAuthor != null && !newAuthor.isEmpty()) {
            queryBuilder.append(" AND p.author LIKE ?");
        }

        if (newType != null && !newType.isEmpty()) {
            queryBuilder.append(" AND p.type LIKE ?");
        }

        if (newText != null && !newText.isEmpty()) {
            queryBuilder.append(" AND p.text LIKE ?");
        }

        try (Connection con = DataControl.openData();
             PreparedStatement pstmt = con.prepareStatement(queryBuilder.toString())) {
            int paramIndex = 1;
            pstmt.setInt(paramIndex++, SystemConstants.currentID);

            if (newId != null && !newId.isEmpty()) {
                pstmt.setInt(paramIndex++, Integer.parseInt(newId));
            }

            if (newTitle != null && !newTitle.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + newTitle + "%");
            }

            if (newDynasty != null && !newDynasty.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + newDynasty + "%");
            }

            if (newAuthor != null && !newAuthor.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + newAuthor + "%");
            }

            if (newType != null && !newType.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + newType + "%");
            }

            if (newText != null && !newText.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + newText + "%");
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int poetryId = rs.getInt("poetry_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String dynasty = rs.getString("dynasty");
                int star = getStarNumber(poetryId);
                String type = rs.getString("type");
                String text = rs.getString("text");
                poetries.add(new Poetry(poetryId, title, author, dynasty, star, type, text));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return poetries;
    }
    // ���������û���Ϣ
    public static ArrayList<User> getAllUsers(){
        ArrayList<User> users = new ArrayList<>();
        Connection con = null;
        Statement sta = null;
        ResultSet rs = null;

        try {
            con = DataControl.openData();
            sta = con.createStatement();
            String query = "SELECT * FROM user";
            rs = sta.executeQuery(query);

            while (rs.next()) {
                int user_id = rs.getInt("user_id");
                String name = rs.getString("user_account");
                String password = rs.getString("user_password");
                String telephone = rs.getString("user_telephone");
                int star = getUserStarNumber(user_id);

                users.add(new User(user_id, name, password, telephone, star));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // ����ҵ�����󣬾����Ƿ���Ҫ���ؿ��б�������������
            // ����ѡ�񷵻ؿ��б���ӡ�쳣��Ϣ
        } finally {
            // �ر� ResultSet, Statement �� Connection
            try {
                if (rs != null) {
                    rs.close();
                }
                if (sta != null) {
                    sta.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }
    // ���ҵ�ǰ��¼�û����ղ�����
    private static int getUserStarNumber(int userID) {
        String query = "SELECT COUNT(*) AS star_count FROM poetrydatabase.star_table WHERE user_id = ?";
        int starCount = 0;

        try (Connection con = DataControl.openData();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                starCount = rs.getInt("star_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return starCount;
    }
    // �����������û�
    public static ArrayList<User> findUser(String item, String text) {
        ArrayList<User> users = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String column = "";
        switch (item) {
            case "�û�ID":
                column = "user_id";
                break;
            case "�û���":
                column = "user_account";
                break;
            case "����":
                column = "user_password";
                break;
            case "�绰":
                column = "user_telephone";
                break;
        }

        try {
            con = DataControl.openData();
            String query = "SELECT * FROM user WHERE " + column + " LIKE ?";
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + text + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                int user_id = rs.getInt("user_id");
                String name = rs.getString("user_account");
                String password = rs.getString("user_password");
                String telephone = rs.getString("user_telephone");
                int star = getUserStarNumber(user_id);

                users.add(new User(user_id, name, password, telephone, star));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }
    // ͨ��ID���ҹ���Ա
    public static Administrator findByAdminID(int id){
        Connection con = null;
        Statement sta = null;
        ResultSet rs = null;
        String query = "SELECT * FROM admin WHERE admin_id = " + id;
        try {
            con = DataControl.openData();
            sta = con.createStatement();
            rs = sta.executeQuery(query);

            if (rs.next()){
                int admin_id = rs.getInt("admin_id");
                String account = rs.getString("admin_account");
                String password = rs.getString("admin_password");
                String name = rs.getString("admin_name");
                String tel = rs.getString("telephone");
                return new Administrator(admin_id, account, password, name, tel);
            }
            else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // ͨ��ID�����û�
    public static User findByUserID(int id){
        Connection con = null;
        Statement sta = null;
        ResultSet rs = null;
        String query = "SELECT * FROM user WHERE user_id = " + id;
        try {
            con = DataControl.openData();
            sta = con.createStatement();
            rs = sta.executeQuery(query);

            if (rs.next()){
                int user_id = rs.getInt("user_id");
                String name = rs.getString("user_account");
                String password = rs.getString("user_password");
                String telephone = rs.getString("user_telephone");
                int star = getUserStarNumber(user_id);
                return new User(user_id, name, password, telephone ,star);
            }
            else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
