<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa đơn nghỉ phép</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">

    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background-color: #f4f6f8;
            margin: 0;
            display: flex;
        }

        .main-content {
            flex: 1;
            margin-left: 240px;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            padding-top: 60px;
            min-height: 100vh;
        }

        .form-container {
            background: #fff;
            padding: 35px 40px;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 600px;
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 25px;
        }

        label {
            font-weight: 600;
            display: block;
            margin-top: 15px;
            color: #444;
        }

        select,
        input[type="date"],
        textarea {
            width: 100%;
            padding: 10px 12px;
            margin-top: 6px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
            transition: border-color 0.2s;
        }

        select:focus,
        input:focus,
        textarea:focus {
            border-color: #4A90E2;
            outline: none;
        }

        textarea {
            height: 90px;
            resize: vertical;
        }

        .actions {
            margin-top: 25px;
            text-align: center;
        }

        button {
            padding: 10px 25px;
            background-color: #4A90E2;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 15px;
            transition: background-color 0.2s;
        }

        button:hover {
            background-color: #357ABD;
        }

        .cancel-link {
            margin-left: 15px;
            color: #555;
            text-decoration: none;
            font-size: 15px;
        }

        .cancel-link:hover {
            text-decoration: underline;
        }

        .error {
            color: red;
            margin-top: 12px;
            text-align: center;
        }
    </style>
</head>
<body>
    <%@ include file="/common/sidebar.jsp" %>

    <div class="main-content">
        <div class="form-container">
            <h2>✏️ Chỉnh sửa đơn nghỉ phép</h2>

            <form method="post" action="${pageContext.request.contextPath}/request/edit">
                <input type="hidden" name="id" value="${leave.id}">

                <label for="typeId">Loại nghỉ phép:</label>
                <select name="typeId" id="typeId" required>
                    <c:forEach var="t" items="${leaveTypes}">
                        <option value="${t.id}" ${t.id == leave.leaveType.id ? 'selected' : ''}>${t.typeName}</option>
                    </c:forEach>
                </select>

                <label for="startDate">Từ ngày:</label>
                <input type="date" name="startDate" id="startDate" value="${leave.startDate}" required>

                <label for="endDate">Đến ngày:</label>
                <input type="date" name="endDate" id="endDate" value="${leave.endDate}" required>

                <label for="reason">Lý do:</label>
                <textarea name="reason" id="reason" required>${leave.reason}</textarea>

                <div class="actions">
                    <button type="submit">💾 Lưu thay đổi</button>
                    <a href="${pageContext.request.contextPath}/request/list" class="cancel-link">↩️ Quay lại danh sách</a>
                </div>

                <c:if test="${not empty errorMessage}">
                    <p class="error">${errorMessage}</p>
                </c:if>
            </form>
        </div>
    </div>
</body>
</html>
