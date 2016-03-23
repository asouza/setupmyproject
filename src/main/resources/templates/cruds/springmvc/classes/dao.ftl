package pacoteControllerDefinidoNoJava;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ${mavenForm.packageFor("models.PaginatedList")};
<#list daoImports as importLine>
import ${importLine};	
</#list>

@Repository
public class ${currentCrudModel.getDaoName()} {
	
	@PersistenceContext
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
	
	public PaginatedList paginated(int page, int max) {
		return new PaginatorQueryHelper().list(manager, ${currentCrudModel.getName()}.class, page, max);		
	}	

}
