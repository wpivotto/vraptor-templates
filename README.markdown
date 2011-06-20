## vraptor-templates

Uma biblioteca simples para renderizar templates com vraptor.
É fortemente baseada no projeto <https://github.com/caelum/vraptor-freemarker>

A idéia é tornar padrão a renderização de templates independente da engine escolhida (Freemarker, Velocity...)
bastando adicionar os jars da sua implementação preferida no classpath

Engines Suportadas
------

* Velocity
* Freemarker
* Scalate (SSP)

Instalação (Adicione ao seu web.xml)
------

Adicione os .jars e dependências na pasta WEB-INF/libs
Configure o web.xml

		<context-param>
        	<param-name>br.com.caelum.vraptor.packages</param-name>
	        <param-value>br.com.caelum.vraptor.templates</param-value>
    	</context-param>

Renderizando usando TemplateService
------

		@Resource
		public class DashboardController {
		
			private final Clients clients;
			private final TemplateService service;
		
			public DashboardController(Clients clients, TemplateService service) {
				this.clients = clients;
				this.service = service;
			}
			
			@Get("/clients/dashboard")
			public void dashboard(){
				service.use("clients/dashboard").with("clients", clients.listAll()).render();
			}
			
		}
		
Renderizando usando Result
------
		
		import static br.com.caelum.vraptor.templates.TemplateResult.*;
		
		@Resource
		public class DashboardController {
		
			private final Clients clients;
			private final Result result;
		
			public DashboardController(Clients clients, Result result) {
				this.clients = clients;
				this.result = result;
			}
			
			@Get("/clients/dashboard")
			public void dashboard(){
				result.use(template()).with("clients", clients.listAll()).render();
			}
			
		}

Objetos implicitos
------

Objetos injetados pela biblioteca:

* request
* context path
* localization
* validator

Exemplos de uso: ${localization.locale.country}

Decorando templates
------

Para injetar outros objetos basta construir uma classe como esta

	@Component
	public class CustomDecorator implements TemplateDecorator {

		private final User user;
	
		public CustomDecorator(User user) {
			this.user = user;
		}
	
		public void decorate(Template template){
			template.with("user", user);
		}
	}

# Convenções

Ao terminar a execução do método, o dispatch da requisição vai para o template /WEB-INF/templates/clients/dashboard.vm ou ftl.
Ou seja, a convenção para a view padrão é /WEB-INF/templates/nome_do_controller/nome_do_metodo.extensao_do_template


Scalate (Scala Server Pages)
------

Para tornar os templates mais "DRY", os binds e imports dos atributos passados para a template são resolvidos automaticamente pela biblioteca.
Logo não é necessário declarar nenhum atributo no cabeçalho da sua template, como no exemplo:

	<%@ val model: Person %>
	<% import model._ %>
	<p>Hello ${name}, what is the weather like in ${city}</p>
	
Basta acessar diretamente os métodos:
	
	<p>Hello ${person.getName}, what is the weather like in ${person.getCity}</p>

Para passar coleções é necessário especificar o tipo do dado:

	result.use(template()).with("clientes", clients.listAll(), Client.class).render()

Assim para iterar basta fazer:

	#for(client <- clients)
    	<tr>  
	      	<td>${client.getName}</td>          
	      	<td>${client.getEmail}</td>
	   	</tr>  
	#end
