<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core">
<ui:composition template="/shared/crud-template.xhtml">
	<ui:define name="body">
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
	                  <ui:repeat value="${jsfExpression.begin()}${currentCrudModel.getControllerNameAsProperty()}.${currentCrudModel.getClassNameAsPropertyPluralized()}}" var="object">         		
		                  <tr>
							<td>
							 <a href="${jsfExpression.begin()}request.contextPath}/${currentCrudModel.getModuleName()}/form-update.xhtml?id=${jsfExpression.begin()}object.id}">${jsfExpression.begin()}object.id}</a>
							</td>						
		                  	<#list table.getRowExpression().getValueExpressions() as valueExpression>
			                  	<td>${jsfExpression.begin()}${valueExpression}}</td>
		                  	</#list>                  		                  
							<td>
								<h:form>
									<h:commandLink action="${jsfExpression.begin()}${currentCrudModel.getControllerNameAsProperty()}.remove(object.id)}">Remove</h:commandLink>
								</h:form>
							</td>
						  </tr>
	                  </ui:repeat>
	                  </tbody>
	          </table>
			  
	          <a href="${jsfExpression.begin()}request.contextPath}/${currentCrudModel.getModuleName()}/form-add.xhtml" class="btn btn-success"><span class="glyphicon glyphicon-plus-sign"></span> Add New</a>
	        </div>
	    </div>
	  </div>
	</ui:define>
</ui:composition>

</html>
