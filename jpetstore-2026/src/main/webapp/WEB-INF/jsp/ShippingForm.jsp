<%@ include file="IncludeTop.jsp" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="card medium">

<spring:bind path="orderForm.*">
  <c:if test="${!empty status.errorMessages}">
  <div class="alert">
  <c:forEach var="error" items="${status.errorMessages}">
    <div><c:out value="${error}"/></div>
  </c:forEach>
  </div>
  </c:if>
</spring:bind>

<form action="<c:url value="/shop/newOrder.do"/>" method="post">
<h3 class="section-title">Shipping Address</h3>
<div class="form-grid">
  <label class="field">First name:
  <spring:bind path="orderForm.order.shipToFirstName">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field">Last name:
  <spring:bind path="orderForm.order.shipToLastName">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field wide">Address 1:
  <spring:bind path="orderForm.order.shipAddress1">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field wide">Address 2:
  <spring:bind path="orderForm.order.shipAddress2">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field">City:
  <spring:bind path="orderForm.order.shipCity">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field">State:
  <spring:bind path="orderForm.order.shipState">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field">Zip:
  <spring:bind path="orderForm.order.shipZip">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field">Country:
  <spring:bind path="orderForm.order.shipCountry">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
</div>

<div class="actions">
  <button class="btn" type="submit">Continue</button>
</div>

</form>
</div>

<%@ include file="IncludeBottom.jsp" %>
