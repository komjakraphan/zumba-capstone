package com.zumba.dao;

import com.zumba.model.Participant;
import java.sql.SQLException;
import java.util.List;

public interface ParticipantDAO {
    void addParticipant(Participant participant) throws SQLException;
    List<Participant> getAllParticipants() throws  SQLException;
    void deleteParticipant(int pid) throws SQLException;
    boolean updateParticipant(Participant participant) throws SQLException;
    Participant getParticipant(int pid) throws SQLException;
}
