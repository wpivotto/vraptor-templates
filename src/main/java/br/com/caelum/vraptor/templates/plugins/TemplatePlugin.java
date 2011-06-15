package br.com.caelum.vraptor.templates.plugins;

import br.com.caelum.vraptor.templates.TemplateRenderer;


public interface TemplatePlugin {
	
	TemplateRenderer getRenderer(String templateName);

}
