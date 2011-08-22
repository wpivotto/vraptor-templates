package br.com.caelum.vraptor.templates.plugins

import java.io.File
import org.fusesource.scalate.util.FileResourceLoader
import org.fusesource.scalate._
import scala.collection.JavaConversions._

class ScalatePlugin(path: String) extends TemplatePlugin {

  val engine = new TemplateEngine
  engine.allowCaching = false
  engine.allowReload = true  
  engine.resourceLoader = new FileResourceLoader(Some(new File(path))) 
 
  def getRenderer(template: String): TemplateRenderer = {
    new ScalateRenderer(template, engine)
  }
  
}
