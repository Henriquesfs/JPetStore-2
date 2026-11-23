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
<h3 class="section-title">Payment Details</h3>
<div class="form-grid">
  <label class="field">Card Type:
  <spring:bind path="orderForm.order.cardType">
    <select name="<c:out value="${status.expression}"/>">
      <c:forEach var="cardType" items="${creditCardTypes}">
        <option <c:if test="${cardType == status.value}">selected</c:if> value="<c:out value="${cardType}"/>"><c:out value="${cardType}"/></option>
      </c:forEach>
    </select>
  </spring:bind>
  </label>
  <label class="field">Card Number:
  <spring:bind path="orderForm.order.creditCard">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
    <span class="hint">* Use a fake number!</span>
  </label>
  <label class="field">Expiry Date (MM/YYYY):
  <spring:bind path="orderForm.order.expiryDate">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
</div>

<h3 class="section-title">Billing Address</h3>
<div class="form-grid">
  <label class="field">First name:
  <spring:bind path="orderForm.order.billToFirstName">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field">Last name:
  <spring:bind path="orderForm.order.billToLastName">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field wide">Address 1:
  <spring:bind path="orderForm.order.billAddress1">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field wide">Address 2:
  <spring:bind path="orderForm.order.billAddress2">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field">City:
  <spring:bind path="orderForm.order.billCity">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field">State:
  <spring:bind path="orderForm.order.billState">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field">Zip:
  <spring:bind path="orderForm.order.billZip">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field">Country:
  <spring:bind path="orderForm.order.billCountry">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
</div>

<div class="form-grid">
  <spring:bind path="orderForm.shippingAddressRequired">
  <label class="check"><input type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked</c:if>> Ship to different address...</label>
  </spring:bind>
</div>

<div class="actions">
  <button class="btn" type="submit">Continue</button>
</div>

</form>
</div>

<%@ include file="IncludeBottom.jsp" %>
