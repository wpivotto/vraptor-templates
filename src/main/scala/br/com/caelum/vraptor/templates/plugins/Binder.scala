package br.com.caelum.vraptor.templates.plugins
import org.fusesource.scalate.{Binding, RenderContext, TemplateEngine}
import org.slf4j.LoggerFactory
import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer
import br.com.caelum.vraptor.templates.TemplatesConfiguration

class Binder(engine: TemplateEngine, context: RenderContext, configs: TemplatesConfiguration) {  
  
  val bindings = new ListBuffer[Binding]
  val logger = LoggerFactory.getLogger(getClass)
  
  def bind(key: String, value: AnyRef) {
    bindings += Binding(key, value.getClass.getName)
    context.attributes(key) = value
    engine.importStatements ::= "import " + value.getClass.getName
    logger.debug("binding " + key + " = " + value.getClass.getName)
  }

  def bind(key: String, values: java.util.Collection[_], clazz: java.lang.Class[_]) {
    val className = "scala.collection.Iterable[" + clazz.getSimpleName + "]"
    val iterable : scala.collection.Iterable[_] = values
    context.attributes(key) = iterable
    bindings += Binding(key, className)
    engine.importStatements ::= "import " + clazz.getName
    logger.debug("binding " + key + " = " + className)
  }
  
  def getBindings(): List[Binding] = {
    bindHelpers
    bindings.toList
  }
  
  
   def bindHelpers() {
     val helper = new TemplateHelper(configs.getContextPath)
     bindings += Binding("helper", helper.getClass.getName, true)
     context.attributes("helper") = helper
	 engine.importStatements  ::= "import br.com.caelum.vraptor.validator.ValidationMessage"
  }

}


  
