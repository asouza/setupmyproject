package com.setupmyproject.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;

@XmlRootElement(name="dependencies")
@XmlAccessorType(XmlAccessType.FIELD)
public class MavenDepencies {

	@XmlElement(name="dependency")
	private List<MavenDependency> list = new ArrayList<MavenDependency>();
	
	public List<DependencyBuilder> getList() {
		return list.stream().map((mavenDep) -> mavenDep.toDependencyBuilder())
				.collect(Collectors.toList());
	}
	
	public List<MavenDependency> lista(){
		return list;
	}
}
