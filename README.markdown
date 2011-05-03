## vraptor-templates

Uma biblioteca simples para renderizar templates com vraptor.
É um fork do projeto <https://github.com/caelum/vraptor-freemarker>

A idéia é tornar padrão a renderização de templates independente da engine escolhida (Freemarker, Velocity...)
bastando adicionar os jars da sua implementação preferida no classpath


Renderizando paginas usando TemplateEngine
------

		@Resource
		public class DashboardController {
		
			private final Clients clients;
			private final TemplateEngine engine;
		
			public DashboardController(Clients clients, TemplateEngine engine) {
				this.clients = clients;
				this.engine = engine;
			}
			
			@Get("/clients/dashboard")
			public void dashboard() throws IOException {
				engine.use("clients/dashboard").with("clients", clients.listAll()).render();
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
			public void dashboard() throws IOException {
				result.use(template()).with("clients", clients.listAll()).render();
			}
			
		}

# Convenções

Ao terminar a execução do método, o dispatch da requisição vai para o template /WEB-INF/templates/clients/dashboard.vm ou ftl.
Ou seja, a convenção para a view padrão é /WEB-INF/templates/nome_do_controller/nome_do_metodo.extensao_do_template


