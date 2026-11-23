<%@ include file="IncludeTop.jsp" %>

<div class="page-head">
  <span></span>
  <a class="back" href="<c:url value="/shop/index.do"/>">Main Menu</a>
</div>

<c:if test="${!empty message}">
  <div class="notice"><c:out value="${message}"/></div>
</c:if>

<div class="card">
<div class="order-head">
  <h2>Order #<c:out value="${order.orderId}"/></h2>
  <p><fmt:formatDate value="${order.orderDate}" pattern="yyyy/MM/dd hh:mm:ss"/></p>
  <span class="status">Status: <c:out value="${order.status}"/></span>
</div>
<div class="summary">
  <div>
    <h3>Payment Details</h3>
    <dl>
      <dt>Card Type:</dt><dd><c:out value="${order.cardType}"/></dd>
      <dt>Card Number:</dt><dd><c:out value="${order.creditCard}"/> <span class="hint">* Fake number!</span></dd>
      <dt>Expiry Date (MM/YYYY):</dt><dd><c:out value="${order.expiryDate}"/></dd>
      <dt>Courier:</dt><dd><c:out value="${order.courier}"/></dd>
    </dl>
  </div>
  <div>
    <h3>Billing Address</h3>
    <dl>
      <dt>First name:</dt><dd><c:out value="${order.billToFirstName}"/></dd>
      <dt>Last name:</dt><dd><c:out value="${order.billToLastName}"/></dd>
      <dt>Address 1:</dt><dd><c:out value="${order.billAddress1}"/></dd>
      <dt>Address 2:</dt><dd><c:out value="${order.billAddress2}"/></dd>
      <dt>City:</dt><dd><c:out value="${order.billCity}"/></dd>
      <dt>State:</dt><dd><c:out value="${order.billState}"/></dd>
      <dt>Zip:</dt><dd><c:out value="${order.billZip}"/></dd>
      <dt>Country:</dt><dd><c:out value="${order.billCountry}"/></dd>
    </dl>
  </div>
  <div>
    <h3>Shipping Address</h3>
    <dl>
      <dt>First name:</dt><dd><c:out value="${order.shipToFirstName}"/></dd>
      <dt>Last name:</dt><dd><c:out value="${order.shipToLastName}"/></dd>
      <dt>Address 1:</dt><dd><c:out value="${order.shipAddress1}"/></dd>
      <dt>Address 2:</dt><dd><c:out value="${order.shipAddress2}"/></dd>
      <dt>City:</dt><dd><c:out value="${order.shipCity}"/></dd>
      <dt>State:</dt><dd><c:out value="${order.shipState}"/></dd>
      <dt>Zip:</dt><dd><c:out value="${order.shipZip}"/></dd>
      <dt>Country:</dt><dd><c:out value="${order.shipCountry}"/></dd>
    </dl>
  </div>
</div>

<h3 class="section-title">Items</h3>
<div class="table-wrap">
<table class="table">
  <tr><th>Item ID</th><th>Description</th><th class="center">Quantity</th><th class="num">Price</th><th class="num">Total Cost</th></tr>
<c:forEach var="lineItem" items="${order.lineItems}">
  <tr>
    <td class="id"><a href="<c:url value="/shop/viewItem.do"><c:param name="itemId" value="${lineItem.itemId}"/></c:url>"><c:out value="${lineItem.itemId}"/></a></td>
    <td>
      <c:out value="${lineItem.item.attribute1}"/>
      <c:out value="${lineItem.item.attribute2}"/>
      <c:out value="${lineItem.item.attribute3}"/>
      <c:out value="${lineItem.item.attribute4}"/>
      <c:out value="${lineItem.item.attribute5}"/>
      <c:out value="${lineItem.item.product.name}"/>
    </td>
    <td class="center"><c:out value="${lineItem.quantity}"/></td>
    <td class="num"><fmt:formatNumber value="${lineItem.unitPrice}" pattern="$#,##0.00"/></td>
    <td class="num"><fmt:formatNumber value="${lineItem.totalPrice}" pattern="$#,##0.00"/></td>
  </tr>
</c:forEach>
</table>
</div>
<div class="totals"><span>Total: <fmt:formatNumber value="${order.totalPrice}" pattern="$#,##0.00"/></span></div>
</div>

<%@ include file="IncludeBottom.jsp" %>
