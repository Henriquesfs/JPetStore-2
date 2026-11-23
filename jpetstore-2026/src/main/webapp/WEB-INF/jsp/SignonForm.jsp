<%@ include file="IncludeTop.jsp" %>

<div class="card narrow">
  <h2>Sign In</h2>

<c:if test="${!empty message}">
  <div class="alert"><c:out value="${message}"/></div>
</c:if>

<form action="<c:url value="/shop/signon.do"/>" method="POST">
<c:if test="${!empty signonForwardAction}">
  <input type="hidden" name="forwardAction" value="<c:url value="${signonForwardAction}"/>"/>
</c:if>
  <p>Please enter your username and password.</p>
  <div class="form-grid">
    <label class="field wide">Username:<input type="text" name="username" value="j2ee"/></label>
    <label class="field wide">Password:<input type="password" name="password" value="j2ee"/></label>
  </div>
  <div class="actions">
    <button class="btn" type="submit" name="update">Sign In</button>
    <a class="btn btn-light" href="<c:url value="/shop/newAccount.do"/>">Register Now!</a>
  </div>
</form>
</div>

<%@ include file="IncludeBottom.jsp" %>
