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
				def pluginXml = getPluginXml(repo)
				def pluginList = new XmlSlurper().parse(pluginXml)
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

	Reader getPluginXml(def repo) {
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
		
		return new InputStreamReader(response)
	}

	List createPluginsFromXml(def repo, def pluginList) {
		def newPlugins = []
		def newPluginReleases = []
		
		log.info "parsing plugin list of repository ${repo.name}"
		
//		Plugin.withSession { session ->
//			Plugin.withTransaction {

				pluginList.plugin*.release*.each { elRelease ->
					def elPlugin = elRelease.parent()
					def name = elPlugin.@name?.text()
					def version = elRelease.@version?.text()
					def documentationUrl = elRelease.documentation?.text()
					def downloadUrl = elRelease.file?.text()

					//log.info "repository ${repo.name}: $name $version"

					// get or create plugin
					def plugin = Plugin.findByName(name)
					if (!plugin) {
						def author = elRelease.author?.text()
						def description = elRelease.@description?.text()
						
						plugin = new Plugin(name: name, 
											author: author,
											description: description)
						if (plugin.save()) {
							newPlugins << plugin
							log.info "found new plugin $name ($author)"
						}
						else {
							log.error "failed to save new plugin $name: ${plugin.errors}"
						}
					}
					
					// get or create plugin release
					//def pluginRelease = Plugin.findByNameAndPluginVersion(name, version)
					def pluginRelease = PluginRelease.find("from PluginRelease as p where p.name = ? and p.pluginVersion = ? and p.repository = ?", [ name, version, repo ])
					if (!pluginRelease) {

						pluginRelease = new PluginRelease(name: name, 
							pluginVersion: version,
							plugin: plugin,
							repository: repo,
							documentationUrl: documentationUrl,
							downloadUrl: downloadUrl)
						repo.addToReleases(pluginRelease)
						plugin.addToReleases(pluginRelease)
						if (repo.save()
							&& plugin.save()) {
							newPluginReleases << pluginRelease
							log.info "found new plugin release $name $version"
						}
						else {
							log.error "failed to save new plugin release $name $version: ${pluginRelease?.errors}, ${repo?.errors}, ${plugin?.errors}"
						}
					}
				}
//			}
//			session.flush()
//		}
		
		log.info "finished parsing plugin list of repository ${repo.name} (${newPlugins.size()} new)"
		
		return newPluginReleases
	}
}
