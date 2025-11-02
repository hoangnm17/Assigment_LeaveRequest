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
        <title>Home</title>
    </head>
    <style>
        .home-intro {
            margin-left: 240px; /* dành chỗ cho sidebar */
            padding: 50px;
            font-family: 'Segoe UI', sans-serif;
            background-color: #f9fafd;
            min-height: 100vh;
            text-align: center;
            box-sizing: border-box;
        }

        .home-intro h1 {
            font-size: 32px;
            color: #2c3e50;
            margin-bottom: 20px;
        }

        .home-intro p {
            font-size: 18px;
            color: #555;
            margin-bottom: 20px;
        }

        .home-intro ul {
            display: inline-block;
            text-align: left;
            color: #555;
            font-size: 16px;
            line-height: 1.6;
            margin-bottom: 20px;
        }

        .home-intro ul li {
            margin-bottom: 8px;
        }

        .welcome-img {
            margin-top: 30px;
            max-width: 300px;
            width: 100%;
            border-radius: 12px;
        }
    </style>
    <body>
        <%@ include file="/components/sidebar.jsp" %>
        <div class="home-intro">
            <h1>Chào mừng, <strong>${sessionScope.auth.employee.employeeName}</strong>!</h1>
            <p>Chào mừng bạn đến với hệ thống quản lý đơn xin nghỉ của công ty.</p>
            <ul>
                <li>Theo dõi và gửi các đơn xin nghỉ của mình</li>
                <li>Nhận thông báo về trạng thái đơn</li>
                <li>Dành cho quản lý: duyệt đơn của nhân viên</li>
                <li>Dành cho admin: quản lý người dùng</li>
            </ul>
            <p>Hãy sử dụng menu bên trái để điều hướng tới các chức năng phù hợp với quyền của bạn.</p>
            <img src="${pageContext.request.contextPath}/images/welcome.png" alt="Welcome" class="welcome-img"/>
        </div>
    </body>
</html>
