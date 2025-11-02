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
            select {
                padding: 6px 10px;
                border-radius: 5px;
                border: 1px solid #ccc;
                margin-bottom: 15px;
                font-size: 14px;
            }
            form {
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <%@ include file="/components/sidebar.jsp" %>

        <div class="main-content">
            <h2>📋 Danh sách đơn xin nghỉ</h2>

            <!-- Bộ lọc trạng thái -->
            <form action="${pageContext.request.contextPath}/request/list" method="get">
                <label for="status"><b>Lọc theo trạng thái:</b></label>
                <select name="status" id="status" onchange="this.form.submit()">
                    <option value="all" ${status eq 'all' ? 'selected' : ''}>Tất cả</option>
                    <option value="approved" ${status eq 'approved' ? 'selected' : ''}>Đã duyệt</option>
                    <option value="rejected" ${status eq 'rejected' ? 'selected' : ''}>Từ chối</option>
                    <option value="pending" ${status eq 'pending' ? 'selected' : ''}>Đang chờ</option>
                </select>
            </form>

            <c:if test="${empty listApp}">
                <p>Không có đơn nào cần hiển thị.</p>
            </c:if>

            <c:if test="${not empty listApp}">
                <table>
                    <thead>
                        <tr>
                            <th>Loại nghỉ phép</th>
                            <th>Ngày bắt đầu</th>
                            <th>Ngày kết thúc</th>
                            <th>Lý do</th>
                            <th>Trạng thái</th>
                            <th>Ghi chú duyệt</th>
                            <th>Ngày tạo</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="req" items="${listApp}">
                            <tr>
                                <td>${req.leaveType.typeName}</td>
                                <td>${req.startDate}</td>
                                <td>${req.endDate}</td>
                                <td>${req.reason}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${req.status eq 'Approved'}">✅ Đã duyệt</c:when>
                                        <c:when test="${req.status eq 'Rejected'}">❌ Từ chối</c:when>
                                        <c:otherwise>⏳ Đang chờ</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${req.appStep.notes}</td>
                                <td>${req.created_time}</td>
                            </tr>
                        </c:forEach>
                    </tbody>

                </table>
            </c:if>
        </div>

    </body>
</html>
