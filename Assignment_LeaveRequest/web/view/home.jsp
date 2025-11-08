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
        <title>Trang chủ - Quản lý đơn nghỉ</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            body {
                margin: 0;
                font-family: 'Segoe UI', sans-serif;
                background: #f4f6f9;
                color: #333;
            }

            .home-container {
                margin-left: 240px; /* chừa chỗ cho sidebar */
                padding: 60px 40px;
                min-height: 100vh;
                background: linear-gradient(to bottom right, #f8fafc, #eef3f7);
                box-sizing: border-box;
            }

            .welcome-card {
                background: white;
                border-radius: 16px;
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
                padding: 50px;
                text-align: center;
                transition: transform 0.2s ease, box-shadow 0.2s ease;
            }

            .welcome-card:hover {
                transform: translateY(-3px);
                box-shadow: 0 6px 25px rgba(0, 0, 0, 0.1);
            }

            .welcome-card h1 {
                font-size: 34px;
                color: #2c3e50;
                margin-bottom: 15px;
            }

            .welcome-card p {
                font-size: 18px;
                color: #555;
                margin: 10px 0 25px;
            }

            .welcome-list {
                display: inline-block;
                text-align: left;
                list-style: none;
                padding: 0;
                margin-bottom: 25px;
            }

            .welcome-list li {
                font-size: 17px;
                margin-bottom: 12px;
                padding-left: 30px;
                position: relative;
            }

            .welcome-list li::before {
                content: "\f00c";
                font-family: "Font Awesome 6 Free";
                font-weight: 900;
                color: #2ecc71;
                position: absolute;
                left: 0;
                top: 1px;
            }

            .welcome-img {
                margin-top: 25px;
                max-width: 280px;
                width: 100%;
                border-radius: 16px;
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
                transition: transform 0.3s ease;
            }

            .welcome-img:hover {
                transform: scale(1.05);
            }

            .btn-start {
                display: inline-block;
                margin-top: 25px;
                padding: 12px 25px;
                background: #2e86de;
                color: white;
                border-radius: 8px;
                text-decoration: none;
                font-weight: 600;
                transition: background 0.3s ease, transform 0.2s ease;
            }

            .btn-start:hover {
                background: #1b4f72;
                transform: translateY(-2px);
            }

        </style>
    </head>
    <body>
        <%@ include file="/common/sidebar.jsp" %>

        <div class="home-container">
            <div class="welcome-card">
                <h1>👋 Xin chào, <strong>${sessionScope.auth.employee.employeeName}</strong>!</h1>
                <p>Chào mừng bạn đến với <strong>Hệ thống Quản lý Đơn Xin Nghỉ</strong> của công ty.</p>

                <ul class="welcome-list">
                    <li>Theo dõi và gửi đơn xin nghỉ của bản thân</li>
                    <li>Nhận thông báo cập nhật trạng thái đơn</li>
                    <li>Đối với quản lý: Duyệt và xem lịch nghỉ nhân viên</li>
                    <li>Đối với admin: Quản lý tài khoản và phân quyền</li>
                </ul>

                <p>Hãy sử dụng menu bên trái để truy cập các chức năng phù hợp với quyền của bạn.</p>

                <a href="${pageContext.request.contextPath}/home" class="btn-start">
                    <i class="fa-solid fa-arrow-right"></i> Bắt đầu ngay
                </a>

                <br>
                <img src="${pageContext.request.contextPath}/images/welcome.png" alt="Welcome" class="welcome-img"/>
            </div>
        </div>
    </body>
</html>
