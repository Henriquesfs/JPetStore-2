<%@ include file="IncludeTop.jsp" %>

<div class="page-head">
  <h2>Shopping Cart</h2>
  <a class="back" href="<c:url value="/shop/index.do"/>">Main Menu</a>
</div>

<div class="with-aside">
<div class="card">
<form action="<c:url value="/shop/updateCartQuantities.do"/>" method="post">
  <div class="table-wrap">
  <table class="table">
    <tr><th>Item ID</th><th>Product ID</th><th>Description</th><th class="center">In Stock?</th><th class="center">Quantity</th><th class="num">List Price</th><th class="num">Total Cost</th><th></th></tr>
<c:if test="${cart.numberOfItems == 0}">
    <tr><td colspan="8"><b>Your cart is empty.</b></td></tr>
</c:if>
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
      <td class="center"><input type="text" size="3" name="<c:out value="${cartItem.item.itemId}"/>" value="<c:out value="${cartItem.quantity}"/>" aria-label="Quantity"/></td>
      <td class="num"><fmt:formatNumber value="${cartItem.item.listPrice}" pattern="$#,##0.00"/></td>
      <td class="num"><fmt:formatNumber value="${cartItem.totalPrice}" pattern="$#,##0.00"/></td>
      <td class="num"><a class="btn btn-light btn-small" href="<c:url value="/shop/removeItemFromCart.do"><c:param name="workingItemId" value="${cartItem.item.itemId}"/></c:url>">Remove</a></td>
    </tr>
</c:forEach>
  </table>
  </div>
  <div class="totals">
    <span>Sub Total: <fmt:formatNumber value="${cart.subTotal}" pattern="$#,##0.00"/></span>
    <button class="btn btn-light" type="submit" name="update">Update Cart</button>
  </div>
  <div class="pager">
  <c:if test="${!cart.cartItemList.firstPage}">
    <a href="<c:url value="viewCart.do?page=previousCart"/>">&laquo; Prev</a>
  </c:if>
  <c:if test="${!cart.cartItemList.lastPage}">
    <a href="<c:url value="viewCart.do?page=nextCart"/>">Next &raquo;</a>
  </c:if>
  </div>
</form>
<c:if test="${cart.numberOfItems > 0}">
  <div class="actions"><a class="btn" href="<c:url value="/shop/checkout.do"/>">Proceed to Checkout</a></div>
</c:if>
</div>

<div>
<c:if test="${!empty userSession.account.username}">
  <c:if test="${userSession.account.listOption}">
    <%@ include file="IncludeMyList.jsp" %>
  </c:if>
</c:if>
</div>
</div>

<%@ include file="IncludeBanner.jsp" %>

<%@ include file="IncludeBottom.jsp" %>
