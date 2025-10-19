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
    </head>
    <body>

        <c:set var="role" value="${sessionScope.user.role.roleName}" />

        <aside class="sidebar">
            <ul>
                <%-- Using <c:choose> is the JSTL equivalent of a Java switch or if-else-if block --%>
                <c:choose>
                    <%-- Case 1: Role is 'Admin' --%>
                    <c:when test="${role == 'Admin'}">
                        <li><a href="${pageContext.request.contextPath}/view/dashboards/admin.jsp">🏠 Trang chủ</a></li>
                        <li><a href="#">👥 Quản lý nhân viên</a></li>
                        <li><a href="#">📂 Quản lý phòng ban</a></li>
                        <li><a href="${pageContext.request.contextPath}/app/list">📝 Tất cả đơn xin nghỉ</a></li>
                        <li><a href="#">💬 Quản lý phản hồi</a></li>
                        <li><a href="#">📞 Quản lý liên hệ</a></li>
                        </c:when>

                    <%-- Case 2: Role is 'Manager' --%>
                    <c:when test="${role == 'Manager'}">
                        <li><a href="${pageContext.request.contextPath}/view/dashboards/manager.jsp">🏠 Trang chủ</a></li>
                        <li><a href="#">📄 Đơn thuộc nhóm</a></li>
                        <li><a href="#">📈 Thống kê nhóm</a></li>
                        <li><a href="#">👤 Hồ sơ cá nhân</a></li>
                        </c:when>

                    <%-- Case 3: Role is 'Employee' --%>
                    <c:when test="${role == 'Employee'}">
                        <li><a href="${pageContext.request.contextPath}/view/dashboards/employee.jsp">🏠 Trang chủ</a></li>
                        <li><a href="${pageContext.request.contextPath}/app/create">📝 Tạo đơn xin nghỉ</a></li>
                        <li><a href="#">📋 Danh sách đơn của tôi</a></li>
                        <li><a href="#">👤 Hồ sơ cá nhân</a></li>
                        </c:when>

                    <%-- Default Case: No role found (not logged in) --%>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/login">🔐 Vui lòng đăng nhập</a></li>
                        </c:otherwise>
                    </c:choose>

                <%-- Common link for all logged-in users --%>
                <c:if test="${not empty role}">
                    <li><a href="${pageContext.request.contextPath}/auth/logout">🚪 Đăng xuất</a></li>
                    </c:if>
            </ul>
        </aside>


    </body>
</html>
