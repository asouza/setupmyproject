<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags/template" prefix="template" %>
<template:admin>
  <div>
    <div class ="container min-container">      
    <h2 class="basic-title">Add</h2>
      <form role="form" class="well" action="<c:url value='${form.getAction()}'/>" method="${form.getMethod()}">
      	<%@include file="form-inputs.jsp" %>
        <button type="submit" class="btn btn-primary">Submit</button>
      </form>	
    </div>
  </div>
</template:admin>
