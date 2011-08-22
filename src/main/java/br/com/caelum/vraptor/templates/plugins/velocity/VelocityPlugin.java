package br.com.caelum.vraptor.templates.plugins.velocity;

import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.templates.TemplateNotFoundException;
import br.com.caelum.vraptor.templates.TemplatesConfiguration;
import br.com.caelum.vraptor.templates.plugins.TemplatePlugin;
import br.com.caelum.vraptor.templates.plugins.TemplateRenderer;

public class VelocityPlugin implements TemplatePlugin {

	private VelocityContext context;
	private VelocityEngine engine;
	private final TemplatesConfiguration configs;
	private final Logger logger = LoggerFactory.getLogger(VelocityPlugin.class);

	public VelocityPlugin(TemplatesConfiguration configs) {
		this.configs = configs;
		Properties p = new Properties();
		p.setProperty("resource.loader", "file");
		p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		p.setProperty("file.resource.loader.path", configs.getTemplatesPath());
		if(configs.allowCaching())
			p.setProperty("file.resource.loader.cache", "true"); 
		context = new VelocityContext();
		engine = new VelocityEngine(p);
		engine.init();
	}

	public Template getTemplate(String name) {
		try {
			logger.debug("trying to load template " + name + "at" + configs.getTemplatesPath());
			return engine.getTemplate(name + ".vm");
		} catch (ResourceNotFoundException e) {
			throw new TemplateNotFoundException("Could not find template " + name + " at " + configs.getTemplatesPath());
		}
	}

	@Override
	public TemplateRenderer getRenderer(String templateName) {
		Template template = getTemplate(templateName);
		return new VelocityRenderer(template, context);
	}

}
