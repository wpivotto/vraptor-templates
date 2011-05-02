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
			
			@Get("/admin/dashboard")
			public void lista() throws IOException {
				engine.use("dashboard").with("usuarioLogado", usuario).render();
			}
			
		}
		
Renderizando paginas usando Result
------
		
		import static br.com.caelum.vraptor.templates.TemplateResult.*;
		
		@Resource
		public class DashboardController {
		
			private final Usuario usuario;
			private final Result result;
		
			public DashboardController(Usuario usuario, Result result) {
				this.usuario = usuario;
				this.result = result;
			}
			
			@Get("/admin/dashboard")
			public void lista() throws IOException {
				result.use(template("dashboard")).with("usuarioLogado", usuario).render();
			}
			
		}

# Renderizando emails

		String body = engine.use("notificacao_email_enviado").with("usuarioLogado", usuario).getContent();
		String body = result.use(template("notificacao_email_enviado")).with("usuarioLogado", usuario).getContent();


