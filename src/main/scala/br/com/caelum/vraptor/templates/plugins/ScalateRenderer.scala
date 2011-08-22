package br.com.caelum.vraptor.templates.plugins
import java.io.{PrintWriter, StringWriter}
import javax.servlet.http.HttpServletResponse
import org.fusesource.scalate.{Binding, DefaultRenderContext, Template, TemplateEngine}
import org.slf4j.LoggerFactory
import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer

class ScalateRenderer(templateName: String, engine: TemplateEngine) extends TemplateRenderer {
  
  val output = new StringWriter
  val context = new DefaultRenderContext("", engine, new PrintWriter(output))
  val logger = LoggerFactory.getLogger(getClass)
  val bindings = new ListBuffer[Binding]
  
  def render(response: HttpServletResponse) {
    render
    response.getWriter.write(output.toString)
  }

  def add(key: String, value: Object) {
    bind(key, value)
  }

  def add(key: String, values: java.util.Collection[_], clazz: java.lang.Class[_]) {
    bind(key, values, clazz)
  }

  def getContent(): String = {
    render
    output.toString()
  }

  private def render() {
    val template = engine.load(templateName + ".ssp", bindings.toList)
    template.render(context)
  }
 
  private def bind(key: String, value: AnyRef) {
    bindings += Binding(key, value.getClass.getName)
    engine.importStatements ::= "import " + value.getClass.getName
    context.attributes(key) = value
    logger.debug("binding " + key + " = " + value.getClass.getName)
  }

  private def bind(key: String, values: java.util.Collection[_], clazz: java.lang.Class[_]) {
    val className = "scala.collection.Iterable[" + clazz.getSimpleName + "]"
    val iterable : scala.collection.Iterable[_] = values
    context.attributes(key) = iterable
    bindings += Binding(key, className)
    engine.importStatements ::= "import " + clazz.getName
    logger.debug("binding " + key + " = " + className)
  }

}