package br.com.caelum.vraptor.templates;


public interface TemplatePlugin {
	
	TemplateRenderer getRenderer(String templateName);

}
