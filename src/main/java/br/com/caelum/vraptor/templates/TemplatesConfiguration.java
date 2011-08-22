package br.com.caelum.vraptor.templates;

import javax.servlet.ServletContext;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
  
@Component     
@ApplicationScoped
public class TemplatesConfiguration {  
	
	private final ServletContext context;
	private static final String DEFAULT_TEMPLATES_PATH = "/WEB-INF/templates";

	public TemplatesConfiguration(ServletContext context) {
		this.context = context;
	}
	
	public boolean allowCaching(){
		String param = context.getInitParameter("vraptor.templates.cache");
		return param != null && param.equalsIgnoreCase("true");
	}
	
	public boolean allowReload(){
		String param = context.getInitParameter("vraptor.templates.reload");
		return param != null && param.equalsIgnoreCase("true");
	}
	
	public String getTemplatesPath(){
		return context.getRealPath(getTemplatesRelativePath());
	}
	
	private String getTemplatesRelativePath(){
		String param = context.getInitParameter("vraptor.templates.path");
		return param != null ? param.trim() : DEFAULT_TEMPLATES_PATH;
	}

}
