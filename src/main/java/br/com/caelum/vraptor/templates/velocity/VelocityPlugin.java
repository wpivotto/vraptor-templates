package br.com.caelum.vraptor.templates.velocity;


import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import br.com.caelum.vraptor.templates.TemplatePlugin;
import br.com.caelum.vraptor.templates.TemplateRenderer;


public class VelocityPlugin implements TemplatePlugin {

	private VelocityContext context;
	private VelocityEngine engine;

	public VelocityPlugin(String path) {
		Properties p = new Properties();
		p.setProperty("resource.loader", "file");
		p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		p.setProperty("file.resource.loader.path", path);
	    context = new VelocityContext();
	    engine = new VelocityEngine(p);
	    engine.init();
	}

	public Template getTemplate(String templateName) {
		return engine.getTemplate(templateName + ".vm");
	}

	@Override
	public TemplateRenderer getRenderer(String templateName){
		Template template = getTemplate(templateName);
		return new VelocityRenderer(template, context);
	}

	
}
