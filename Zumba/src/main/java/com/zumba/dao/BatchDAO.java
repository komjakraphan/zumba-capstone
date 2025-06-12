package com.zumba.dao;

import com.zumba.model.Batch;
import java.sql.SQLException;
import java.util.List;

public interface BatchDAO {
    void addBatch(Batch batch) throws SQLException;
    List<Batch> getAllBatches() throws SQLException;
    void deleteBatch(int id) throws SQLException;
    boolean updateBatch(Batch batch) throws SQLException;
    Batch getBatch(int id) throws SQLException;
}
