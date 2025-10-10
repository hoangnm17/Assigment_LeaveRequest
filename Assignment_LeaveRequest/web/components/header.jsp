<%-- 
    Document   : header
    Created on : Oct 11, 2025, 12:07:07 AM
    Author     : 84911
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <header class="header">
            <div class="header-left">
                <h2>🏢 Hệ thống quản lý đơn xin nghỉ</h2>
            </div>
            <div class="header-right">
                <span>Xin chào, <strong><%= session.getAttribute("userName") != null ? session.getAttribute("userName") : "Khách" %></strong></span>
            </div>
        </header>


    </body>
</html>
