<%-- 
    Document   : sidebar
    Created on : Oct 11, 2025, 12:07:24 AM
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

        <%
            String role = "HR";
        %>

        <aside class="sidebar">
            <ul>
                <% if ("Admin".equals(role)) { %>
                <li><a href="../dashboard/admin.jsp">🏠 Trang chủ</a></li>
                <li><a href="#">👥 Quản lý nhân viên</a></li>
                <li><a href="#">📂 Quản lý phòng ban</a></li>
                <li><a href="#">📝 Tất cả đơn xin nghỉ</a></li>
                <li><a href="#">💬 Quản lý phản hồi</a></li>
                <li><a href="#">📞 Quản lý liên hệ</a></li>

                <% } else if ("HR".equals(role)) { %>
                <li><a href="../dashboard/hr.jsp">🏠 Trang chủ</a></li>
                <li><a href="#">📄 Đơn chờ duyệt</a></li>
                <li><a href="#">📊 Báo cáo nghỉ phép</a></li>
                <li><a href="#">👤 Hồ sơ nhân viên</a></li>
                <li><a href="#">💬 Quản lý phản hồi</a></li>
                <li><a href="#">📞 Quản lý liên hệ</a></li>

                <% } else if ("Manager".equals(role)) { %>
                <li><a href="../dashboard/manager.jsp">🏠 Trang chủ</a></li>
                <li><a href="#">📄 Đơn thuộc nhóm</a></li>
                <li><a href="#">📈 Thống kê nhóm</a></li>
                <li><a href="#">👤 Hồ sơ cá nhân</a></li>

                <% } else if ("Employee".equals(role)) { %>
                <li><a href="../dashboard/employee.jsp">🏠 Trang chủ</a></li>
                <li><a href="#">📝 Tạo đơn xin nghỉ</a></li>
                <li><a href="#">📋 Danh sách đơn của tôi</a></li>
                <li><a href="#">👤 Hồ sơ cá nhân</a></li>

                <% } else { %>
                <li><a href="../../../index.jsp">🔐 Vui lòng đăng nhập</a></li>
                    <% } %>

                <li><a href="../../logout">🚪 Đăng xuất</a></li>
            </ul>
        </aside>


    </body>
</html>
