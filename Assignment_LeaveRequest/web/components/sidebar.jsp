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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">

    </head>
    <body>

        <aside class="sidebar">
            <p>Xin chào, <strong>${sessionScope.auth.employee.employeeName}</strong>!</p>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/home">Trang chủ</a></li>

                    <c:if test="${sessionScope.permissions.contains('leave:create')}">
                        <li><a href="${pageContext.request.contextPath}/request/create">Tạo đơn xin nghỉ</a></li>
                        </c:if>

                    <c:if test="${sessionScope.permissions.contains('leave:approve:team')}">
                        <li><a href="${pageContext.request.contextPath}/approval/list">Duyệt đơn cấp 1</a></li>
                        </c:if>

                    <c:if test="${sessionScope.permissions.contains('leave:approve:dept')}">
                        <li><a href="approval-queue?level=dept">Duyệt đơn cấp 2</a></li>
                        </c:if>

                    <c:if test="${sessionScope.permissions.contains('leave:view:all')}">
                        <li><a href="${pageContext.request.contextPath}/request/list">Xem tất cả đơn</a></li>
                        </c:if>

                    <c:if test="${sessionScope.permissions.contains('admin:manage:users')}">
                        <li><a href="admin/users">Quản lý người dùng</a></li>
                        </c:if>

                    <li><a href="${pageContext.request.contextPath}/auth/logout">Đăng xuất</a></li>
                </ul>
            </nav>
        </aside>



    </body>
</html>
