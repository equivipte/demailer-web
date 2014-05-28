<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:if test="${not empty errors}">
    <div class="alert alert-danger">
        <button type="button" class="close" data-dismiss="alert">
            <i class="icon-remove"></i>
        </button>

        <c:forEach items="${errors}" var="error">
            <strong>
                <i class="icon-remove"></i>
                    ${error}
            </strong>
            <br/>
        </c:forEach>
    </div>
</c:if>

