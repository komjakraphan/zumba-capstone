<%@ page import="com.example.model.Participant" %>
<%@ page import="com.example.model.Batch" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
  Participant participant = (Participant) request.getAttribute("participant");
  List<Batch> batch = (List<Batch>) request.getAttribute("allBatches");
  boolean editing = (participant != null);
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title><%= editing ? "Edit" : "Add New" %> Participant</title>
  <style>
    /* Base reset & font */
    * {
      box-sizing: border-box;
      margin: 0;
      padding: 0;
    }
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background: #f9fafb;
      color: #333;
      line-height: 1.6;
      padding: 2rem;
    }

    /* Centered form container */
    .form-container {
      max-width: 480px;
      margin: 0 auto;
      background: #fff;
      padding: 2rem;
      border-radius: 8px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.05);
    }

    h3 {
      text-align: center;
      margin-bottom: 1.5rem;
      color: #222;
    }

    form label {
      display: block;
      margin-bottom: 0.5rem;
      font-weight: 600;
    }

    form input[type="text"],
    form select {
      width: 100%;
      padding: 0.5rem 0.75rem;
      margin-bottom: 1.25rem;
      border: 1px solid #ccc;
      border-radius: 4px;
      font-size: 1rem;
      transition: border-color 0.2s;
    }
    form input[type="text"]:focus,
    form select:focus {
      outline: none;
      border-color: #007BFF;
    }

    button[type="submit"] {
      width: 100%;
      padding: 0.75rem;
      background: #007BFF;
      border: none;
      border-radius: 4px;
      color: #fff;
      font-size: 1rem;
      font-weight: 600;
      cursor: pointer;
      transition: background 0.2s;
    }
    button[type="submit"]:hover {
      background: #0056b3;
    }

    .back-link {
      display: block;
      text-align: center;
      margin-top: 1.5rem;
      font-size: 0.95rem;
    }
    .back-link a {
      color: #555;
      text-decoration: none;
      transition: color 0.2s;
    }
    .back-link a:hover {
      color: #007BFF;
    }
  </style>
</head>
<body>
  <div class="form-container">
    <h3><%= editing ? "Edit" : "Add New" %> Participant</h3>
    <form action="participants" method="post">
      <input type="hidden" name="action" value="<%= editing ? "update" : "insert" %>"/>
      <% if (editing) { %>
        <input type="hidden" name="id" value="<%= participant.getId() %>"/>
      <% } %>

      <label for="name">Name</label>
      <input
        id="name" type="text" name="name" required
        value="<%= editing ? participant.getName() : "" %>"/>

      <label for="phone">Phone</label>
      <input
        id="phone" type="text" name="phone" required
        value="<%= editing ? participant.getPhone() : "" %>"/>

      <label for="email">Email</label>
      <input
       id="email" type="text" name="email" required
       value="<%= editing ? participant.getEmail() : "" %>"/>

      <label for="Batch">Class Time</label>
      <select id="batch" name="batch" class="form-select">
        <option value="">-- Select Time --</option>
        <% if (categories != null) {
            for (Batch batch : batches) { %>
          <option value="<%= batch.getId() %>"
            <%= (editing && participant.getBatchTime() != null && participant.getBatchTime().equals(batch.getTime())) ? "selected" : "" %>>
            <%= batch.getTime() %>
          </option>
        <% }
          } %>
      </select>

      <button type="submit">
        <%= editing ? "Update Participant" : "Add Participant" %>
      </button>
    </form>
    <p class="back-link"><a href="books">← Back to list</a></p>
  </div>
</body>
</html>
