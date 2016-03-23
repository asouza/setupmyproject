<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  	<#list form.innerElements() as input>
        <div class="form-group">
          <label for="${input.getName()}">${input.getName()}</label>
          <div class="input-group">
            <span class="input-group-addon"><i class="glyphicon glyphicon-unchecked"></i></span>${input.generate(frameworkConf.getFormProcessor())}
          </div>
        </div>
   </#list>
