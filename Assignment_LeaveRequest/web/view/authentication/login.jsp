<%-- 
    Document   : login
    Created on : Oct 12, 2025, 10:03:32 PM
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
        <form id="loginForm" action="" method="POST" novalidate>

            <div class="field">
                <label for="username">Tên đăng nhập</label>
                <input id="username" name="username" type="text" required maxlength="60" autocomplete="username" />
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

            <c:if test="${not empty message}">
                <p style="color: red;" class="error-message">${message}</p>
            </c:if>

        </form>

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
