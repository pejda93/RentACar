package com.example.demo.dao;

import com.example.demo.db.DatabaseConnection;
import com.example.demo.model.CarModel;
import com.example.demo.model.ContractsModel;
import com.example.demo.model.request.ContractApprovalModel;
import com.example.demo.model.response.RegisterResponseModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContractDaoSQL implements ContractDao{
    private static final Connection conn = DatabaseConnection.getConnection();

    @Override
    public RegisterResponseModel add(ContractsModel cm) {
        boolean inUse = false;

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM cars WHERE car_id NOT IN " +
                    "(SELECT car_id FROM contracts " +
                    "WHERE (('"+cm.getStart_date()+"' BETWEEN start_date AND end_date) OR ('"+cm.getEnd_date()+"' BETWEEN start_date AND end_date)) " +
                    "OR ((start_date BETWEEN '"+cm.getStart_date()+"' AND '"+cm.getEnd_date()+"') OR (end_date BETWEEN '"+cm.getStart_date()+"' AND '"+cm.getEnd_date()+"'))) " +
                    "AND car_id = '"+cm.getCar_id().toString()+"' ");

            if (!rs.next())
//                list.add(new CarModel(UUID.fromString(rs.getString(1)), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(7),rs.getString(13),rs.getString(14),rs.getInt(5),rs.getInt(6),rs.getInt(9),rs.getInt(11),rs.getDouble(8),rs.getString(10),rs.getBoolean(12)));
                    return new RegisterResponseModel(false,"Већ је заузет аутомобил");

        } catch (SQLException e) {
            e.printStackTrace();
//            return new RegisterResponseModel(false,"Већ је заузет аутомобил");
        }

        String query = "INSERT INTO contracts (contract_id, user_id, car_id, start_date, end_date, total_price, signed, approved) " +
                "VALUES (?, ?, ?, '"+cm.getStart_date()+"', '"+cm.getEnd_date()+"', ?, ?, ?) ;";

        try {
            PreparedStatement st = conn.prepareStatement(query);

            st.setString(1, cm.getCar_id().toString());
            st.setString(2, cm.getUser_id().toString());
            st.setString(3, cm.getCar_id().toString());
//            st.setString(4, cm.getStart_date().toString());
//            st.setString(5,cm.getEnd_date().toString());
            st.setDouble(4,cm.getTotal_price());
            st.setBoolean(5, cm.isSigned());
            st.setBoolean(6,cm.isApproved());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new RegisterResponseModel(true,"Уговор креиран, чека одобрење");
    }

    @Override
    public List<ContractsModel> getAllContracts() {
        List<ContractsModel> list = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contracts ORDER BY start_date ");

            while (rs.next())
                list.add(new ContractsModel(UUID.fromString(rs.getString(1)), UUID.fromString(rs.getString(2)),UUID.fromString(rs.getString(3)), LocalDate.parse(rs.getString(4)),LocalDate.parse(rs.getString(5)),rs.getDouble(6),rs.getBoolean(7),rs.getBoolean(8)));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ContractsModel> getAllPendingContracts() {
        List<ContractsModel> list = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contracts WHERE approved = false ORDER BY start_date ");

            while (rs.next())
                list.add(new ContractsModel(UUID.fromString(rs.getString(1)), UUID.fromString(rs.getString(2)),UUID.fromString(rs.getString(3)), LocalDate.parse(rs.getString(4)),LocalDate.parse(rs.getString(5)),rs.getDouble(6),rs.getBoolean(7),rs.getBoolean(8)));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void ContractApproval(boolean isApproved, String contractId) {
        if (isApproved) {
            String query = "UPDATE contracts SET approved = true WHERE contract_id = ? ";
            try {
                PreparedStatement st = conn.prepareStatement(query);

                st.setString(1, contractId.toString());
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            String query = "DELETE FROM contracts WHERE contract_id = ? ";
            try {
                PreparedStatement st = conn.prepareStatement(query);

                st.setString(1, contractId.toString());
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<ContractsModel> getAllUserContracts(String userId) {
        List<ContractsModel> list = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contracts WHERE user_id = '"+userId+"' ");

            while (rs.next())
                list.add(new ContractsModel(UUID.fromString(rs.getString(1)), UUID.fromString(rs.getString(2)),UUID.fromString(rs.getString(3)), LocalDate.parse(rs.getString(4)),LocalDate.parse(rs.getString(5)),rs.getDouble(6),rs.getBoolean(7),rs.getBoolean(8)));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
