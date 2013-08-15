<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
<c:when test="${not empty  errors}">
    <div class="error">
    <h3>${tituloMensagem}</h3>
    <c:forEach items="${errors}" var="err">
        ${err.defaultMessage}
        <br/>
    </c:forEach>
    </div>
</c:when>
<c:when test="${not empty  mensagem}">
    <div class="error">
    <h3>${tituloMensagem}</h3>
        ${mensagem}
        <br/>
    </div>
</c:when>
</c:choose>

<c:choose>
<c:when test="${not empty  sucesso}">
    <div class="sucesso">
    <h3>${tituloMensagem}</h3>
        ${sucesso}
    </div>
</c:when>
</c:choose>