<%-- 
    Document   : header
    Created on : Oct 11, 2025, 12:07:07 AM
    Author     : 84911
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.User"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <%
            User user = (User) session.getAttribute("user");
            String name = (user != null) ? user.getFullName() : "Khách";
        %>
        
        <header class="header">
            <div class="header-left">
                <h2>🏢 Hệ thống quản lý đơn xin nghỉ</h2>
            </div>
            <div class="header-right">
                <span>Xin chào, <strong><%= name %></strong></span> |

            </div>
        </header>


    </body>
</html>
