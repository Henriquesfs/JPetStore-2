<c:if test="${!empty userSession.myList}">
<aside class="card mylist">
  <h3>Pet Favorites</h3>
  <p>Shop for more of your favorite pets here.</p>
  <ul>
  <c:forEach var="product" items="${userSession.myList.pageList}">
    <li>
      <a href="<c:url value="/shop/viewProduct.do"><c:param name="productId" value="${product.productId}"/></c:url>"><c:out value="${product.name}"/></a>
      <small>(<c:out value="${product.productId}"/>)</small>
    </li>
  </c:forEach>
  </ul>
  <div class="pager">
  <c:if test="${!userSession.myList.firstPage}">
    <a href="viewCart.do?page=previous">&laquo; Prev</a>
  </c:if>
  <c:if test="${!userSession.myList.lastPage}">
    <a href="viewCart.do?page=next">Next &raquo;</a>
  </c:if>
  </div>
</aside>
</c:if>
