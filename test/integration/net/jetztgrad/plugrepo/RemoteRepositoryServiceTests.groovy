package net.jetztgrad.plugrepo

import grails.test.*

class RemoteRepositoryServiceTests extends GrailsUnitTestCase {
	def remoteRepositoryService
	
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

	void testCreateSinglePLugin() {
		def repo = Repository.findByName(Repository.LOCAL)
		assertNotNull repo
		
		def pluginXml = """
<plugins revision="55186">
<plugin latest-release="0.5.2" name="acegi">
   <release tag="RELEASE_0_2" type="svn" version="0.2">
     <title>Spring Security (Acegi Security) on Grails Plugin</title>
     <author>Tsuyoshi Yamamoto</author>
     <authorEmail>tyama@xmldo.jp</authorEmail>
     <description>Plugin to use grails domain class from the Spring Security(Acegi Security) and secure your applications with Spring Security(Acegi Security) filters.
	</description>
     <documentation>http://docs.codehaus.org/display/GRAILS/AcegiSecurity+Plugin</documentation>
     <file>http://plugins.grails.org/grails-acegi/tags/RELEASE_0_2/grails-acegi-0.2.zip</file>
   </release>
</plugin>
</plugins>
"""
		def pluginList = new XmlSlurper().parse(new StringReader(pluginXml.toString()))
		def results = remoteRepositoryService.createPluginsFromXml(repo, pluginList)
		
		assertNotNull results
		assertEquals 1, Plugin.count()
		assertEquals 1, PluginRelease.count()
		
		def pluginRelease = PluginRelease.find("from PluginRelease as p where p.name = ? and p.pluginVersion = ? and p.repository = ?", [ 'acegi', '0.2', repo ])
		assertNotNull pluginRelease
	}
	
	void testCreateUpdatePLugin() {
		def repo = Repository.findByName(Repository.LOCAL)
		assertNotNull repo
		
		// create existing version
		def plugin = new Plugin(name: "acegi", 
						author: "Tsuyoshi Yamamoto",
						description: "description")
						
		def pluginRelease = new PluginRelease(name: "acegi", 
							pluginVersion: "0.1",
							plugin: plugin,
							repository: repo)
		plugin.addToReleases(pluginRelease)
		plugin.save()
		
		pluginRelease = PluginRelease.find("from PluginRelease as p where p.name = ? and p.pluginVersion = ? and p.repository = ?", [ 'acegi', '0.1', repo ])
		assertNotNull pluginRelease
		assertEquals 1, Plugin.count()
		assertEquals 1, PluginRelease.count()

		def pluginXml = """
<plugins revision="55186">
<plugin latest-release="0.5.2" name="acegi">
   <release tag="RELEASE_0_2" type="svn" version="0.2">
     <title>Spring Security (Acegi Security) on Grails Plugin</title>
     <author>Tsuyoshi Yamamoto</author>
     <authorEmail>tyama@xmldo.jp</authorEmail>
     <description>Plugin to use grails domain class from the Spring Security(Acegi Security) and secure your applications with Spring Security(Acegi Security) filters.
	</description>
     <documentation>http://docs.codehaus.org/display/GRAILS/AcegiSecurity+Plugin</documentation>
     <file>http://plugins.grails.org/grails-acegi/tags/RELEASE_0_2/grails-acegi-0.2.zip</file>
   </release>
</plugin>
</plugins>
"""
		def pluginList = new XmlSlurper().parse(new StringReader(pluginXml.toString()))
		def results = remoteRepositoryService.createPluginsFromXml(repo, pluginList)

		assertNotNull results
		assertEquals 1, Plugin.count()
		assertEquals 2, PluginRelease.count()

		pluginRelease = PluginRelease.find("from PluginRelease as p where p.name = ? and p.pluginVersion = ? and p.repository = ?", [ 'acegi', '0.2', repo ])
		assertNotNull pluginRelease
	}
}
