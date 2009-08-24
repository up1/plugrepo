import net.jetztgrad.plugrepo.Repository
import net.jetztgrad.plugrepo.RepositoryType

class BootStrap {

     def init = { servletContext ->
	
		/////////////////////////////////////////////////
		// setup default repositories
		
		// local
		def local = Repository.findByName(Repository.LOCAL)
		if (!local) {
			local = new Repository(name: Repository.LOCAL,
											type: RepositoryType.INTERNAL,
											description: "Main local repository",
											priority: 15,
											enabled: true)
			if (local.save(flush: true)) {
				println "created default local repository"
			}
			else {
				println "failed to create default local repository:" + local.errors
			}
		}
		
		// local
		def localTest = Repository.findByName(Repository.TEST)
		if (!localTest) {
			def home = System.getProperty('user.home')
			def fileUrlString = "file://${home}/dev/plugins-list.xml"
			localTest = new Repository(name: Repository.TEST,
											type: RepositoryType.URL,
											description: "Local test repository",
											repositoryURL: fileUrlString, 
											priority: 5,
											enabled: true)
			if (localTest.save(flush: true)) {
				println "created local test repository"
			}
			else {
				println "failed to create local test repository:" + local.errors
			}
		}

		// grails.org
		def grailsOrg = Repository.findByName(Repository.GRAILSORG)
		if (!grailsOrg) {
			grailsOrg = new Repository(name: Repository.GRAILSORG,
											type: RepositoryType.SUBVERSION,
											description: "Official Grails repository on grails.org",
											repositoryURL: "http://plugins.grails.org", 
											enabled: true)
			
			if (grailsOrg.save(flush: true)) {
				println "created default grails.org repository"
			}
			else {
				println "failed to create default grails.org repository:" + grailsOrg.errors
			}
		}
	
     }
     def destroy = {
     }
} 