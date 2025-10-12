<%-- 
    Document   : dashboard
    Created on : Oct 10, 2025, 11:57:13 PM
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
                <h2>📋 Trang tổng quan Nhân sự</h2>
                <p>Đơn xin nghỉ chờ xử lý: 5</p>
                <p>Nhân viên đang nghỉ: 2</p>
                <p>Tổng số đơn đã duyệt trong tháng: 37</p>
            </div>
        </div>

        <%@ include file="/components/footer.jsp" %>

    </body>
</html>


