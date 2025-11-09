<%-- 
    FILE: /common/pagination.jsp
--%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/pagination.css">

<c:if test="${totalPages > 1}">
    
    
    <c:url var="baseURL" value="${paginationURL}" />
    
    
    <c:set var="query" value="${paginationQuery}" />
    
    
    <c:set var="separator" value="${empty query ? '?' : '&'}" />
    
    
    <c:set var="fullURLWithFilter" value="${baseURL}${empty query ? '' : '?'}${query}" />


    
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

    <nav class="pagination-container" aria-label="Page navigation">
        <ul class="pagination">

            
            <li class="${currentPage == 1 ? 'disabled' : ''}">
                <a href="${currentPage == 1 ? '#' : fullURLWithFilter}${separator}page=${currentPage - 1}">&laquo;</a>
            </li>

            
            <c:if test="${startPage > 1}">
                <li><a href="${fullURLWithFilter}${separator}page=1">1</a></li>
                <c:if test="${startPage > 2}"><li class="disabled"><a href="#">...</a></li></c:if>
            </c:if>

            
            <c:forEach begin="${startPage}" end="${endPage}" var="i">
                <li class="${i == currentPage ? 'active' : ''}">
                    <a href="${fullURLWithFilter}${separator}page=${i}">${i}</a>
                </li>
            </c:forEach>

            
            <c:if test="${endPage < totalPages}">
                <c:if test="${endPage < totalPages - 1}"><li class="disabled"><a href="#">...</a></li></c:if>
                <li><a href="${fullURLWithFilter}${separator}page=${totalPages}">${totalPages}</a></li>
            </c:if>

            
            <li class="${currentPage == totalPages ? 'disabled' : ''}">
                <a href="${currentPage == totalPages ? '#' : fullURLWithFilter}${separator}page=${currentPage + 1}">&raquo;</a>
            </li>

        </ul>
    </nav>
</c:if>