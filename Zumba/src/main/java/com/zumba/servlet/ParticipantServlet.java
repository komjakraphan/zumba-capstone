package com.zumba.servlet;

import com.zumba.dao.ParticipantDAO;
import com.zumba.dao.ParticipantDAOImpl;
import com.zumba.dao.BatchDAO;
import com.zumba.dao.BatchDAOImpl;
import com.zumba.model.Participant;
import com.zumba.model.Batch;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/participants")
public class ParticipantServlet extends HttpServlet {
    private final ParticipantDAO participantDAO = new ParticipantDAOImpl();
    private final BatchDAO batchDAO = new BatchDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        try {
            String action = req.getParameter("action");

            if("delete".equals(action)) {
                int pid = Integer.parseInt(req.getParameter("pid"));
                participantDAO.deleteParticipant(pid);
                resp.sendRedirect(req.getContextPath() + "/participants");
            }
            else if ("edit".equals(action)){
                int pid = Integer.parseInt(req.getParameter("pid"));
                Participant participant = participantDAO.getParticipant(pid);
                List<Batch> allBatches = batchDAO.getAllBatches();
                req.setAttribute("participant", participant);
                req.setAttribute("allBatches", allBatches);
                req.getRequestDispatcher("/addParticipant.jsp")
                        .forward(req,resp);
                return;
            }

            List<Participant> participants = participantDAO.getAllParticipants();
            req.setAttribute("listParticipants", participants);
            req.getRequestDispatcher("/listParticipants.jsp")
                    .forward(req,resp);
        } catch (SQLException e) {
            throw new ServletException("Database error in ParticipantServlet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            String name = req.getParameter("name");
            String phone = req.getParameter("phone");
            String email = req.getParameter("email");
            String batchId = req.getParameter("time");

            if("update".equals(action)) {
                int pid = Integer.parseInt(req.getParameter("pid"));
                Participant participant = new Participant(pid, name, phone, email);
                if (batchId != null && !batchId.isEmpty()) {
                    Batch batch = batchDAO.getBatch(Integer.parseInt(batchId));
                    participant.setBatchTime(batch.getTime());
                }
                participantDAO.updateParticipant(participant);
            } else {
                Participant participant = new Participant(name, phone, email);
                if (batchId != null && !batchId.isEmpty()) {
                    Batch batch = batchDAO.getBatch(Integer.parseInt(batchId));
                    participant.setBatchTime(batch.getTime());
                }
                participantDAO.addParticipant(participant);
            }
            resp.sendRedirect(req.getContextPath() + "/participants");
        } catch (SQLException e) {
            throw new ServletException("Database error in ParticipantServlet", e);
        }
    }
}
