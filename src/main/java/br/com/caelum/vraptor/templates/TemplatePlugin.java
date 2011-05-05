package br.com.caelum.vraptor.templates;

import java.io.IOException;

public interface TemplatePlugin {
	
	TemplateRenderer getRenderer(String templateName) throws IOException;

}
