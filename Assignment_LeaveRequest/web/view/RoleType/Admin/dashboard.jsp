<%-- 
    Document   : dashboard
    Created on : Oct 10, 2025, 11:55:10 PM
    Author     : 84911
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Dashboard - Quản trị viên</title>
        <link rel="stylesheet" href="../../../css/styledashboard.css"/>
    </head>
    <body>

        <%@ include file="../../../components/header.jsp" %>
        <%@ include file="../../../components/sidebar.jsp" %>

        <div class="main-content">
            <div class="card">
                <h2>📊 Bảng điều khiển quản trị</h2>
                <p>Tổng số nhân viên: 124</p>
                <p>Đơn xin nghỉ chờ duyệt: 8</p>
                <p>Báo cáo phản hồi: 3</p>
            </div>
        </div>

        <%@ include file="../../../components/footer.jsp" %>



    </body>
</html>
