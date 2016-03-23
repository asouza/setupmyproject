<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:jsf="http://xmlns.jcp.org/jsf">

<ui:composition>
  	<#list form.innerElements() as input>
        <div class="form-group">
          <label for="${input.getId()}">${input.getName()}</label>
          <div class="input-group">
            <span class="input-group-addon"><i class="glyphicon glyphicon-unchecked"></i></span>${input.generate(frameworkConf.getFormProcessor())}
          </div>
        </div>
   </#list>
</ui:composition>   
</html>
