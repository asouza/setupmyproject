<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags/template" prefix="template" %>
<template:admin>
  <div>
    <div class ="container min-container">      
    <h2 class="basic-title">Add</h2>
      <form:form role="form" cssClass="well" commandName="${currentCrudModel.getClassNameAsProperty()}" servletRelativeAction="${form.getAction()}" method="${form.getMethod()}">
      	<%@include file="form-inputs.jsp" %>
        <button type="submit" class="btn btn-primary">Submit</button>

      </form:form>	
    </div>
  </div>
</template:admin>
