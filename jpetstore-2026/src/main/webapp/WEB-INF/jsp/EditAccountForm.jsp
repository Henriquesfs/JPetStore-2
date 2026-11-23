<%@ include file="IncludeTop.jsp" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="card medium">

<spring:bind path="accountForm.*">
  <c:if test="${!empty status.errorMessages}">
  <div class="alert">
  <c:forEach var="error" items="${status.errorMessages}">
    <div><c:out value="${error}"/></div>
  </c:forEach>
  </div>
  </c:if>
</spring:bind>

<c:if test="${accountForm.newAccount}">
<form action="<c:url value="/shop/newAccount.do"/>" method="post">
</c:if>
<c:if test="${!accountForm.newAccount}">
<form action="<c:url value="/shop/editAccount.do"/>" method="post">
</c:if>

<h3 class="section-title">User Information</h3>
<div class="form-grid">
  <label class="field wide">User ID:
<c:if test="${accountForm.newAccount}">
  <spring:bind path="accountForm.account.username">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
</c:if>
<c:if test="${!accountForm.newAccount}">
    <span class="value"><c:out value="${accountForm.account.username}"/></span>
</c:if>
  </label>
  <label class="field">New password:
  <spring:bind path="accountForm.account.password">
    <input type="password" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field">Repeat password:
  <spring:bind path="accountForm.repeatedPassword">
    <input type="password" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
</div>

<%@ include file="IncludeAccountFields.jsp" %>

<div class="actions">
  <button class="btn" type="submit" name="submit" value="Save Account Information">Save Account Information</button>
</div>

</form>
</div>

<div class="actions"><a href="<c:url value="/shop/listOrders.do"/>"><b>My Orders</b></a></div>

<%@ include file="IncludeBottom.jsp" %>
