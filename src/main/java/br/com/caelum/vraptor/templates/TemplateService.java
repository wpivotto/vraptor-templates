package br.com.caelum.vraptor.templates;

import java.io.IOException;

/**
 * A Generic template renderer
 * 
 * @author Andrew Kurauchi
 * @author Guilherme Silveira
 *
 */
public interface TemplateService {

	Template use(String name) throws IOException;
	
}
