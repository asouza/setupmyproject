<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags/template" prefix="template" %>
<template:admin>
<jsp:attribute name="extraStyles">
<link rel="stylesheet" href="<c:url value='/assets/css/pagination/jqpagination.css'/>" />
</jsp:attribute>
<jsp:attribute name="extraScripts">
<script src="<c:url value='/assets/js/jquery.jqpagination.js'/>"></script>
</jsp:attribute>
<jsp:body>
  <div>
    <div class ="container min-container">
      <h2 class="basic-title">List ${currentCrudModel.getUserName()}</h2>
        <div class="well">
          <table class="table table-condensed table-bordered table-striped table-hover">
          		  <thead>
	                  <tr>
	                  	<td>id</td>
	                  	<#list table.getHeader().getTds() as tableData>
		                  	<td>${tableData.getValue()}</td>
	                  	</#list>
						<td>actions</td>
	                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach items='${r"${paginatedList.currentList}"}' var='object'>         		
	                  <tr>
						<td><a href="<c:url value='${currentCrudModel.getEditAction()}'/>/${r"${object.id}"}">${r"${object.id}"}</a></td>
	                  	<#list table.getRowExpression().getValueExpressions() as valueExpression>
		                  	<td>${r"${"}${valueExpression}${r"}"}</td>
	                  	</#list>                  		                  
	                    <td><a href="<c:url value='${currentCrudModel.getRemoveAction()}'/>/${r"${object.id}"}">Remove</a></td>
					  </tr>
                  </c:forEach>
                  </tbody>
          </table>
		  <template:paginationComponent paginatedList="${jspExpression.write("paginatedList")}" page="${jspExpression.write("param.page")}" action="${currentCrudModel.getListAction()}"/>
          <a href="<c:url value='${currentCrudModel.getNewAction()}'/>" class="btn btn-success"><span class="glyphicon glyphicon-plus-sign"></span> Add New</a>
        </div>
    </div>
  </div>
</jsp:body>
</template:admin>
