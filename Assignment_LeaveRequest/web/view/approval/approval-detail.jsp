<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.leave.LeaveRequest"%>
<%@page import="dal.LeaveRequestDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String idParam = request.getParameter("id");
    LeaveRequest lr = null;

    if (idParam != null && !idParam.trim().isEmpty()) {
        try {
            int id = Integer.parseInt(idParam);
            LeaveRequestDAO dao = new LeaveRequestDAO();
            lr = dao.get(id);
        } catch (NumberFormatException e) {
            out.println("<p class='error'>❌ ID không hợp lệ.</p>");
        }
    } else {
        out.println("<p class='warning'>⚠️ Thiếu tham số 'id' trên URL hoặc request.</p>");
    }
%>

<%@ include file="/common/sidebar.jsp" %>

<style>
    body {
        font-family: "Segoe UI", sans-serif;
        background-color: #f4f6f9;
        margin: 0;
        padding: 0;
    }

    .container {
        max-width: 850px;
        margin: 50px auto;
        background: #fff;
        border-radius: 12px;
        box-shadow: 0 4px 20px rgba(0,0,0,0.1);
        padding: 30px 40px;
    }

    .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 25px;
    }

    .header h2 {
        color: #333;
        margin: 0;
    }

    .status {
        background-color: #e9f2ff;
        color: #004aad;
        padding: 8px 15px;
        border-radius: 8px;
        font-weight: 600;
    }

    .info p {
        margin: 8px 0;
        font-size: 15px;
        color: #333;
    }

    .info b {
        color: #1c1c1c;
    }

    .reason-box {
        background: #f9fafc;
        padding: 15px;
        border-left: 4px solid #0078d7;
        border-radius: 6px;
        margin-top: 10px;
        font-style: italic;
    }

    form {
        margin-top: 30px;
    }

    label {
        font-weight: 600;
        color: #333;
    }

    textarea {
        width: 100%;
        border: 1px solid #ccc;
        border-radius: 8px;
        padding: 10px;
        margin-top: 8px;
        resize: vertical;
        font-family: inherit;
        font-size: 14px;
        background: #fdfdfd;
    }

    .actions {
        display: flex;
        justify-content: flex-end;
        gap: 15px;
        margin-top: 25px;
    }

    .btn {
        padding: 10px 25px;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        font-weight: 600;
        transition: all 0.25s;
        font-size: 15px;
    }

    .btn-approve {
        background-color: #28a745;
        color: #fff;
    }

    .btn-reject {
        background-color: #dc3545;
        color: #fff;
    }

    .btn:hover {
        opacity: 0.9;
        transform: scale(1.03);
    }

    .alert {
        margin-top: 20px;
        padding: 12px 15px;
        border-radius: 8px;
        background-color: #e8f4fc;
        color: #0062cc;
    }

    .error {
        color: red;
        text-align: center;
        font-weight: bold;
        margin-top: 20px;
    }

    .warning {
        color: #d39e00;
        text-align: center;
        font-weight: bold;
        margin-top: 20px;
    }
</style>

<div class="container">
    <% if (lr != null) { %>
    <div class="header">
        <h2>Chi tiết đơn nghỉ #<%= idParam %></h2>
        <div class="status">Trạng thái: <%= lr.getStatus() %></div>
    </div>

    <div class="info">
        <p><b>👤 Người tạo:</b> <%= lr.getCreated_by().getEmployeeName() %></p>
        <p><b>📧 Email:</b> <%= lr.getCreated_by().getEmail() %></p>
        <p><b>🗓️ Thời gian nghỉ:</b> <%= lr.getStartDate() %> → <%= lr.getEndDate() %></p>
        <p><b>📂 Loại nghỉ:</b> <%= lr.getLeaveType().getTypeName() %></p>
        <p><b>📝 Lý do:</b></p>
        <div class="reason-box"><%= lr.getReason() %></div>
    </div>

    <form action="<%= request.getContextPath() %>/approval/update" method="post">
        <input type="hidden" name="requestId" value="<%= idParam %>">

        <label for="notes">✏️ Ghi chú của người duyệt:</label>
        <textarea id="notes" name="notes" rows="4" placeholder="Nhập ghi chú tại đây..." required></textarea>

        <div class="actions">
            <button type="submit" name="action" value="reject" class="btn btn-reject">❌ Từ chối</button>
            <button type="submit" name="action" value="approve" class="btn btn-approve">✅ Duyệt</button>
        </div>
    </form>

    <c:if test="${not empty message}">
        <div class="alert">${message}</div>
    </c:if>
    <% } else { %>
    <p class="error">Không tìm thấy đơn nghỉ phù hợp.</p>
    <% } %>
</div>
