#Para rodar o projeto, precisa ter o forge instalado. Siga os seguintes passos:

	* Download forge, just follow this link https://repository.jboss.org/nexus/service/local/artifact/maven/redirect?r=releases&g=org.jboss.forge&a=forge-distribution&v=2.13.0.Final&e=zip&c=offline  
    * Rename application.properties.example to application.properties e aponte o seu diretorio de instalação/addons
    * In order to verify if forge is working as expected, just execute the ForgeStandaloneTest class.
	* A new project will be created in the temp dir, the path will be returned.	
	* Importe o projeto gerado como maven project :).
	
##Configuração de banco de dados

You need to create a database called setupmyproject_dev. In relation to that, it is important that all controllers have the @Transactional annotation, you can use the javax package. This is important beacuse any step could access the database.

##Do you want to create a new sequence of steps?

###The ProjectType class

It is a enum that has all kind of projects that user can create, at least until now. Take a look and analyze how it is configured the sequence of steps, from the beginning until the end of the wizard.

###ProjectCommand and CommandGenerator interfaces

ProjectCommand is the interface you need to implement to create a new Command. Take a look in the implementations of commands to generate a Spring MVC project. The CommandGenerator is necessary because, probably, from your form will need to generate a new Command. Take a look in the **com.setupmyproject.controllers.forms** package.

###The controllers for the wizard steps

If you need some sort of validation, take a look in the MavenSetupController class. If you don't need, take a look in DBSetupController.

##Testing the wizard in dev/homolog environment

To acess the downloads page, access RequestedSetup table, choose a token and acess http://localhost:8080/downloads/index?token=tokenGeradoNoBancoDeDados.

##Facets of Jboss Forge

In your ProjectType, you can provide all the facets that will be needed to execute your command.

##Menu with commands in the view
Each ProjectCommand has a method called getNameKey() and the return is used as key in your pages. Did you create a new command? Give a name to it! There is a default implementation in the interface, that uses your class name as part of the Command name key. When the ProjectCommand is a Enum, you must override the getNameKey().

##Names of ProjectType that the user can choose
Basically it uses the getNameKey(). For the ProjectType, you must the Enum name directly in the properties.
