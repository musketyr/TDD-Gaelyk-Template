package pleasefollowme
import com.google.appengine.api.datastore.*
import com.google.appengine.api.mail.*
import com.google.appengine.api.memcache.*
import com.google.appengine.tools.development.testing.*


import groovyx.gaelyk.GaelykCategory

abstract class GaelykUnitSpec extends spock.lang.Specification {
	
	def groovletInstance
	def helper
	def datastore
	def memcache
	def mail
	
	def setup(){
		helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig(),
			new LocalMemcacheServiceTestConfig(),
			new LocalMailServiceTestConfig()
		)
		helper.setUp()
		
		Object.mixin GaelykCategory
		
		datastore = DatastoreServiceFactory.getDatastoreService()
		memcache = MemcacheServiceFactory.getMemcacheService()
		mail = MailServiceFactory.getMailService()
	}
	
	def teardown(){
		helper.tearDown()
	}
		
	def groovlet = {
		groovletInstance = new GroovletUnderSpec("$it")
		groovletInstance.datastore = datastore
		groovletInstance.memcache = memcache
		groovletInstance.mail = mail
		this.metaClass."${it.tokenize('/').last().tokenize('.').first()}" = groovletInstance
	}
		
}
	