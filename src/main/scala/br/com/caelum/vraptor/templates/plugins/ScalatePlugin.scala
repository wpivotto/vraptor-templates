package br.com.caelum.vraptor.templates.plugins

import java.io.File
import org.fusesource.scalate.util.FileResourceLoader
import org.fusesource.scalate._
import scala.collection.JavaConversions._
import br.com.caelum.vraptor.templates.TemplateNotFoundException
import org.slf4j.LoggerFactory

class ScalatePlugin(path: String) extends TemplatePlugin {

  val engine = new TemplateEngine
  engine.allowCaching = false
  engine.allowReload = true  
  engine.resourceLoader = new FileResourceLoader(Some(new File(path))) 
  val logger = LoggerFactory.getLogger(getClass)
 
  def getRenderer(templateName: String): TemplateRenderer = {
	 val template = getTemplate(templateName)
     new ScalateRenderer(template, engine)
  }
  
  private def getTemplate(template : String): String = {
    if(templateExists(template, ".ssp"))
    	return template + ".ssp"
    if(templateExists(template, ".scaml"))
    	return template + ".scaml"
    if(templateExists(template, ".jade"))
    	return template + ".jade"
    if(templateExists(template, ".mustache"))
    	return template + ".mustache"
     throw new TemplateNotFoundException("Could not find template " + template + " at " + path)
  }
  
  private def templateExists(templateName : String, extension : String): Boolean = {
    val templatePath = normalizeName(path + File.separatorChar + templateName + extension)
    val file = new File(templatePath)
    logger.debug("trying to find " + file.getAbsolutePath)
    file.exists
  }
  
  private def normalizeName(name: String) = {
	val sep = File.separatorChar
	if(sep == '/') name else name.replace(sep, '/')
  }
  
  
  
  
}
