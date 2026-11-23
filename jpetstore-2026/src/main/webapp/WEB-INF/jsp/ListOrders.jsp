<%@ include file="IncludeTop.jsp" %>

<div class="page-head">
  <h2>My Orders</h2>
</div>

<div class="card medium">
<div class="table-wrap">
<table class="table">
  <tr><th>Order ID</th><th>Date</th><th class="num">Total Price</th></tr>
<c:forEach var="order" items="${orderList}">
  <tr>
    <td class="id"><a href="<c:url value="/shop/viewOrder.do"><c:param name="orderId" value="${order.orderId}"/></c:url>"><c:out value="${order.orderId}"/></a></td>
    <td><fmt:formatDate value="${order.orderDate}" pattern="yyyy/MM/dd hh:mm:ss"/></td>
    <td class="num"><fmt:formatNumber value="${order.totalPrice}" pattern="$#,##0.00"/></td>
  </tr>
</c:forEach>
</table>
</div>
</div>

<%@ include file="IncludeBottom.jsp" %>
