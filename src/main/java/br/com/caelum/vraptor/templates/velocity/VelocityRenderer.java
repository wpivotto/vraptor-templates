package br.com.caelum.vraptor.templates.velocity;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import br.com.caelum.vraptor.templates.TemplateRenderer;

public class VelocityRenderer implements TemplateRenderer {
	
	private final Template template;
	private final VelocityContext context;

	public VelocityRenderer(Template template, VelocityContext context) {
		this.template = template;
		this.context = context;
	}

	@Override
	public void render(HttpServletResponse response) {
		
		PrintWriter writer = null;
		
		try {
			
			writer = response.getWriter();
			merge(writer);
			writer.flush();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void add(String key, Object value) {
		context.put(key, value);
	}

	@Override
	public String getContent() {
		
		StringWriter writer = new StringWriter();
		merge(writer);
		return writer.getBuffer().toString();
			
	}
	
	private void merge(Writer writer){
		try {
			template.merge(context, writer);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
