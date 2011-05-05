## vraptor-templates

Uma biblioteca simples para renderizar templates com vraptor.
É um fork do projeto <https://github.com/caelum/vraptor-freemarker>

A idéia é tornar padrão a renderização de templates independente da engine escolhida (Freemarker, Velocity...)
bastando adicionar os jars da sua implementação preferida no classpath

Instalação (Adicione ao seu web.xml)
------
		<context-param>
        	<param-name>br.com.caelum.vraptor.packages</param-name>
	        <param-value>br.com.caelum.vraptor.templates</param-value>
    	</context-param>

Renderizando paginas usando TemplateService
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
		
Renderizando paginas usando Result
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

# Convenções

Ao terminar a execução do método, o dispatch da requisição vai para o template /WEB-INF/templates/clients/dashboard.vm ou ftl.
Ou seja, a convenção para a view padrão é /WEB-INF/templates/nome_do_controller/nome_do_metodo.extensao_do_template


