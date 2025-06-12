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
    }

}
