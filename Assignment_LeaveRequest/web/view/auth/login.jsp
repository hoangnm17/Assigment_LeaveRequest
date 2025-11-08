<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Đăng nhập</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            /* Bảng màu đã được thay đổi sang DARK MODE
            */
            --primary-color: #4A90E2;       /* Màu xanh dương (Giữ nguyên làm điểm nhấn) */
            --secondary-color: #ADB5BD;     /* Màu xám nhạt (cho text phụ) */
            --background-color: #2c2323;    /* Nền chính (Đen/Xám rất tối) */
            --card-background: #3f3838;    /* Nền của form (Tối, nhưng nổi bật hơn nền chính) */
            --text-color: #E0E0E0;          /* Màu chữ chính (Trắng ngà) */
            --error-color: #E57373;         /* Màu đỏ nhạt cho lỗi */
            --border-color: #333333;       /* Viền input (Xám tối) */
            --input-bg-color: #252525;      /* Nền của input */
            --focus-border-color: #80BDFF;  /* Viền focus (Giữ nguyên) */
            --box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
        }

        body {
            font-family: 'Poppins', sans-serif;
            background-color: var(--background-color);
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            color: var(--text-color); /* <-- Thay đổi màu chữ mặc định */
            -webkit-font-smoothing: antialiased;
            -moz-osx-font-smoothing: grayscale;
        }

        .login-wrapper {
            background-color: var(--card-background);
            padding: 40px;
            border-radius: 12px;
            box-shadow: var(--box-shadow);
            width: 100%;
            max-width: 400px;
            text-align: center;
            border: 1px solid var(--border-color); /* Thêm viền nhẹ để nổi bật */
        }

        .login-wrapper h2 {
            font-size: 2rem;
            margin-bottom: 30px;
            color: var(--primary-color);
            font-weight: 600;
        }

        .field {
            margin-bottom: 20px;
            text-align: left;
        }

        .field label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: var(--text-color); /* <-- Thay đổi */
        }

        .field input[type="text"],
        .field input[type="password"] {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid var(--border-color);
            border-radius: 8px;
            box-sizing: border-box;
            font-size: 1rem;
            transition: all 0.2s ease-in-out;
            
            /* --- CẬP NHẬT QUAN TRỌNG CHO DARK MODE --- */
            background-color: var(--input-bg-color);
            color: var(--text-color);
            /* ------------------------------------------ */
        }

        .field input[type="text"]:focus,
        .field input[type="password"]:focus {
            outline: none;
            border-color: var(--primary-color);
            box-shadow: 0 0 0 3px rgba(74, 144, 226, 0.25);
        }

        .pw-wrap {
            position: relative;
            display: flex;
            align-items: center;
        }

        .pw-wrap input {
            flex-grow: 1;
            padding-right: 50px;
        }

        #togglePw {
            position: absolute;
            right: 10px;
            background: none;
            border: none;
            color: var(--secondary-color); /* <-- Thay đổi */
            cursor: pointer;
            font-size: 0.9rem;
            font-weight: 500;
            padding: 0;
            outline: none;
            transition: color 0.2s ease-in-out;
        }

        #togglePw:hover {
            color: var(--primary-color);
        }

        small.error {
            color: var(--error-color); /* <-- Thay đổi */
            font-size: 0.85rem;
            display: block;
            margin-top: 5px;
        }

        .actions {
            margin-top: 30px;
        }

        .btn-primary {
            width: 100%;
            padding: 14px;
            border: none;
            border-radius: 8px;
            background-color: var(--primary-color);
            color: white;
            font-size: 1.1rem;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.2s ease-in-out, box-shadow 0.2s ease-in-out;
        }

        .btn-primary:hover {
            background-color: #3A7FD5;
            box-shadow: 0 4px 10px rgba(74, 144, 226, 0.3);
        }

        .error-message {
            color: var(--error-color); /* <-- Thay đổi */
            font-size: 0.95rem;
            margin-top: 20px;
            font-weight: 500;
        }
    </style>
</head>
<body>

    <div class="login-wrapper">
        <h2>Đăng nhập Hệ thống</h2>
        
        <form id="loginForm" action="${pageContext.request.contextPath}/auth/login" method="POST" novalidate>

            <div class="field">
                <label for="username">Tên đăng nhập</label>
                <input id="username" name="username" type="text" required maxlength="60" autocomplete="username" value="${username}"/>
                <small class="error" id="err-username"></small>
            </div>

            <div class="field">
                <label for="password">Mật khẩu</label>
                <div class="pw-wrap">
                    <input id="password" name="password" type="password" required minlength="6" autocomplete="current-password" />
                    <button type="button" id="togglePw" aria-label="Hiện mật khẩu">Hiện</button>
                </div>
                <small class="error" id="err-password"></small>
            </div>

            <div class="actions">
                <button type="submit" class="btn-primary">Đăng nhập</button>
            </div>

            <c:if test="${not empty error}">
                <p class="error-message">${error}</p>
            </c:if>

        </form>
    </div>

    <script>
        const togglePw = document.getElementById("togglePw");
        const passwordInput = document.getElementById("password");

        togglePw.addEventListener("click", () => {
            const isHidden = passwordInput.type === "password";
            passwordInput.type = isHidden ? "text" : "password";
            togglePw.textContent = isHidden ? "Ẩn" : "Hiện";
        });
    </script>
</body>
</html>