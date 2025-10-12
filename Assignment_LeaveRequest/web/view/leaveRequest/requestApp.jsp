<%-- 
    Document   : requestApp
    Created on : Oct 13, 2025, 12:00:04 AM
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
        <div class="container">
            <h2>Đơn xin nghỉ</h2>
            <form action="#" method="post">
                <label for="employeeName">Họ và tên</label>
                <input type="text" id="employeeName" name="employeeName" placeholder="Nhập họ và tên">

                <label for="leaveType">Loại nghỉ</label>
                <select id="leaveType" name="leaveType">
                    <option value="">-- Chọn loại nghỉ --</option>
                    <option value="phep">Nghỉ phép</option>
                    <option value="om">Nghỉ ốm</option>
                    <option value="viec-rieng">Nghỉ việc riêng</option>
                    <option value="khac">Khác</option>
                </select>

                <label for="startDate">Từ ngày</label>
                <input type="date" id="startDate" name="startDate">

                <label for="endDate">Đến ngày</label>
                <input type="date" id="endDate" name="endDate">

                <label for="reason">Lý do</label>
                <textarea id="reason" name="reason" placeholder="Nhập lý do xin nghỉ..."></textarea>

                <button type="submit">Gửi đơn</button>
            </form>

        </div>
    </body>
</html>
