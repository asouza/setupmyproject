#Para rodar o projeto, precisa ter o forge instalado. Siga os seguintes passos:

	* Baixe o forge seguindo este link https://repository.jboss.org/nexus/service/local/artifact/maven/redirect?r=releases&g=org.jboss.forge&a=forge-distribution&v=2.13.0.Final&e=zip&c=offline  
    * Renomeie o dev.properties.example para dev.properties e aponte o seu diretorio de instalação/addons
    * Para verificar se a integração com o forge está funcionando, rode a classe ForgeStandaloneTest
	* Atualmente ele está criando o projeto em um dir temporário, de todo jeito é retornado o endereço.	
	* Importe o projeto gerado como maven project :).
	
##Configuração de banco de dados

É necessário que seja criado um banco, no mysql, chamado setupmyproject_dev. Ainda em relação a isso, é importante que todos
os controllers tenham o @Transactional em cima, do javax mesmo. Já que qualquer passo pode precisar de acesso ao banco de 
dados.

##Para criar uma nova sequência de comandos

###A classe ProjectType

É uma enum que contém os tipos de projetos possíveis até o momento. Dê uma olhada nela para ver como foi configurada a
sequência de passos até chegar ao fim do wizard.

###A interface ProjectCommand e CommandGenerator

A primeira é a interface necessária para você criar um comando. Dê uma olhada nas implementações para gerar comandos para
o projeto usando o Spring MVC. O CommandGenerator é necessário para, provavelmente a partir do seu formulário, vc gerar o 
ProjectCommand necessário. Dê uma olhada nos formulários no pacote controllers.form.

###Os controllers para os passos do wizard

Caso você tenha que adicionar validação, dê uma olhada na classe MavenSetupController. Se não for preciso validação, dê uma
olhada na classe DBSetupController.

##Testando o wizard em ambiente de dev/homolog(podemos melhorar)

Não é necessário criar todo fluxo de pagamento em dev. Ele vai já mostrar o botão de download automaticamente quando estivermos
em dev. 

Para acessar a página de download, vá na tabela de RequestedSetup, pegue um token e acesse http://localhost:8080/setupmyproject/downloads/index?token=tokenGeradoNoBancoDeDados.

Para ver o botão de pagamento em homolog, basta que vc troque de dev para homolog na classe ServletSpringMVC

##Facets do forge necessárias

OS tipos de projetos diferentes podem precisar de facets não instaladas por default. Por conta disso, no próprio ProjectType
você tem a chance de disponibilizar os facets necessários.

##Menu com comandos adicionados na view
Cada ProjectCommand tem um método que chama getNameKey() e o mesmo é usado como chave nas views. Criou um comando novo? Já dê um
nome para ele! Quando o ProjectCommand é uma Enum, tem que sobreescrever o método getNameKey(), não sei o motivo.

##Nomes das opções para o usuário escolher
Basicamente usa o getNameKey(). No caso dos ProjectType, tem que adicionar o nome da enum diretamente no properties.

##Simulando produção

Para simular a compra, basta subir a app em ambiente de homologação. Para realizar a compra no ambiente da sanbox, pode
usar o usas as seguintes opções de login

* user = teste2345@gmail.com, senha = teste2345 (norte americano)
* user = alots.ssa-buyer@gmail.com, senha = testeteste (brasileiro)
