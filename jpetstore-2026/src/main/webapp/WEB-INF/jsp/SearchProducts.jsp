<%@ include file="IncludeTop.jsp" %>

<div class="page-head">
  <h2>Search Results</h2>
  <a class="back" href="<c:url value="/shop/index.do"/>">Main Menu</a>
</div>

<div class="card">
  <div class="table-wrap">
  <table class="table">
    <tr><th></th><th>Product ID</th><th>Name</th></tr>
  <c:forEach var="product" items="${productList.pageList}">
    <tr>
      <td><a href="<c:url value="/shop/viewProduct.do"><c:param name="productId" value="${product.productId}"/></c:url>"><c:out value="${product.description}" escapeXml="false"/></a></td>
      <td class="id"><a href="<c:url value="/shop/viewProduct.do"><c:param name="productId" value="${product.productId}"/></c:url>"><c:out value="${product.productId}"/></a></td>
      <td><c:out value="${product.name}"/></td>
    </tr>
  </c:forEach>
  </table>
  </div>
  <div class="pager">
  <c:if test="${!productList.firstPage}">
    <a href="?page=previous">&laquo; Prev</a>
  </c:if>
  <c:if test="${!productList.lastPage}">
    <a href="?page=next">Next &raquo;</a>
  </c:if>
  </div>
</div>

<%@ include file="IncludeBottom.jsp" %>
