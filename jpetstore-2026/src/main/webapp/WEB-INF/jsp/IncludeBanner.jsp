<c:if test="${userSession.account.bannerOption}">
<div class="banner card">
  <c:out value="${userSession.account.bannerName}" escapeXml="false"/>
</div>
</c:if>
