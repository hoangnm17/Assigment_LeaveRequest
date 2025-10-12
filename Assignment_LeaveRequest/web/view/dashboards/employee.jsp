<%-- 
    Document   : dashboard
    Created on : Oct 10, 2025, 11:48:44 PM
    Author     : 84911
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Dashboard - Nhân viên</title>
        <%
            String contextPath = request.getContextPath();
        %>

        <link rel="stylesheet" href="<%=contextPath%>/css/styledashboard.css">
        <link rel="stylesheet" href="<%=contextPath%>/css/header.css">
        <link rel="stylesheet" href="<%=contextPath%>/css/sidebar.css">
        <link rel="stylesheet" href="<%=contextPath%>/css/footer.css">
    </head>
    <body>

        <%@ include file="/components/header.jsp" %>
        <%@ include file="/components/sidebar.jsp" %>


        <div class="main-content">
            <div class="card">
                <h2>📝 Trang của Nhân viên</h2>
                <p>Xin chào <%= session.getAttribute("userName") != null ? session.getAttribute("userName") : "bạn" %>!</p>
                <p>Số ngày nghỉ còn lại: 7</p>
                <p>Đơn đang chờ duyệt: 1</p>
            </div>
        </div>

        <%@ include file="/components/footer.jsp" %>

    </body>
</html>
