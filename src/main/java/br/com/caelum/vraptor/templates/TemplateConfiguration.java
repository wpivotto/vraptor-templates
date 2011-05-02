package br.com.caelum.vraptor.templates;

import java.io.IOException;

public interface TemplateConfiguration {
	
	TemplateRenderer getRenderer(String templateName) throws IOException;

}
