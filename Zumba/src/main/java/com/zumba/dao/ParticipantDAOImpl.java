package com.zumba.dao;

import com.zumba.model.Batch;
import com.zumba.model.Participant;

import java.sql.*;
import java.util.*;

public class ParticipantDAOImpl implements ParticipantDAO {
    private final String jdbcURL ="jdbc:sqlite:/Users/komjakraphan/Documents/caltech-class/projects/two-capstone/Zumba/data/zumba.db";
    private final BatchDAO batchDAO = new BatchDAOImpl();

    @Override
    public void addParticipant(Participant participant) throws SQLException{
        String sql = "INSERT INTO participants(name, phone, email, batch_id) VALUES (?,?,?,?)";
        try(Connection c = DriverManager.getConnection(jdbcURL);
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, participant.getName());
            ps.setString(2, participant.getPhone());
            ps.setString(3, participant.getEmail());

            String participantBatch = participant.getBatchTime();
            Integer batchId = null;
            if (participantBatch != null && !participantBatch.isEmpty()){
                List<Batch> batch = batchDAO.getAllBatches();
                for (Batch bat : batch) {
                    if(bat.getTime().equals(participantBatch)) {
                        batchId = bat.getId();
                        break;
                    }
                }
            }
            ps.setObject(4, batchId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Participant> getAllParticipants() throws SQLException{
        List<Participant> list = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.phone, p.email, b.time as time " +
                "FROM participants p " +
                "LEFT JOIN batch b ON p.batch_id = b.id";
        try (Connection c = DriverManager.getConnection(jdbcURL);
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()){
                Participant participant = new Participant(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("time")
                );
                list.add(participant);
            }
        }
        for(Participant p : list){
            System.out.println(p.getName() + " goes to zumba in the " + p.getBatchTime());
        }
        return list;
    }

    @Override
    public void deleteParticipant(int pid) throws SQLException {
        String sql = "DELETE FROM participants WHERE id = ?";
        try (Connection c = DriverManager.getConnection(jdbcURL);
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, pid);
            ps.executeUpdate();
        }
    }

    @Override
    public boolean updateParticipant(Participant participant) throws SQLException {
        String sql = "UPDATE participants SET name = ?, phone = ?, email = ?, batch_id = ? WHERE id = ?";
        try (Connection c = DriverManager.getConnection(jdbcURL);
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, participant.getName());
            stmt.setString(2, participant.getPhone());
            stmt.setString(3, participant.getEmail());

            String participantBatch = participant.getBatchTime();
            Integer batchId = null;
            if (participantBatch != null && !participantBatch.isEmpty()) {
                List<Batch> batches = batchDAO.getAllBatches();
                for (Batch bat : batches) {
                    if (bat.getTime().equals(participantBatch)){
                        batchId = bat.getId();
                        break;
                    }
                }
            }
            stmt.setObject(4, batchId);
            stmt.setInt(5, participant.getPid());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public Participant getParticipant(int pid) throws SQLException {
        String sql = "SELECT p.id, p.name, p.phone, p.email, b.time as time " +
                "FROM participants p " +
                "LEFT JOIN batch b ON p.batch_id = b.id " +
                "WHERE p.id = ?";
        try (Connection c = DriverManager.getConnection(jdbcURL);
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, pid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Participant(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getString("time")
                    );
                }
                return null;
            }
        }
    }
}
