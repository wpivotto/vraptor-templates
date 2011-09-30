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

Put `vraptor-templates-1.0.XX.jar` and dependencies in your `WEB-INF/lib` folder. 

Rendering using Result 
------
		
		import static br.com.caelum.vraptor.templates.TemplateView.*;
		
		@Resource
		public class ClientsController {
		
			private final Clients clients;
			private final Result result;
		
			public ClientsController(Clients clients, Result result) {
				this.clients = clients;
				this.result = result;
			}
			
			public void form(){}
			
			public void show(){
				result.include("clients", clients.all());
				result.use(template()).render();
			}
			
			@Get("/edit/{id}")
			public void edit(Long id){
				result.include("client", clients.find(id));
				result.use(template()).render();
			}
			
			@Put("/clients")
			public void update(Client client){
				clients.update(client);
				result.redirectTo(this).show();
			}
			
		}
		
		
WEB-INF/templates/clients/edit.jade
------
		
		html
  			head
    			title = {"edit_client".i18n}
  			body
    			form(action={"clients".url} method="POST")
      				input(type="hidden" name="_method" value="PUT")
      				input(type="hidden" name="client.id" value={client.getId})
      				label(for="client.name") #{"label_name".i18n}:
      				input(type="text" name="client.name" value={client.getName})
      				label(for="client.email") #{"label_email".i18n}:
      				input(type="text" name="client.email" value={client.getEmail})
      				input(type="submit" value="Update" class="button")		
      	
      	
WEB-INF/templates/clients/form.scaml
------
      	
      	%html
  			%title #{"new_client".i18n}
  			%body
  				%form{:action => {"clients".url}, :method => "post"}
    				%label{:for => "client.name"} #{"label_name".i18n}:
    				%input{:type => "text", :name => "client.name"}
    				%label{:for => "client.email"} #{"label_email".i18n}:
    				%input{:type => "text", :name => "client.email"}
    				%input{:type => "submit", :value => "Save", :class => "button"}
    	
    	
WEB-INF/templates/clients/show.ssp
------

    	<body>
	   
	   		<a href="${"form".url}">New</a>
	   
	   			${"clients_list".i18n}
	  
				<table cellspacing = "5" border = "0" cellpadding = "5">  
		   			<tr>  
		      			<td>Name</td>   
		      			<td>Email</td>   
		   			</tr> 
		   			#for(client <- clients)
	    				<tr>    
		      				<td>${client.getName}</td>          
		      				<td>${client.getEmail}</td>
		      				<td><a href="${contextPath}/client/${client.getId}/edit">Edit</a></td>
		   				</tr>  
					#end
	   			</table>
	   
	   		<a href="${"logoff".url}">Log Off</a>
	   		
	   	</body>
		
		
# Conventions 

At the end of method execution, VRaptor will dispatch the request to the template at `/WEB-INF/templates/clients/show.(vm, ftl, ssp, scaml, jade, mustache)`. 
The convention for the default view is `/WEB-INF/templates/<controller_name>/<method_name>.<file_extension>`.
		
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