package br.com.caelum.vraptor.templates.plugins

import br.com.caelum.vraptor.templates.{TemplateNotFoundException, TemplatesConfiguration}
import java.io.File
import org.fusesource.scalate.util.FileResourceLoader
import org.fusesource.scalate._
import org.slf4j.LoggerFactory
import scala.collection.JavaConversions._

class ScalatePlugin(configs: TemplatesConfiguration) extends TemplatePlugin {

  val engine = new TemplateEngine
  val path = configs.getTemplatesPath
  engine.allowCaching = configs.allowCaching
  engine.allowReload = configs.allowReload  
  engine.resourceLoader = new FileResourceLoader(Some(new File(path))) 
  val logger = LoggerFactory.getLogger(getClass)
 
  def getRenderer(templateName: String): TemplateRenderer = {
	 val template = getTemplate(templateName)
     new ScalateRenderer(template, engine, configs)
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
