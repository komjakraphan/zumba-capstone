package com.zumba.dao;

import com.zumba.model.Batch;
import java.sql.*;
import java.util.*;

public class BatchDAOImpl implements BatchDAO {
    private  final String jdbcURL = "jdbc:sqlite:/Users/komjakraphan/Documents/caltech-class/projects/two-capstone/Zumba/data/zumba.db";

    @Override
    public void addBatch(Batch batch) throws SQLException {
        String sql = "INSERT INTO batch(time) VALUES(?)";
        try (Connection c = DriverManager.getConnection(jdbcURL);
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, batch.getTime());
            ps.executeUpdate();
        }
    }

    @Override
    public List<Batch> getAllBatches() throws SQLException {
        List<Batch> list = new ArrayList<>();
        String sql = "SELECT id, time FROM batch";
        try (Connection c = DriverManager.getConnection(jdbcURL);
            Statement stmt  = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Batch(
                        rs.getInt("id"),
                        rs.getString("time")
                ));
            }
        }
        return list;
    }

    @Override
    public void deleteBatch(int id) throws SQLException {
        String updateBatch = "UPDATE participants SET batch_id = NULL WHERE batch_id = ?";
        try (Connection c = DriverManager.getConnection(jdbcURL);
            PreparedStatement ps = c. prepareStatement(updateBatch)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }

        String sql = "DELETE FROM batch WHERE id = ?";
        try (Connection c = DriverManager.getConnection(jdbcURL);
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public boolean updateBatch(Batch batch) throws SQLException {
        String sql = "UPDATE batch SET time = ? WHERE id = ?";
        try (Connection c = DriverManager.getConnection(jdbcURL);
            PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, batch.getTime());
            stmt.setInt(2, batch.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public Batch getBatch(int id) throws SQLException {
        String sql = "SELECT id, time FROM batch WHERE id = ?";
        try (Connection c = DriverManager.getConnection(jdbcURL);
            PreparedStatement ps = c. prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Batch(
                            rs.getInt("id"),
                            rs.getString("time")
                    );
                }
                return null;
            }
        }
    }

}
