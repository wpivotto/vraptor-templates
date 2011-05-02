package br.com.caelum.vraptor.templates;

import java.io.IOException;

/**
 * A Generic template renderer
 * 
 * @author Andrew Kurauchi
 * @author Guilherme Silveira
 *
 */
public interface TemplateEngine {

	Template use(String name) throws IOException;

}
