package net.jetztgrad.plugrepo

class RemoteRepositoryService {

    boolean transactional = true; //false

    def update() {
		def updatedPlugins = [:]
		// get 
		def remoteRepos = []
//		Repository.withTransaction {
			remoteRepos = Repository.findAllByTypeNotEqual(RepositoryType.INTERNAL);
//		}
		
		remoteRepos.each { repo ->
			updatedPlugins[repo.name] = []
			
			log.debug "updating plugins for repository ${repo.name}"
			
			try {
				// TODO use Groovy HTTPBuilder
				def urlString = repo.repositoryURL
				if (repo.type == RepositoryType.SUBVERSION) {
					urlString = "${repo.repositoryURL}/.plugin-meta/plugins-list.xml"
				}
				URL url = new URL(urlString)
				URLConnection conn = url.openConnection()
				conn.setDoInput(true)
				conn.setDoOutput(false)
				int timeout = 60 *1000
				conn.setConnectTimeout(timeout)
				conn.connect()
				def response = conn.getInputStream()
				def pluginList = new XmlSlurper().parse(response)
				def newPlugins = createPluginsFromXml(repo, pluginList)
				
				updatedPlugins[repo.name] << newPlugins
				
				log.info "found ${newPlugins.size()} new plugins for repository ${repo.name}"
			}
			catch (Throwable t) {
				log.error("failed to update plugins for repository ${repo.name}: ${t.message}")
				log.debug('details: ', t)
				t.printStackTrace()
			}
			
		}
		return updatedPlugins
    }

	protected List createPluginsFromXml(def repo, def pluginList) {
		def newPlugins = []
		
		log.info "parsing plugin list of repository ${repo.name}"
		
//		Plugin.withSession { session ->
//			Plugin.withTransaction {

				pluginList.plugin*.release*.each { release ->
					def plugin = release.parent()
					def name = plugin.@name?.text()
					def version = release.@version?.text()

					log.info "repository ${repo.name}: $name $version"

					//def pluginRelease = Plugin.findByNameAndPluginVersion(name, version)
					def pluginRelease = Plugin.find("from Plugin as p where p.name = ? and p.pluginVersion = ? and p.repository = ?", [ name, version, repo ])
					if (!pluginRelease) {
						def author = release.author?.text()
						def description = release.@description?.text()

						pluginRelease = new Plugin(name: name, 
							pluginVersion: version,
							repository: repo,
							author: author,
							description: description)
						pluginRelease = pluginRelease.merge()
						if (pluginRelease.save()) {
							newPlugins << pluginRelease
							log.info "found new plugin $name $version ($author)"
						}
						else {
							log.error "failed to save new plugin $name $version: ${pluginRelease.errors}"
						}
					}
					// TODO remove me
					else {
						newPlugins << pluginRelease
						log.debug "found existing plugin $name $version"
					}
				}
//			}
//			session.flush()
//		}
		
		log.info "finished parsing plugin list of repository ${repo.name} (${newPlugins.size()} new)"
		
		return newPlugins
	}
}
