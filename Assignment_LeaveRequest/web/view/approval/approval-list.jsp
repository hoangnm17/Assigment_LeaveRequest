<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Duyệt đơn nghỉ phép</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">

        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                display: flex;
                min-height: 100vh;
                background-color: #f7f8fa;
            }
            .main-content {
                flex: 1;
                padding: 30px;
                margin-left: 240px;
            }
            h2 {
                margin-bottom: 20px;
            }
            table {
                border-collapse: collapse;
                width: 100%;
                background-color: #fff;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            }
            th, td {
                border: 1px solid #ddd;
                padding: 10px 12px;
                text-align: left;
            }
            th {
                background-color: #f3f3f3;
            }
            tr:hover {
                background-color: #f9f9f9;
            }
            button {
                background-color: #4A90E2;
                color: white;
                border: none;
                padding: 6px 12px;
                border-radius: 6px;
                cursor: pointer;
            }
            button:hover {
                background-color: #357ABD;
            }

            /* Modal */
            .modal {
                display: none;
                position: fixed;
                z-index: 1000;
                padding-top: 100px;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgba(0,0,0,0.5);
            }
            .modal-content {
                background-color: #fff;
                margin: auto;
                padding: 20px;
                border-radius: 12px;
                width: 60%;
            }
            .close {
                color: #aaa;
                float: right;
                font-size: 28px;
                cursor: pointer;
            }
            .close:hover {
                color: black;
            }
        </style>
    </head>
    <body>
        <%@ include file="/components/sidebar.jsp" %>

        <div class="main-content">
            <h2>📋 Danh sách đơn xin nghỉ</h2>

            <c:if test="${empty list_approval}">
                <p>Không có đơn nào cần duyệt.</p>
            </c:if>

            <c:if test="${not empty list_approval}">
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
                            <th>Phê duyệt</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="req" items="${list_approval}">
                            <tr>
                                <td>${req.created_by.employeeName}</td>
                                <td>${req.leaveType.typeName}</td>
                                <td>${req.startDate}</td>
                                <td>${req.endDate}</td>
                                <td>${req.reason}</td>
                                <td>${req.status}</td>
                                <td>${req.created_time}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/approval/update?id=${req.id}">
                                        Xem chi tiết
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>

    </body>
</html>
