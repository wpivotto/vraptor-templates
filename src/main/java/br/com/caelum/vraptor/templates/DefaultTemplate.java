package br.com.caelum.vraptor.templates;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.Result;

public class DefaultTemplate implements Template {

	private final TemplateRenderer renderer;
	private final HttpServletResponse response;
	private final Result result;

	public DefaultTemplate(TemplateRenderer renderer, HttpServletResponse response, Result result) {
		this.renderer = renderer;
		this.response = response;
		this.result = result;
	}

	@Override
	public void render() {
		renderer.render(response);
		result.nothing();
	}

	@Override
	public Template with(String key, Object value) {
		renderer.add(key, value);
		return this;
	}
	
	@Override
	public Template with(String key, Collection<?> values, Class<?> type) {
		renderer.add(key, values, type);
		return this;
	}

	@Override
	public String getContent() {
		return renderer.getContent();  
	}

}
