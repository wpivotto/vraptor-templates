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
	
	public boolean hasParameter(String param){
		return context.getInitParameter(param) != null;
	}
	
	public boolean isEnabled(String param){
		return hasParameter(param) && context.getInitParameter(param).equalsIgnoreCase("true");
	}
	
	public boolean allowCaching(){
		return isEnabled("vraptor.templates.cache");
	}
	
	public boolean allowReload(){
		return isEnabled("vraptor.templates.reload");
	}
	
	public String[] importStatements(){
		String packages = context.getInitParameter("vraptor.templates.packages");
		if(packages != null){
			return packages.split(",");
		}
		return new String[]{};
	}
	
	public String getTemplatesPath(){
		return context.getRealPath(getTemplatesRelativePath());
	}
	  
	public String getContextPath(){
		return context.getContextPath();
	}
	
	private String getTemplatesRelativePath(){
		String param = context.getInitParameter("vraptor.templates.path");
		return param != null ? param.trim() : DEFAULT_TEMPLATES_PATH;
	}

}
