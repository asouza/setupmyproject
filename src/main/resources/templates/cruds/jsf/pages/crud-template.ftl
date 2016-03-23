<html xmlns="http://www.w3.org/1999/xhtml"	
	xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:h="http://java.sun.com/jsf/html"
    xmlns:f="http://java.sun.com/jsf/core"
    xmlns:jsf="http://xmlns.jcp.org/jsf">
<head >
  <meta charset="UTF-8"/>
  <title>Listing</title>

  <!-- bootstrap -->
  <link rel="stylesheet" href="${jsfExpression.begin()}request.contextPath}/assets/css/bootstrap.min.css"/>
  <link rel="stylesheet" href="${jsfExpression.begin()}request.contextPath}/assets/css/bootstrap-theme.min.css"/>

  <!-- style -->
   <link rel="stylesheet" href="${jsfExpression.begin()}request.contextPath}/assets/css/index.css"/>
   <link rel="stylesheet" href="${jsfExpression.begin()}request.contextPath}/assets/css/forms.css"/>
</head>
<body>

  <!-- INICIO NAV (alterar pra include)-->

  <nav class="navbar navbar-default">
    <div class="container-fluid">
      <div class="navbar-header">

        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#menu" aria-expanded="false">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="index.html">Project Name</a>
      </div>

      <div class="collapse navbar-collapse" id="menu">
        <ul class="nav navbar-nav">
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Admin <span class="caret"></span></a>
            <ul class="dropdown-menu">
	            <#list crudModels.getList() as crudModel>
	              <li><a href="${jsfExpression.begin()}request.contextPath}/${crudModel.getModuleName()}/form-add.xhtml"><span class="glyphicon glyphicon-plus-sign"></span> Add ${crudModel.getUserName()}</a></li>	              
	              <li><a href="${jsfExpression.begin()}request.contextPath}/${crudModel.getModuleName()}/list.xhtml"><span class="glyphicon glyphicon-menu-hamburger"></span> List ${crudModel.getUserName()}</a></li>
				  <li role="separator" class="divider"></li>
	            </#list>
            </ul>
          </li>
        </ul>
        
      </div>
    </div>
  </nav>

  <!-- FINAL NAV -->	

	<ui:insert name="body"/>

	<script src="${jsfExpression.begin()}request.contextPath}/assets/js/jquery-2.1.4.min.js"></script>
	<script src="${jsfExpression.begin()}request.contextPath}/assets/js/bootstrap.min.js"></script>

</body>
</html>
