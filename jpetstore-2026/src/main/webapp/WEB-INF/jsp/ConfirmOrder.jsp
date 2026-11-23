<%@ include file="IncludeTop.jsp" %>

<div class="page-head">
  <span></span>
  <a class="back" href="<c:url value="/shop/index.do"/>">Main Menu</a>
</div>

<div class="notice">Please confirm the information below and then press continue...</div>

<div class="card medium">
<div class="order-head">
  <h2>Order</h2>
  <p><fmt:formatDate value="${orderForm.order.orderDate}" pattern="yyyy/MM/dd hh:mm:ss"/></p>
</div>
<div class="summary">
  <div>
    <h3>Billing Address</h3>
    <dl>
      <dt>First name:</dt><dd><c:out value="${orderForm.order.billToFirstName}"/></dd>
      <dt>Last name:</dt><dd><c:out value="${orderForm.order.billToLastName}"/></dd>
      <dt>Address 1:</dt><dd><c:out value="${orderForm.order.billAddress1}"/></dd>
      <dt>Address 2:</dt><dd><c:out value="${orderForm.order.billAddress2}"/></dd>
      <dt>City:</dt><dd><c:out value="${orderForm.order.billCity}"/></dd>
      <dt>State:</dt><dd><c:out value="${orderForm.order.billState}"/></dd>
      <dt>Zip:</dt><dd><c:out value="${orderForm.order.billZip}"/></dd>
      <dt>Country:</dt><dd><c:out value="${orderForm.order.billCountry}"/></dd>
    </dl>
  </div>
  <div>
    <h3>Shipping Address</h3>
    <dl>
      <dt>First name:</dt><dd><c:out value="${orderForm.order.shipToFirstName}"/></dd>
      <dt>Last name:</dt><dd><c:out value="${orderForm.order.shipToLastName}"/></dd>
      <dt>Address 1:</dt><dd><c:out value="${orderForm.order.shipAddress1}"/></dd>
      <dt>Address 2:</dt><dd><c:out value="${orderForm.order.shipAddress2}"/></dd>
      <dt>City:</dt><dd><c:out value="${orderForm.order.shipCity}"/></dd>
      <dt>State:</dt><dd><c:out value="${orderForm.order.shipState}"/></dd>
      <dt>Zip:</dt><dd><c:out value="${orderForm.order.shipZip}"/></dd>
      <dt>Country:</dt><dd><c:out value="${orderForm.order.shipCountry}"/></dd>
    </dl>
  </div>
</div>
<div class="actions"><a class="btn" href="<c:url value="/shop/newOrder.do?_finish=true"/>">Continue</a></div>
</div>

<%@ include file="IncludeBottom.jsp" %>
