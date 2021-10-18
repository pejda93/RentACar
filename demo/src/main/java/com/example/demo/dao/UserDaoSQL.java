package com.example.demo.dao;

import com.example.demo.db.DatabaseConnection;
import com.example.demo.model.CarModel;
import com.example.demo.model.UserModel;
import com.example.demo.model.request.LoginRequestModel;
import com.example.demo.model.request.RegisterRequestModel;
import com.example.demo.model.response.LoginResponseModel;
import com.example.demo.model.response.RegisterResponseModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDaoSQL implements UserDao{
    private static final Connection conn = DatabaseConnection.getConnection();

    public static boolean isEmail(String s) { //TODO: implementirati gde treba
        return s.matches("^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}");
    }

    public static boolean isPasswordValid(String s){
        String numRegex   = ".*[0-9].*";
        String alphaRegex = ".*[A-Z].*";
        String alphaLowerRegex = ".*[a-z].*";

        return s.length() >= 8 && s.matches(numRegex) && (s.matches(alphaRegex) || s.matches(alphaLowerRegex));
    }

    @Override
    public RegisterResponseModel registerUser(RegisterRequestModel rrm) {
//        String query = "INSERT INTO users (user_id, username, email, password, first_name, last_name, phone_number, personal_number, image, admin) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

        if (!isEmail(rrm.getEmail())) return new RegisterResponseModel(false,"Email je nevalidan");
        if (rrm.getUsername().length() < 3) return new RegisterResponseModel(false,"Username je kraci od 3 slova");
        if (!isPasswordValid(rrm.getPassword())) return new RegisterResponseModel(false,"Nevalidna sifra");

        String query = "INSERT INTO users (user_id, username, email, password,admin) " +
                "VALUES (?, ?, ?, ?,false) ";

        try {
            PreparedStatement st = conn.prepareStatement(query);

            st.setString(1, rrm.getUser_id().toString());
            st.setString(2,rrm.getUsername());
            st.setString(3,rrm.getEmail());
            st.setString(4,rrm.getPassword());

            st.executeUpdate();

            return new RegisterResponseModel(true,rrm.getUsername()+" - "+rrm.getEmail()+" је успешно регистрован.");



        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new RegisterResponseModel(false,"Username је заузет");

    }

    @Override
    public LoginResponseModel loginUser(LoginRequestModel lrm) {
        String query = "SELECT user_id FROM users " +
                "WHERE (email = '"+lrm.getIdentification()+"' OR username = '"+lrm.getIdentification()+"') " +
                "AND password = '"+lrm.getPassword()+"' ";

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) return new LoginResponseModel(true,rs.getString(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new LoginResponseModel(false,"Погрешан username/email или password.");
    }

    @Override
    public LoginResponseModel update(UserModel um, String newPassword) {
        UserModel umSQL = new UserModel();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users WHERE user_id = '" + um.getUser_id().toString() +"' ");

            if (rs.next()) {
                umSQL.setUser_id(UUID.fromString(rs.getString(1)));
                umSQL.setUsername(rs.getString(2));
                umSQL.setEmail(rs.getString(3));
                umSQL.setPassword(rs.getString(4));
                umSQL.setFirst_name(rs.getString(5));
                umSQL.setLast_name(rs.getString(6));
                umSQL.setPhone_number(rs.getString(7));
                umSQL.setPersonal_number(rs.getString(8));
                umSQL.setImage(rs.getString(9));
                umSQL.setAdmin(rs.getBoolean(10));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return new LoginResponseModel(false, "Погрешан user id");
        }

        if (!um.getPassword().equals(umSQL.getPassword())) return new LoginResponseModel(false, "Password not valid");

        if (newPassword != null){
            if (!isPasswordValid(newPassword)) return new LoginResponseModel(false, "New Password not valid");
            um.setPassword(newPassword);
        }

        if (um.getUsername().length() < 3) return new LoginResponseModel(false,"Username je kraci od 3 slova");

        if (um.getUsername() == null || um.getUsername().equals("null") || um.getUsername().equals("")) um.setUsername(umSQL.getUsername());
        if (um.getEmail() == null || um.getEmail().equals("null") || um.getEmail().equals("")) um.setEmail(umSQL.getUsername());
        if (um.getPassword() == null || um.getPassword().equals("null") || um.getPassword().equals("")) um.setPassword(umSQL.getUsername());
        if (um.getFirst_name() == null || um.getFirst_name().equals("null") || um.getFirst_name().equals("")) um.setFirst_name(umSQL.getUsername());
        if (um.getLast_name() == null || um.getLast_name().equals("null") || um.getLast_name().equals("")) um.setLast_name(umSQL.getUsername());
        if (um.getPhone_number() == null || um.getPhone_number().equals("null") || um.getPhone_number().equals("")) um.setPhone_number(umSQL.getUsername());
        if (um.getPersonal_number() == null || um.getPersonal_number().equals("null") || um.getPersonal_number().equals("")) um.setPersonal_number(umSQL.getUsername());
        if (um.getImage() == null || um.getImage().equals("null") || um.getImage().equals("")) um.setImage(umSQL.getUsername());

        try {
            PreparedStatement st = conn.prepareStatement("UPDATE users SET username = ?, email = ?, password = ?, first_name = ?, last_name = ?, " +
                    "phone_number = ?, personal_number = ?, image = ?, admin = ? " +
                    "WHERE user_id = ? ");

            st.setString(1,um.getUsername());
            st.setString(2,um.getEmail());
            st.setString(3,um.getPassword());
            st.setString(4,um.getFirst_name());
            st.setString(5,um.getLast_name());
            st.setString(6,um.getPhone_number());
            st.setString(7,um.getPersonal_number());
            st.setString(8,um.getImage());
            st.setBoolean(9,um.isAdmin());
            st.setString(10,um.getUser_id().toString());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new LoginResponseModel(true, "Changed.");
    }

    @Override
    public UserModel getUser(UUID userId) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users WHERE user_id = '"+userId.toString()+"' ");

            if (rs.next())
                return new UserModel(UUID.fromString(rs.getString(1)), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getBoolean(10));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<UserModel> getAllUsers() {
        List<UserModel> users = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users ORDER BY username");

            while (rs.next()) users.add(new UserModel(UUID.fromString(rs.getString(1)), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getBoolean(10)));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public boolean isAdmin(UUID userId) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT admin FROM users WHERE user_id = '"+userId.toString()+"' ");

            if (rs.next())
                return rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public LoginResponseModel AdminUpdate(UserModel um) {
        UserModel umSQL = new UserModel();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users WHERE user_id = '" + um.getUser_id().toString() +"' ");

            if (rs.next()) {
                umSQL.setUser_id(UUID.fromString(rs.getString(1)));
                umSQL.setUsername(rs.getString(2));
                umSQL.setEmail(rs.getString(3));
                umSQL.setPassword(rs.getString(4));
                umSQL.setFirst_name(rs.getString(5));
                umSQL.setLast_name(rs.getString(6));
                umSQL.setPhone_number(rs.getString(7));
                umSQL.setPersonal_number(rs.getString(8));
                umSQL.setImage(rs.getString(9));
                umSQL.setAdmin(rs.getBoolean(10));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return new LoginResponseModel(false, "Погрешан user id");
        }

        if (um.getEmail() != null && !isEmail(um.getEmail())) return new LoginResponseModel(false,"Email je nevalidan");
        if (!isPasswordValid(um.getPassword())) return new LoginResponseModel(false,"Nevalidna sifra");
        if (um.getUsername().length() < 3) return new LoginResponseModel(false,"Username je kraci od 3 slova");

        if (um.getPassword() == null || um.getPassword().equals("null") || um.getPassword().equals("")) um.setPassword(umSQL.getPassword());
        if (um.getUsername() == null || um.getUsername().equals("null") || um.getUsername().equals("")) um.setUsername(umSQL.getUsername());
        if (um.getEmail() == null || um.getEmail().equals("null") || um.getEmail().equals("")) um.setEmail(umSQL.getUsername());
        if (um.getPassword() == null || um.getPassword().equals("null") || um.getPassword().equals("")) um.setPassword(umSQL.getUsername());
        if (um.getFirst_name() == null || um.getFirst_name().equals("null") || um.getFirst_name().equals("")) um.setFirst_name(umSQL.getUsername());
        if (um.getLast_name() == null || um.getLast_name().equals("null") || um.getLast_name().equals("")) um.setLast_name(umSQL.getUsername());
        if (um.getPhone_number() == null || um.getPhone_number().equals("null") || um.getPhone_number().equals("")) um.setPhone_number(umSQL.getUsername());
        if (um.getPersonal_number() == null || um.getPersonal_number().equals("null") || um.getPersonal_number().equals("")) um.setPersonal_number(umSQL.getUsername());
        if (um.getImage() == null || um.getImage().equals("null") || um.getImage().equals("")) um.setImage(umSQL.getUsername());

        try {
            PreparedStatement st = conn.prepareStatement("UPDATE users SET username = ?, email = ?, password = ?, first_name = ?, last_name = ?, " +
                    "phone_number = ?, personal_number = ?, image = ?, admin = ? " +
                    "WHERE user_id = ? ");

            st.setString(1,um.getUsername());
            st.setString(2,um.getEmail());
            st.setString(3,um.getPassword());
            st.setString(4,um.getFirst_name());
            st.setString(5,um.getLast_name());
            st.setString(6,um.getPhone_number());
            st.setString(7,um.getPersonal_number());
            st.setString(8,um.getImage());
            st.setBoolean(9,um.isAdmin());
            st.setString(10,um.getUser_id().toString());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new LoginResponseModel(true, "Changed.");
    }
}
