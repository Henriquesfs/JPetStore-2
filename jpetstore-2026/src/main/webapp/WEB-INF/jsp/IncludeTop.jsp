<%@ page contentType="text/html" %>
<%@ taglib prefix="c"      uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt"    uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>JPetStore Demo</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Cache-Control" content="max-age=0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Tue, 01 Jan 1980 1:00:00 GMT">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="<c:url value="/css/jpetstore.css"/>">
</head>
<body>

<header class="topbar">
  <div class="container topbar-inner">
    <a class="brand" href="<c:url value="/shop/index.do"/>">
      <svg viewBox="0 0 64 64" aria-hidden="true"><ellipse cx="14" cy="27" rx="6" ry="8"/><ellipse cx="26" cy="14" rx="6" ry="8"/><ellipse cx="38" cy="14" rx="6" ry="8"/><ellipse cx="50" cy="27" rx="6" ry="8"/><path d="M32 30c-10 0-18 12-18 19 0 6 5 9 10 8 3-1 5-2 8-2s5 1 8 2c5 1 10-2 10-8 0-7-8-19-18-19z"/></svg>
      JPetStore
    </a>
    <form class="search" action="<c:url value="/shop/searchProducts.do"/>" method="post">
      <input type="hidden" name="search" value="true"/>
      <input name="keyword" placeholder="Search products" aria-label="Search products"/>
      <button class="btn" type="submit">Search</button>
    </form>
    <nav class="account-nav">
      <a href="<c:url value="/shop/viewCart.do"/>">Cart</a>
<c:if test="${empty userSession.account}">
      <a href="<c:url value="/shop/signonForm.do"/>">Sign In</a>
</c:if>
<c:if test="${!empty userSession.account}">
      <a href="<c:url value="/shop/editAccount.do"/>">My Account</a>
      <a href="<c:url value="/shop/signoff.do"/>">Sign Out</a>
</c:if>
      <a href="../help.html">Help</a>
    </nav>
  </div>
<%@ include file="IncludeQuickHeader.jsp" %>
</header>

<main class="container">
