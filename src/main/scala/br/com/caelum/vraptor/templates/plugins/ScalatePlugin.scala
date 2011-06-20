package br.com.caelum.vraptor.templates.plugins

import br.com.caelum.vraptor.templates.TemplateRenderer
import org.fusesource.scalate._
import java.io.File
import java.io.StringWriter
import java.io.PrintWriter
import org.fusesource.scalate.Template
import org.fusesource.scalate.util.FileResourceLoader
import br.com.caelum.vraptor.templates.TemplateNotFoundException 
import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._

class ScalatePlugin(path: String) extends TemplatePlugin {

  val engine = new TemplateEngine
  engine.allowCaching = false
  engine.allowReload = true  
  engine.resourceLoader = new FileResourceLoader(Some(new File(path))) 
 
  def getRenderer(template: String): TemplateRenderer = {
    new ScalateRenderer(template, engine)
  }
  
  def imports(){
    engine.importStatements  ::= "scala.collection.Iterable"
  }
  
}
