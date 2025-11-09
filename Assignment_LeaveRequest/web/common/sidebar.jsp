<%-- 
    Document   : sidebar
    Created on : Oct 11, 2025, 12:07:24 AM
    Author     : 84911
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/sidebar.css">

    </head>
    <body>

        <aside class="sidebar">
            <p>Xin chào, <strong>${sessionScope.auth.employee.employeeName}</strong>!</p>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/home">Trang chủ</a></li>

                    <c:if test="${sessionScope.auth.active and sessionScope.permissions.contains('leave:*')}">
                        <li><a href="${pageContext.request.contextPath}/request/create">Tạo đơn xin nghỉ</a></li>
                        </c:if>

                    <c:if test="${sessionScope.auth.active and sessionScope.permissions.contains('approve:*')}">
                        <li><a href="${pageContext.request.contextPath}/approval/list">Duyệt đơn</a></li>
                        </c:if>

                    <c:if test="${sessionScope.auth.active and sessionScope.permissions.contains('leave:*')}">
                        <li><a href="${pageContext.request.contextPath}/request/list">Xem tất cả đơn</a></li>
                        </c:if>

                    <c:if test="${sessionScope.auth.active and sessionScope.permissions.contains('agenda')}">
                        <li><a href="${pageContext.request.contextPath}/agenda">Quản lý người dùng</a></li>
                        </c:if>

                    <li><a href="${pageContext.request.contextPath}/auth/logout">Đăng xuất</a></li>
                </ul>
            </nav>
        </aside>



    </body>
</html>