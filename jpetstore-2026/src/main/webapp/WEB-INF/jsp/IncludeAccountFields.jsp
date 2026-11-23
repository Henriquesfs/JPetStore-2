<h3 class="section-title">Account Information</h3>
<div class="form-grid">
  <label class="field">First name:
  <spring:bind path="accountForm.account.firstName">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field">Last name:
  <spring:bind path="accountForm.account.lastName">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field">Email:
  <spring:bind path="accountForm.account.email">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field">Phone:
  <spring:bind path="accountForm.account.phone">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field wide">Address 1:
  <spring:bind path="accountForm.account.address1">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field wide">Address 2:
  <spring:bind path="accountForm.account.address2">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field">City:
  <spring:bind path="accountForm.account.city">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field">State:
  <spring:bind path="accountForm.account.state">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field">Zip:
  <spring:bind path="accountForm.account.zip">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
  <label class="field">Country:
  <spring:bind path="accountForm.account.country">
    <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
  </spring:bind>
  </label>
</div>

<h3 class="section-title">Profile Information</h3>
<div class="form-grid">
  <label class="field">Language Preference:
  <spring:bind path="accountForm.account.languagePreference">
    <select name="<c:out value="${status.expression}"/>">
      <c:forEach var="language" items="${languages}">
        <option <c:if test="${language == status.value}">selected</c:if> value="<c:out value="${language}"/>"><c:out value="${language}"/></option>
      </c:forEach>
    </select>
  </spring:bind>
  </label>
  <label class="field">Favourite Category:
  <spring:bind path="accountForm.account.favouriteCategoryId">
    <select name="<c:out value="${status.expression}"/>">
      <c:forEach var="category" items="${categories}">
        <option <c:if test="${category.categoryId == status.value}">selected</c:if> value="<c:out value="${category}"/>"><c:out value="${category.name}"/></option>
      </c:forEach>
    </select>
  </spring:bind>
  </label>
  <spring:bind path="accountForm.account.listOption">
  <label class="check"><input type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked</c:if>> Enable MyList</label>
  </spring:bind>
  <spring:bind path="accountForm.account.bannerOption">
  <label class="check"><input type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked</c:if>> Enable MyBanner</label>
  </spring:bind>
</div>
