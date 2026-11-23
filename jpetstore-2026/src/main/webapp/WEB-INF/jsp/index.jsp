<%@ include file="IncludeTop.jsp" %>

<section class="hero">
  <div>
    <c:if test="${!empty userSession.account}">
      <span class="welcome">Welcome <c:out value="${userSession.account.firstName}"/>!</span>
    </c:if>
    <h1>Find the right <em>pet</em> for your home</h1>
    <p>Fish, dogs, reptiles, cats and birds, ready to be added to your cart.</p>
    <a class="btn" href="<c:url value="/shop/viewCategory.do?categoryId=FISH"/>">Browse the catalog</a>
  </div>
  <img class="hero-image" src="../images/splash.gif" width="350" height="355" alt="JPetStore pets"/>
</section>

<div class="category-grid">
  <a class="pet-tile" href="<c:url value="/shop/viewCategory.do?categoryId=FISH"/>"><img src="../images/fish1.jpg" alt=""/>Fish</a>
  <a class="pet-tile" href="<c:url value="/shop/viewCategory.do?categoryId=DOGS"/>"><img src="../images/dog1.jpg" alt=""/>Dogs</a>
  <a class="pet-tile" href="<c:url value="/shop/viewCategory.do?categoryId=CATS"/>"><img src="../images/cat1.jpg" alt=""/>Cats</a>
  <a class="pet-tile" href="<c:url value="/shop/viewCategory.do?categoryId=REPTILES"/>"><img src="../images/lizard1.jpg" alt=""/>Reptiles</a>
  <a class="pet-tile" href="<c:url value="/shop/viewCategory.do?categoryId=BIRDS"/>"><img src="../images/bird1.jpg" alt=""/>Birds</a>
</div>

<%@ include file="IncludeBanner.jsp" %>

<%@ include file="IncludeBottom.jsp" %>
