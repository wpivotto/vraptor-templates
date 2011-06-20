package br.com.caelum.vraptor.templates;

import java.util.Collection;


/**
 * A simple template to be rendered. Add the variables and then render it.
 * @author Andrew Kurauchi
 * @author Guilherme Silveira
 * @author Victor Harada
 *
 */
public interface Template {

	/**
	 * Adds a variable to the current template
	 */
	Template with(String key, Object value);
	
	Template with(String key, Collection<?> values, Class<?> type);

	/**
	 * Renders this template to the end user
	 */
	void render();
	
	/**
	 * Renders this template and returns its content
	 */
	String getContent();

}
