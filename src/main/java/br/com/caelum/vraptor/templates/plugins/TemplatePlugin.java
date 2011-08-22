package br.com.caelum.vraptor.templates.plugins;



public interface TemplatePlugin {
	
	TemplateRenderer getRenderer(String templateName);

}
