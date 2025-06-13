package com.zumba.servlet;

import com.zumba.dao.BatchDAO;
import com.zumba.dao.BatchDAOImpl;
import com.zumba.model.Batch;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/batch")
public class BatchServlet extends HttpServlet {
    private final BatchDAO dao = new BatchDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        try {
            String action = req.getParameter("action");

            if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                dao.deleteBatch(id);
                resp.sendRedirect(req.getContextPath() + "/batch");
                return;
            }
            else if ("edit".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Batch batch = dao.getBatch(id);
                req.setAttribute("batch", batch);
                req.getRequestDispatcher("/addBatch.jsp")
                        .forward(req, resp);
                return;
            }

            List<Batch> batch = dao.getAllBatches();
            req.setAttribute("listBatch", batch);
            req.getRequestDispatcher("/listBatches.jsp")
                    .forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Database error in BatchServlet", e);
        }
    }

    @Override
    protected  void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            if ("update".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                String time = req.getParameter("time");
                dao.updateBatch(new Batch(id, time));
            } else {
                String time = req.getParameter("time");
                dao.addBatch(new Batch(time));
            }
            resp.sendRedirect(req.getContextPath() + "/batch");
        }catch (SQLException e) {
            throw new ServletException("Database error in BatchServlet", e);
        }
    }
}
