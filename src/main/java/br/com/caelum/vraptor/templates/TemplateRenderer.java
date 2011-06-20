package br.com.caelum.vraptor.templates;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;


public interface TemplateRenderer {

	public void render(HttpServletResponse response);

	public void add(String key, Object value);
	
	public void add(String key, Collection<?> values, Class<?> type);  

	public String getContent();
	
}
