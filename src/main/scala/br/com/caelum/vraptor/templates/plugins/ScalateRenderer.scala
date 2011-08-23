package br.com.caelum.vraptor.templates.plugins
import java.io.{PrintWriter, StringWriter}
import javax.servlet.http.HttpServletResponse
import org.fusesource.scalate.{DefaultRenderContext, Template, TemplateEngine}
import br.com.caelum.vraptor.templates.TemplatesConfiguration

class ScalateRenderer(templateName: String, engine: TemplateEngine, configs: TemplatesConfiguration) extends TemplateRenderer {
  
  val output = new StringWriter
  val context = new DefaultRenderContext("", engine, new PrintWriter(output))
  val binder = new Binder(engine, context, configs)
  
  def render(response: HttpServletResponse) {
    render
    response.getWriter.write(output.toString)
  }

  def add(key: String, value: Object) {
    binder.bind(key, value)
  }

  def add(key: String, values: java.util.Collection[_], clazz: java.lang.Class[_]) {
    binder.bind(key, values, clazz)
  }

  def getContent(): String = {
    render
    output.toString()
  }

  private def render() {
    val template = engine.load(templateName, binder.getBindings)
    template.render(context)
  }

}