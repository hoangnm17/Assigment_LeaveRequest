<%-- 
    Document   : manager
    Created on : Oct 10, 2025, 11:52:50 PM
    Author     : 84911
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Dashboard - Nhân viên</title>
        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styledashboard.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    </head>
    <body>

        <%@ include file="/components/header.jsp" %>
        <%@ include file="/components/sidebar.jsp" %>


        <div class="main-content">
            <div class="card">
                <h2>📈 Trang tổng quan Quản lý</h2>
                <p>Nhóm bạn đang quản lý: Phòng Kỹ thuật</p>
                <p>Đơn chờ duyệt: 2</p>
                <p>Hiệu suất nhóm: 97%</p>
            </div>
        </div>

        <%@ include file="/components/footer.jsp" %>

    </body>
</html>
