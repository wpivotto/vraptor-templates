## vraptor-templates

A simple library to render templates with vraptor. 

The idea is to make the default rendering engine independent of the chosen template (Freemarker, Velocity ...) 
Simply add your preferred implementation in the classpath. 

Engines
------

* Velocity
* Freemarker
* Scalate (SSP, Scaml, Jade, Mustache) 

Installation
------

Put `vraptor-template.jar` and dependencies in your `WEB-INF/lib` folder. You can get a copy here
Add packages on `web.xml`

		<context-param>
        	<param-name>br.com.caelum.vraptor.packages</param-name>
	        <param-value>br.com.caelum.vraptor.templates</param-value>
    	</context-param>

Rendering using TemplateService 
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
		
Rendering using Result 
------
		
		import static br.com.caelum.vraptor.templates.TemplateView.*;
		
		@Resource
		public class DashboardController {
		
			private final Clients clients;
			private final Result result;
		
			public DashboardController(Clients clients, Result result) {
				this.clients = clients;
				this.result = result;
			}
			
			@Get("/dashboard")
			public void dashboard(){
				result.use(template()).with("clients", clients.listAll()).render();
			}
			
			@Get("/dashboard/{id}")
			public void dashboard(Long id){
				result.include("client", clients.find(id));
				result.use(template()).render();
			}
			
		}

Implicit objects 
------

Objects injected by the library: 

* request
* request parameters (result.include)
* context path
* i18n

Decorating templates 
------

To inject other objects just build a class like this: 

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

# Conventions 

At the end of method execution, VRaptor will dispatch the request to the template at `/WEB-INF/templates/clients/dashboard.(vm, ftl, ssp...)`. 
The convention for the default view is `/WEB-INF/templates/<controller_name>/<method_name>.<file_extension>`.

Scalate
------

To make the templates more "DRY", the binds and imports of attributes passed to the template are resolved automatically by the library.
So it's not necessary to declare any attribute in the header of your template, like this:

	<%@ val model: Person %>
	<% import model._ %>
	<p>Hello ${name}, what is the weather like in ${city}</p>
	
Just go directly to the methods:
	
	<p>Hello ${person.getName}, what is the weather like in ${person.getCity}</p>

To pass collections:

	result.use(template()).with("clients", clients.listAll(), Client.class).render()

So to iterate just do:

	#for(client <- clients)
    	<tr>  
	      	<td>${client.getName}</td>          
	      	<td>${client.getEmail}</td>
	   	</tr>  
	#end

Helpers
------

Helper methods automatically exposed to the templates

	%form{:action => {"clients".url}, :method => "post"}
	
will generate the following action `/context-path/clients` (Equivalent of `<c:url/>`)

	${"users_form".i18n}
	
will search for the key `users_form` in the resource bundle `messages.properties` 

Example
------

<https://github.com/wpivotto/vraptor-templates-example>