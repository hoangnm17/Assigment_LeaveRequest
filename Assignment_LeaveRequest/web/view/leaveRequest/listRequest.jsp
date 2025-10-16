<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh sách đơn xin nghỉ</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 30px;
            }
            table {
                border-collapse: collapse;
                width: 100%;
            }
            th, td {
                border: 1px solid #ccc;
                padding: 10px;
                text-align: left;
            }
            th {
                background-color: #f3f3f3;
            }
            tr:hover {
                background-color: #f9f9f9;
            }
        </style>
    </head>
    <body>
        <h2>📋 Danh sách đơn xin nghỉ</h2>

        <c:if test="${empty listApp}">
            <p>Không có đơn nào.</p>
        </c:if>

        <c:if test="${not empty listApp}">
            <table>
                <thead>
                    <tr>
                        <th>Họ tên</th>
                        <th>Loại nghỉ phép</th>
                        <th>Ngày bắt đầu</th>
                        <th>Ngày kết thúc</th>
                        <th>Lý do</th>
                        <th>Trạng thái</th>
                        <th>Ngày tạo</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="req" items="${listApp}">
                        <tr>
                            <td>${req.user.fullName}</td>
                            <td>${req.leaveType.typeName}</td>
                            <td>${req.startDate}</td>
                            <td>${req.endDate}</td>
                            <td>${req.reason}</td>
                            <td>${req.status}</td>
                            <td>${req.createAt}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </body>
</html>
