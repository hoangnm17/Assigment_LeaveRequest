<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.leave.LeaveRequest"%>
<%@page import="dal.LeaveRequestDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // --- Lấy id từ request ---
    String idParam = request.getParameter("id");
    LeaveRequest lr = null;

    if (idParam != null && !idParam.trim().isEmpty()) {
        try {
            int id = Integer.parseInt(idParam);
            LeaveRequestDAO dao = new LeaveRequestDAO();
            lr = dao.get(id);
        } catch (NumberFormatException e) {
            out.println("<p style='color:red;'>❌ ID không hợp lệ.</p>");
        }
    } else {
        out.println("<p style='color:red;'>⚠️ Thiếu tham số 'id' trên URL hoặc request.</p>");
    }
%>
<%@ include file="/components/sidebar.jsp" %>
<% if (lr != null) { %>
<h2>Chi tiết đơn nghỉ #<%= idParam %></h2>
<p><b>Người tạo:</b> <%= lr.getCreated_by().getEmployeeName() %></p>
<p><b>Loại nghỉ:</b> <%= lr.getLeaveType().getTypeName() %></p>
<p><b>Từ ngày:</b> <%= lr.getStartDate() %></p>
<p><b>Đến ngày:</b> <%= lr.getEndDate() %></p>
<p><b>Lý do:</b> <%= lr.getReason() %></p>
<p><b>Trạng thái:</b> <%= lr.getStatus() %></p>

<hr>

<form action="<%= request.getContextPath() %>/approval/update" method="post">
    <input type="hidden" name="requestId" value="<%= idParam %>">
    <label>Ghi chú của người duyệt:</label><br>
    <textarea name="notes" rows="4" cols="50" required></textarea><br><br>
    <button type="submit" name="action" value="approve">✅ Duyệt</button>
    <button type="submit" name="action" value="reject" style="background-color:red;">❌ Từ chối</button>
</form>

<c:if test="${not empty message}">
    <p class="message">${message}</p>
</c:if>
<% } else { %>
<p style="color:red;">Không tìm thấy đơn nghỉ phù hợp.</p>
<% } %>
