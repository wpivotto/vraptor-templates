package br.com.caelum.vraptor.templates;

import java.io.IOException;

import br.com.caelum.vraptor.View;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

@Component
@RequestScoped
public class TemplateResult implements View {

	private final Template template;

	public TemplateResult(TemplateService service, TemplatePathResolver resolver) {

		try {

			this.template = service.use(resolver.getTemplatePath());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Template with(String key, Object value) {
		template.with(key, value);
		return template;
	}

	public static Class<TemplateResult> template() {
		return TemplateResult.class;
	}

}
