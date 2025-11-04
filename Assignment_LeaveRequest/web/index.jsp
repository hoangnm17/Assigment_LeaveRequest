<%-- 
    Document   : a
    Created on : Oct 2, 2025, 11:54:55 PM
    Author     : 84911
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HR Home</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #2c2323;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
                text-align: center;
            }

            .welcome-container {
                background-color: #3f3838;
                padding: 3rem 4rem;
                border-radius: 8px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                width: 450px;
            }

            .welcome-container h1 {
                color: #ffffff;
                margin-bottom: 1rem;
            }

            .welcome-container p {
                font-size: 1.1rem;
                color: #ffffff;
                margin-bottom: 2.5rem;
            }

            /* Đây là CSS để biến thẻ <a> thành một nút bấm đẹp */
            .btn-login {
                display: inline-block;
                padding: 0.85rem 1.75rem;
                border: none;
                border-radius: 4px;
                background-color: #007bff;
                color: white;
                font-size: 1.1rem;
                font-weight: 600;
                text-decoration: none; /* Bỏ gạch chân của thẻ link */
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .btn-login:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>

        <div class="welcome-container">
            <h1>Chào mừng bạn!</h1>
            <p>Đây là hệ thống quản lý ABC. Vui lòng đăng nhập để tiếp tục.</p>

            <a href="auth/login" class="btn-login">Đi đến Đăng nhập</a>
        </div>

    </body>
</html>
