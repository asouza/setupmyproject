package pacoteControllerDefinidoNoJava;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

<#list currentCrudModel.getControllerImports(crudModels,mavenForm) as importLine>
import ${importLine};	
</#list>


@Controller
@RequestMapping("/${currentCrudModel.getModuleName()}")
@Transactional
public class ${currentCrudModel.getControllerName()} {
	
	@Autowired
	private ${currentCrudModel.getDaoName()} ${currentCrudModel.getDaoVariableName()};
	<#list relatedCrudModels.getList() as relatedCrudModel>
	@Autowired
	private ${relatedCrudModel.getDaoName()} ${relatedCrudModel.getDaoVariableName()};
	</#list>	

	@GetMapping("/form")
	public ModelAndView form(${currentCrudModel.getName()} ${currentCrudModel.getClassNameAsProperty()}){
		ModelAndView modelAndView = new ModelAndView("${currentCrudModel.getModuleName()}/form-add");
		<#if !(relatedCrudModels.isEmpty())>
		return loadFormDependencies(modelAndView);
		</#if>
		<#if (relatedCrudModels.isEmpty())>
		return modelAndView;
		</#if>		
		
	}
	
	<#if !relatedCrudModels.isEmpty()>
	private ModelAndView loadFormDependencies(ModelAndView modelAndView) {
		<#list relatedCrudModels.getList() as relatedCrudModel>
		modelAndView.addObject("${relatedCrudModel.getClassNameAsPropertyPluralized()}", ${relatedCrudModel.getDaoVariableName()}.all());	
		</#list>
		return modelAndView;
	}
	</#if>
	
	@PostMapping
	public ModelAndView save(@Valid ${currentCrudModel.getName()} ${currentCrudModel.getClassNameAsProperty()},BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return form(${currentCrudModel.getClassNameAsProperty()});
		}
		${currentCrudModel.getDaoVariableName()}.save(${currentCrudModel.getClassNameAsProperty()});
		return new ModelAndView("redirect:${currentCrudModel.getListAction()}");
	}
	
	@GetMapping("/{id}")
	public ModelAndView load(@PathVariable("id") Integer id){
		ModelAndView modelAndView = new ModelAndView("${currentCrudModel.getModuleName()}/form-update");
		modelAndView.addObject("${currentCrudModel.getClassNameAsProperty()}", ${currentCrudModel.getDaoVariableName()}.findById(id));
		<#if !relatedCrudModels.isEmpty()>
		loadFormDependencies(modelAndView);
		</#if>		
		return modelAndView;
	}
	
	@GetMapping
	public ModelAndView list(@RequestParam(defaultValue="0",required=false) int page){
		ModelAndView modelAndView = new ModelAndView("${currentCrudModel.getModuleName()}/list");
		modelAndView.addObject("paginatedList", ${currentCrudModel.getDaoVariableName()}.paginated(page,10));
		return modelAndView;
	}
	
	//just because get is easier here. Be my guest if you want to change.
	@GetMapping("/remove/{id}")
	public String remove(@PathVariable("id") Integer id){
		${currentCrudModel.getName()} ${currentCrudModel.getClassNameAsProperty()} = ${currentCrudModel.getDaoVariableName()}.findById(id);
		${currentCrudModel.getDaoVariableName()}.remove(${currentCrudModel.getClassNameAsProperty()});
		return "redirect:${currentCrudModel.getListAction()}";
	}
	
	@PostMapping("/{id}")
	public ModelAndView update(@PathVariable("id") Integer id,@Valid ${currentCrudModel.getName()} ${currentCrudModel.getClassNameAsProperty()}, BindingResult bindingResult) {
		${currentCrudModel.getClassNameAsProperty()}.setId(id);
		if (bindingResult.hasErrors()) {
			<#if !(relatedCrudModels.isEmpty())>
			return loadFormDependencies(new ModelAndView("${currentCrudModel.getModuleName()}/form-update"));
			</#if>
			<#if (relatedCrudModels.isEmpty())>
			return new ModelAndView("${currentCrudModel.getModuleName()}/form-update");
			</#if>					
		}
		${currentCrudModel.getDaoVariableName()}.update(${currentCrudModel.getClassNameAsProperty()});
		return new ModelAndView("redirect:${currentCrudModel.getListAction()}");
	}		
}
