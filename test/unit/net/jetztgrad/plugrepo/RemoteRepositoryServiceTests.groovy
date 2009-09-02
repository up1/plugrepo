package net.jetztgrad.plugrepo

import grails.test.*

import net.jetztgrad.plugrepo.Repository
import net.jetztgrad.plugrepo.Plugin
import net.jetztgrad.plugrepo.PluginRelease

import net.jetztgrad.plugrepo.RemoteRepositoryService

class RemoteRepositoryServiceTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSomething() {

    }

	void testUpdate() {
		def repos= [ new Repository(name: Repository.LOCAL,
										type: RepositoryType.INTERNAL,
										description: "Main local repository",
										priority: 15,
										enabled: true) ]
		def plugins = []
		def pluginReleases = []
		
		mockDomain(Repository, repos)
		mockDomain(Plugin, plugins)
		mockDomain(PluginRelease, pluginReleases)
		mockLogging(RemoteRepositoryService)
		
		def service = new RemoteRepositoryService()
		
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
		def results = service.createPluginsFromXml(repo, pluginList)
		
		assertNotNull results
		assertEquals 1, Plugin.count()
		assertEquals 1, PluginRelease.count()
	}
}
