<%-- 
    Document   : a
    Created on : Oct 2, 2025, 11:54:55 PM
    Author     : 84911
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HR Home</title>
    </head>
    <body>
      <form id="loginForm" action="/login" method="POST" novalidate>
          
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

      </form>
    </body>
</html>
