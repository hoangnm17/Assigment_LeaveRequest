<%-- 
    Document   : requestApp
    Created on : Oct 13, 2025
    Author     : 84911
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Đơn xin nghỉ - Nhân viên</title>

        <%-- CSS chung --%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styledashboard.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_requestapp.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    </head>

    <body>
        <%-- Include header/sidebar/footer --%>
        <%@ include file="/components/header.jsp" %>
        <%@ include file="/components/sidebar.jsp" %>

        <div class="main-content">
            <div class="card">
                <h2>📝 Gửi đơn xin nghỉ</h2>
                <form action="${pageContext.request.contextPath}/app/create" method="post">

                    <label for="leaveType">Loại nghỉ</label>
                    <select id="leaveType" name="leaveType" required>
                        <option value="">-- Chọn loại nghỉ --</option>
                        <option value="1">Nghỉ phép</option>
                        <option value="2">Nghỉ ốm</option>
                        <option value="3">Nghỉ việc riêng</option>
                        <option value="4">Khác</option>
                    </select>

                    <label for="startDate">Từ ngày</label>
                    <input type="date" id="startDate" name="startDate" required>

                    <label for="endDate">Đến ngày</label>
                    <input type="date" id="endDate" name="endDate" required>

                    <label for="reason">Lý do</label>
                    <textarea id="reason" name="reason" placeholder="Nhập lý do xin nghỉ..." required></textarea>

                    <button type="submit">Gửi đơn</button>
                </form>

                <c:if test="${not empty message}">
                    <p class="message">${message}</p>
                </c:if>
            </div>
        </div>

        <%@ include file="/components/footer.jsp" %>
    </body>
</html>
