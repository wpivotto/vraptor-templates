package br.com.caelum.vraptor.templates.plugins

class TemplateHelper(context: String) {	  
	implicit def string2PimpedJSTLCore(str:String) = new PimpedJSTLCore(str, context)
	implicit def objectToPimpedObject(obj:java.lang.Object) = new PimpedObject(obj)
  }
  
  class PimpedJSTLCore(str:String, context:String){	
	def url = context + "/" + str
  }
  
  class PimpedObject(obj:java.lang.Object){
	def i18n = I18nHelper.i(obj.toString)
  }
  
  object I18nHelper{
    def i(str:String) = new CustomValidations().i18n(str)
  }
  
  
 