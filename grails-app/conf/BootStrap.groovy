class BootStrap {

     def init = { servletContext ->
	
		/////////////////////////////////////////////////
		// setup default repositories
		
		// local
		def local = Repository.findByName(Repository.LOCAL)
		if (!local) {
			local = new Repository.findByName(name: Repository.LOCAL,
											type: INTERNAL,
											description: "Main local repository",
											priority: 15,
											enabled: true).save(flush: true)
		}

		// grails.org
		def grailsOrg = Repository.findByName(Repository.LOCAL)
		if (!grailsOrg) {
			grailsOrg = new Repository.findByName(name: Repository.GRAILSORG,
											type: SUBVERSION,
											description: "Official Grails repository on grails.org",
											repositoryURL: "http://plugins.grails.org", 
											enabled: true).save(flush: true)
		}
	
     }
     def destroy = {
     }
} 