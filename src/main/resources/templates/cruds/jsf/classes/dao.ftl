package pacoteControllerDefinidoNoJava;

import java.util.List;

import javax.persistence.EntityManager;
import javax.inject.Inject;

<#list daoImports as importLine>
import ${importLine};	
</#list>

public class ${currentCrudModel.getDaoName()} {
	
	@Inject
	private EntityManager manager;

	public List<${currentCrudModel.getName()}> all() {
		return manager.createQuery("select ${currentCrudModel.getFirstLetter()?lower_case} from ${currentCrudModel.getName()} ${currentCrudModel.getFirstLetter()?lower_case}",${currentCrudModel.getName()}.class).getResultList();
	}

	public void save(${currentCrudModel.getName()} ${currentCrudModel.getClassNameAsProperty()}) {
		manager.persist(${currentCrudModel.getClassNameAsProperty()});
	}

	public ${currentCrudModel.getName()} findById(Integer id) {
		return manager.find(${currentCrudModel.getName()}.class, id);
	}
	
	public void remove(${currentCrudModel.getName()} ${currentCrudModel.getClassNameAsProperty()}) {
		manager.remove(${currentCrudModel.getClassNameAsProperty()});
	}	
	
	public void update(${currentCrudModel.getName()} ${currentCrudModel.getClassNameAsProperty()}) {
		manager.merge(${currentCrudModel.getClassNameAsProperty()});
	}	

}
