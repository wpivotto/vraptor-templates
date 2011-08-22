package br.com.caelum.vraptor.templates;

import java.util.Collection;

import br.com.caelum.vraptor.View;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

@Component
@RequestScoped
public class TemplateView implements View {

	private final Template template;

	public TemplateView(TemplateService service, TemplatePathResolver resolver) {
		this.template = service.use(resolver.getTemplatePath());
	}

	public Template with(String key, Object value) {
		template.with(key, value);
		return template;
	}
	  
	public Template with(String key, Collection<?> value, Class<?> type) {
		template.with(key, value, type);
		return template;
	}
	
	public void render(){
		template.render();
	}
	
	public static Class<TemplateView> template() {
		return TemplateView.class;
	}

}
