<%@ include file="IncludeTop.jsp" %>

<div class="page-head">
  <span></span>
  <a class="back" href="<c:url value="/shop/viewProduct.do"><c:param name="productId" value="${product.productId}"/></c:url>"><c:out value="${product.name}"/></a>
</div>

<div class="card medium item">
  <div class="item-photo">
    <c:out value="${product.description}" escapeXml="false"/>
  </div>
  <div>
    <div class="item-id"><c:out value="${item.itemId}"/></div>
    <h2>
      <c:out value="${item.attribute1}"/>
      <c:out value="${item.attribute2}"/>
      <c:out value="${item.attribute3}"/>
      <c:out value="${item.attribute4}"/>
      <c:out value="${item.attribute5}"/>
      <c:out value="${product.name}"/>
    </h2>
    <div class="item-product"><c:out value="${product.name}"/></div>
    <c:if test="${item.quantity <= 0}">
      <div class="stock out">Back ordered.</div>
    </c:if>
    <c:if test="${item.quantity > 0}">
      <div class="stock"><fmt:formatNumber value="${item.quantity}"/> in stock.</div>
    </c:if>
    <div class="price"><fmt:formatNumber value="${item.listPrice}" pattern="$#,##0.00"/></div>
    <a class="btn" href="<c:url value="/shop/addItemToCart.do"><c:param name="workingItemId" value="${item.itemId}"/></c:url>">Add to Cart</a>
  </div>
</div>

<%@ include file="IncludeBottom.jsp" %>
