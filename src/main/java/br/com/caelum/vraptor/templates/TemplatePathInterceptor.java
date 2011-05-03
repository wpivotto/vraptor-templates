package br.com.caelum.vraptor.templates;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Intercepts
public class TemplatePathInterceptor implements Interceptor {

	private final TemplatePathResolver resolver;
	
	public TemplatePathInterceptor(TemplatePathResolver resolver) {
		this.resolver = resolver;
	}

	@Override
	public boolean accepts(ResourceMethod method) {
		return true;
	}

	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method, Object instance) throws InterceptionException {
	
		resolver.updateTemplatePathFor(method);
		stack.next(method, instance);
		
	}
	

}
