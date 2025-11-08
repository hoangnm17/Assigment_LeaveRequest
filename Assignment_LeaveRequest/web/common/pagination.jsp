<%-- 
    Document   : pagination
    Created on : Nov 5, 2025, 2:46:57 AM
    Author     : 84911
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/pagination.css">

<c:if test="${totalPages > 1}">

    <%-- Bước 1: Xây dựng URL cơ sở (giữ lại filter) --%>
    <c:set var="baseURL">
        <%-- 
          DÒNG SỬA LỖI:
          Sử dụng biến "paginationURL" (ví dụ: /request/list) do Controller cung cấp.
          Thẻ <c:url> sẽ tự động thêm Context Path (ví dụ: /Assignment_LeaveRequest) vào đầu.
        --%>
        <c:url value="${paginationURL}">
            <c:forEach var="param" items="${paramValues}">
                <c:if test="${param.key != 'page'}">
                    <c:forEach var="value" items="${param.value}">
                        <c:param name="${param.key}" value="${value}" />
                    </c:forEach>
                </c:if>
            </c:forEach>
        </c:url>
    </c:set>
    <c:set var="separator" value="${fn:contains(baseURL, '?') ? '&' : '?'}" />

    <%-- Bước 2: Logic tính toán cửa sổ trang --%>
    <c:set var="window" value="2" />
    <c:set var="startPage" value="${currentPage - window}" />
    <c:set var="endPage" value="${currentPage + window}" />
    <c:if test="${startPage < 1}">
        <c:set var="endPage" value="${endPage + (1 - startPage)}" />
        <c:set var="startPage" value="1" />
    </c:if>
    <c:if test="${endPage > totalPages}">
        <c:set var="startPage" value="${startPage - (endPage - totalPages)}" />
        <c:set var="endPage" value="${totalPages}" />
    </c:if>
    <c:if test="${startPage < 1}">
        <c:set var="startPage" value="1" />
    </c:if>

    <%-- Bước 3: Render HTML (sử dụng class CSS thuần) --%>
    <nav class="pagination-container" aria-label="Page navigation">
        <ul class="pagination">

            <%-- Nút Previous (Về trước) --%>
            <li class="${currentPage == 1 ? 'disabled' : ''}">
                <a href="${currentPage == 1 ? '#' : (baseURL.concat(separator).concat('page=').concat(currentPage - 1))}" 
                   aria-label="Previous">
                    &laquo;
                </a>
            </li>

            <%-- Hiển thị "1" và "..." nếu cần --%>
            <c:if test="${startPage > 1}">
                <li>
                    <a href="${baseURL.concat(separator).concat('page=1')}">1</a>
                </li>
                <c:if test="${startPage > 2}">
                    <li class="disabled"><a href="#">...</a></li>
                    </c:if>
                </c:if>

            <%-- Vòng lặp các số trang chính --%>
            <c:forEach begin="${startPage}" end="${endPage}" var="i">
                <li class="${i == currentPage ? 'active' : ''}">
                    <a href="${baseURL.concat(separator).concat('page=').concat(i)}">${i}</a>
                </li>
            </c:forEach>

            <%-- Hiển thị "..." và trang cuối nếu cần --%>
            <c:if test="${endPage < totalPages}">
                <c:if test="${endPage < totalPages - 1}">
                    <li class="disabled"><a href="#">...</a></li>
                    </c:if>
                <li>
                    <a href="${baseURL.concat(separator).concat('page=').concat(totalPages)}">${totalPages}</a>
                </li>
            </c:if>

            <%-- Nút Next (Tới sau) --%>
            <li class="${currentPage == totalPages ? 'disabled' : ''}">
                <a href="${currentPage == totalPages ? '#' : (baseURL.concat(separator).concat('page=').concat(currentPage + 1))}" 
                   aria-label="Next">
                    &raquo;
                </a>
            </li>

        </ul>
    </nav>
</c:if>
