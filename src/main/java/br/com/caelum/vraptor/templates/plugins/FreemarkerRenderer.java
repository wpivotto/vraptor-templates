package br.com.caelum.vraptor.templates.plugins;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.templates.TemplateRenderer;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerRenderer implements TemplateRenderer {
	
	private final Template template;
	private final Map<String, Object> root = new HashMap<String, Object>();

	public FreemarkerRenderer(Template template) {
		this.template = template;
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
		root.put(key, value);
	}

	@Override
	public void add(String key, Collection<?> values, Class<?> type) {
		root.put(key, values); 
	}
	
	@Override
	public String getContent() {
		
		StringWriter writer = new StringWriter();
		merge(writer);
		return writer.getBuffer().toString();
		
	}
	
	private void merge(Writer writer) {
		try {
			template.process(root, writer);
			writer.flush();
		} catch (TemplateException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
