## vraptor-templates

Uma biblioteca simples para renderizar templates com vraptor.
É um fork do projeto <https://github.com/caelum/vraptor-freemarker>

A idéia é tornar padrão a renderização de templates independente da engine escolhida (Freemarker, Velocity...)
bastando adicionar os jars da sua implementação preferida no classpath


Renderizando paginas usando TemplateEngine
------

		@Resource
		public class DashboardController {
		
			private final Usuario usuario;
			private final TemplateEngine engine;
		
			public DashboardController(Usuario usuario, TemplateEngine engine) {
				this.usuario = usuario;
				this.engine = engine;
			}
			
			@Path("/admin/dashboard")
			@Get
			public void lista() throws IOException, TemplateException {
				engine.use("dashboard").with("usuarioLogado", usuario).render();
			}
			
		}
		
Renderizando paginas usando Result
------

		@Resource
		public class DashboardController {
		
			private final Usuario usuario;
			private final Result result;
		
			public DashboardController(Usuario usuario, Result result) {
				this.usuario = usuario;
				this.result = result;
			}
			
			@Path("/admin/dashboard")
			@Get
			public void lista() throws IOException, TemplateException {
				result.use(template("dashboard")).with("usuarioLogado", usuario).render();
			}
			
		}

# Renderizando emails

		String body = engine.use("notificacao_email_enviado").with("usuarioLogado", usuario).getContent();
		String body = result.use(template("notificacao_email_enviado")).with("usuarioLogado", usuario).getContent();


