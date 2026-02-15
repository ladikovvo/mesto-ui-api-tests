package com.company.db;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbClient {

    private Connection open() throws SQLException {
        return DriverManager.getConnection(DbConfig.url(), DbConfig.user(), DbConfig.pass());
    }



    public int selectOne() {
        try (Connection conn = open();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT 1")) {

            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException("DB connection/query failed", e);
        }
    }

    public void insertTestUser(String email) {
        String sql = "INSERT INTO test_users(email) VALUES (?)";

        try (Connection conn = open();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.executeUpdate();


        } catch (SQLException exc) {
            throw new RuntimeException("DB insert failed", exc);
        }
    }

    public boolean testUserExists(String email) {
        String sql = "SELECT EXISTS (SELECT 1 FROM test_users WHERE email = ?)";

        try (Connection conn = open();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getBoolean("exists");
            }
        } catch (SQLException exc) {
            throw new RuntimeException("DB exists failed", exc);
        }
    }

    public int deleteTestUserByEmail(String email) {
        String sql = "DELETE FROM test_users WHERE email = ?";
        try (Connection conn = open();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            return ps.executeUpdate(); // сколько строк удалили
        } catch (SQLException e) {
            throw new RuntimeException("DB delete failed", e);
        }
    }

    public List<String> getLatestTestUsers(int limit){
        if(limit < 1)
            throw new IllegalArgumentException("Limit should be >= 1");

        String sql = "SELECT email FROM test_users ORDER BY created_at DESC LIMIT ?";

        try (Connection conn = open();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);

            try(ResultSet rs = ps.executeQuery()){
                List<String> emails = new ArrayList<>();
                while(rs.next()){
                    emails.add(rs.getString("email"));
                }
                return emails;
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB query failed (getLatestTestUsers)", e);
        }
    }

    public int countTestUsers(){
        String sql = "SELECT count(*) FROM test_users";

        try (Connection conn = open();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (!rs.next()) {
                throw new RuntimeException("DB count failed: no rows returned");
            }
            return rs.getInt(1);


        } catch (SQLException e) {
            throw new RuntimeException("DB count failed", e);
        }
    }

}
