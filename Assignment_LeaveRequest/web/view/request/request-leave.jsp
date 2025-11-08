<%-- 
    Document   : requestApp
    Created on : Oct 13, 2025
    Author     : 84911
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Tạo đơn xin nghỉ</title>

        <!-- CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styledashboard.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_requestapp.css">

        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f6f8;
                margin: 0;
                display: flex;
            }

            .main-content {
                flex: 1;
                padding: 40px;
            }

            .card {
                background-color: white;
                border-radius: 12px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.1);
                padding: 30px;
                max-width: 600px;
                margin: 0 auto;
            }

            h2 {
                text-align: center;
                margin-bottom: 25px;
                color: #333;
            }

            label {
                display: block;
                margin-top: 15px;
                font-weight: bold;
            }

            input[type="date"],
            select,
            textarea {
                width: 100%;
                padding: 10px;
                margin-top: 6px;
                border: 1px solid #ccc;
                border-radius: 8px;
                font-size: 14px;
            }

            textarea {
                resize: vertical;
            }

            button {
                display: block;
                width: 100%;
                background-color: #4A90E2;
                color: white;
                font-size: 16px;
                padding: 12px;
                border: none;
                border-radius: 8px;
                margin-top: 25px;
                cursor: pointer;
            }

            button:hover {
                background-color: #357ABD;
            }

            .message {
                text-align: center;
                margin-top: 20px;
                font-weight: bold;
            }

            .message.success {
                color: green;
            }

            .message.error {
                color: red;
            }
        </style>
    </head>

    <body>

        <%@ include file="/common/sidebar.jsp" %>

        <div class="main-content">
            <div class="card">
                <h2>📝 Gửi đơn xin nghỉ</h2>

                <form action="${pageContext.request.contextPath}/request/create" method="post">

                    <!-- Loại nghỉ -->
                    <label for="leaveType">Loại nghỉ</label>
                    <select id="leaveType" name="leaveType" required>
                        <option value="">-- Chọn loại nghỉ --</option>
                        <c:forEach var="leavetype" items="${requestScope.leaveTypes}">
                            <option value="${leavetype.id}"
                                <c:if test="${param.leaveType == leavetype.id}">selected</c:if>>
                                ${leavetype.typeName}
                            </option>
                        </c:forEach>
                    </select>

                    <!-- Ngày bắt đầu -->
                    <label for="startDate">Từ ngày</label>
                    <input type="date" id="startDate" name="startDate"
                           value="<c:out value='${param.startDate}'/>" required>

                    <!-- Ngày kết thúc -->
                    <label for="endDate">Đến ngày</label>
                    <input type="date" id="endDate" name="endDate"
                           value="<c:out value='${param.endDate}'/>" required>

                    <!-- Lý do -->
                    <label for="reason">Lý do</label>
                    <textarea id="reason" name="reason" rows="4" required><c:out value='${param.reason}'/></textarea>

                    <button type="submit">Gửi đơn</button>
                </form>

                <!-- Hiển thị thông báo lỗi từ Controller -->
                <c:if test="${not empty error}">
                    <p class="message error">${error}</p>
                </c:if>

                <!-- Hiển thị thông báo thành công -->
                <c:if test="${not empty message}">
                    <p class="message success">${message}</p>
                </c:if>

            </div>
        </div>

    </body>
</html>
