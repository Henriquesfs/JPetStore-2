<%@ include file="IncludeTop.jsp" %>

<div class="page-head">
  <h2><c:out value="${product.name}"/></h2>
  <a class="back" href="<c:url value="/shop/viewCategory.do"><c:param name="categoryId" value="${product.categoryId}"/></c:url>"><c:out value="${product.name}"/></a>
</div>

<div class="card">
  <div class="table-wrap">
  <table class="table">
    <tr><th>Item ID</th><th>Product ID</th><th>Description</th><th class="num">List Price</th><th></th></tr>
  <c:forEach var="item" items="${itemList.pageList}">
    <tr>
      <td class="id"><a href="<c:url value="/shop/viewItem.do"><c:param name="itemId" value="${item.itemId}"/></c:url>"><c:out value="${item.itemId}"/></a></td>
      <td><c:out value="${item.productId}"/></td>
      <td>
        <c:out value="${item.attribute1}"/>
        <c:out value="${item.attribute2}"/>
        <c:out value="${item.attribute3}"/>
        <c:out value="${item.attribute4}"/>
        <c:out value="${item.attribute5}"/>
        <c:out value="${product.name}"/>
      </td>
      <td class="num"><fmt:formatNumber value="${item.listPrice}" pattern="$#,##0.00"/></td>
      <td class="num"><a class="btn btn-small" href="<c:url value="/shop/addItemToCart.do"><c:param name="workingItemId" value="${item.itemId}"/></c:url>">Add to Cart</a></td>
    </tr>
  </c:forEach>
  </table>
  </div>
  <div class="pager">
  <c:if test="${!itemList.firstPage}">
    <a href="?page=previous">&laquo; Prev</a>
  </c:if>
  <c:if test="${!itemList.lastPage}">
    <a href="?page=next">Next &raquo;</a>
  </c:if>
  </div>
</div>

<%@ include file="IncludeBottom.jsp" %>
