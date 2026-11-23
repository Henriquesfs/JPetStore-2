<%@ include file="IncludeTop.jsp" %>

<div class="page-head">
  <h2>Checkout Summary</h2>
  <a class="back" href="<c:url value="/shop/viewCart.do"/>">Shopping Cart</a>
</div>

<div class="card">
  <div class="table-wrap">
  <table class="table">
    <tr><th>Item ID</th><th>Product ID</th><th>Description</th><th class="center">In Stock?</th><th class="center">Quantity</th><th class="num">List Price</th><th class="num">Total Cost</th></tr>
<c:forEach var="cartItem" items="${cart.cartItemList.pageList}">
    <tr>
      <td class="id"><a href="<c:url value="/shop/viewItem.do"><c:param name="itemId" value="${cartItem.item.itemId}"/></c:url>"><c:out value="${cartItem.item.itemId}"/></a></td>
      <td><c:out value="${cartItem.item.productId}"/></td>
      <td>
        <c:out value="${cartItem.item.attribute1}"/>
        <c:out value="${cartItem.item.attribute2}"/>
        <c:out value="${cartItem.item.attribute3}"/>
        <c:out value="${cartItem.item.attribute4}"/>
        <c:out value="${cartItem.item.attribute5}"/>
        <c:out value="${cartItem.item.product.name}"/>
      </td>
      <td class="center"><c:out value="${cartItem.inStock}"/></td>
      <td class="center"><c:out value="${cartItem.quantity}"/></td>
      <td class="num"><fmt:formatNumber value="${cartItem.item.listPrice}" pattern="$#,##0.00"/></td>
      <td class="num"><fmt:formatNumber value="${cartItem.totalPrice}" pattern="$#,##0.00"/></td>
    </tr>
</c:forEach>
  </table>
  </div>
  <div class="totals">
    <span>Sub Total: <fmt:formatNumber value="${cart.subTotal}" pattern="$#,##0.00"/></span>
  </div>
  <div class="pager">
  <c:if test="${!cart.cartItemList.firstPage}">
    <a href="checkout.do?page=previousCart">&laquo; Prev</a>
  </c:if>
  <c:if test="${!cart.cartItemList.lastPage}">
    <a href="checkout.do?page=nextCart">Next &raquo;</a>
  </c:if>
  </div>
  <div class="actions"><a class="btn" href="<c:url value="/shop/newOrder.do"/>">Continue</a></div>
</div>

<%@ include file="IncludeBottom.jsp" %>
