<html xmlns="http://www.w3.org/1999/xhtml"	
	xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:h="http://java.sun.com/jsf/html"
    xmlns:f="http://java.sun.com/jsf/core"
    xmlns:jsf="http://xmlns.jcp.org/jsf"
    xmlns:pt="http://xmlns.jcp.org/jsf/passthrough">
    <ui:composition template="/shared/crud-template.xhtml">
    	<ui:define name="body">
		  <div>
		    <div class ="container min-container">      
		    <h2 class="basic-title">Add</h2>
		      <h:form prependId="false" styleClass="well" pt:role="form">
		      	<ui:include src="form-inputs.xhtml"/>
		        <button type="submit" jsf:action="${jsfExpression.begin()}${currentCrudModel.getControllerNameAsProperty()}.save}" class="btn btn-primary">Submit</button>
		      </h:form>	
		    </div>
		  </div>
    	</ui:define>
    </ui:composition>
</html>
