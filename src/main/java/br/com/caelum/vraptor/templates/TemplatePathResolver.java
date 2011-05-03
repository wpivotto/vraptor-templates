package br.com.caelum.vraptor.templates;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Component
@RequestScoped
public class TemplatePathResolver {

	private String path;

	public void updateTemplatePathFor(ResourceMethod method) {
		String name = method.getResource().getType().getSimpleName();
		String folderName = extractControllerFromName(name);
		this.path = folderName + "/" + method.getMethod().getName();
	}

	public String getTemplatePath() {
		return this.path;
	}


	protected String extractControllerFromName(String baseName) {
		baseName = lowerFirstCharacter(baseName);
		if (baseName.endsWith("Controller")) {
			return baseName.substring(0, baseName.lastIndexOf("Controller"));
		}
		return baseName;
	}

	private String lowerFirstCharacter(String baseName) {
		return baseName.toLowerCase().substring(0, 1) + baseName.substring(1, baseName.length());
	}


}
