package br.com.caelum.vraptor.templates;

import javax.servlet.http.HttpServletResponse;


public interface TemplateRenderer {

	public void render(HttpServletResponse response);

	public void add(String key, Object value);

	public String getContent();
	
}
