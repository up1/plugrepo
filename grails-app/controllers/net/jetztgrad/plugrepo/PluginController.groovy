package net.jetztgrad.plugrepo

import java.io.InputStream;
import java.io.OutputStream;

import org.compass.core.engine.SearchEngineQueryParseException

import net.jetztgrad.plugrepo.Plugin;
import net.jetztgrad.plugrepo.Repository;

class PluginController {
	
	def searchableService
	def storageService

    def index = {
	}
	
	def browse = {
		params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
		params.sort = params?.sort ?: 'name'
		def plugins = Plugin.list(params);
		def pluginInstanceTotal = Plugin.count();
		
		[pluginInstanceList:plugins, pluginInstanceTotal: pluginInstanceTotal]
	}
	
	def list = {
		def plugins = Plugin.list();
		def pluginInstanceTotal = Plugin.count();
		
		[pluginInstanceList:plugins, pluginInstanceTotal: pluginInstanceTotal]
	}

	def metadata = {
		// TODO get only meta data for specific repository
		def plugins = Plugin.list();
		
		if (params.includeUpstream) {
			// TODO include (cached) meta data from upstream repos
		}
		
		[plugins:plugins]
	}
	
	/**
     * Index page with search form and results
     */
    def search = {
		if (!searchableService) {
			flash.message = "Search not available"
			return [:]
		}
        if (!params.q?.trim()) {
            return [:]
        }
        try {
            return [searchResult: searchableService.search(params.q, params)]
        } catch (SearchEngineQueryParseException ex) {
            return [parseException: true]
        }
    }
	
	def docs = {
		Plugin plugin
		PluginRelease pluginRelease
		
		if (params.plugin == null) {
			// plugin not found
			response.sendError 404
			return
		}
		
		if (params.version) {
			pluginRelease = PluginRelease.findByNameAndPluginVersion(params.plugin, params.version)
		}
		else {
			plugin = Plugin.findByName(params.plugin, true)
			if (plugin) {
				pluginRelease = plugin.defaultRelease
			}
			
			if (!pluginRelease) {
				def results = PluginRelease.findAllByName(params.plugin, [max:1, sort:"pluginVersion", order:"desc"])
				if (results) {
					pluginRelease = results[0]
				}
			}
		}
		
		if (pluginRelease == null) {
			// plugin version not found
			response.sendError 404
			return
		}
		
		if (pluginRelease.plugin?.documentation) {
			[pluginRelease:pluginRelease]
		}
		else if (pluginRelease?.documentationUrl) {
			redirect(url:pluginRelease?.documentationUrl)
		}
		else {
			[pluginRelease:pluginRelease]
		}
	}
	
	def download = {
		Plugin plugin
		PluginRelease pluginRelease
		
		if (params.plugin == null) {
			// plugin not found
			response.sendError 404
			return
		}
		
		if (params.version) {
			pluginRelease = PluginRelease.findByNameAndPluginVersion(params.plugin, params.version)
		}
		else {
			plugin = Plugin.findByName(params.plugin, true)
			if (plugin) {
				pluginRelease = plugin.defaultRelease
			}
			
			if (!pluginRelease) {
				def results = PluginRelease.findAllByName(params.plugin, [max:1, sort:"pluginVersion", order:"desc"])
				if (results) {
					pluginRelease = results[0]
				}
			}
		}
		
		if (pluginRelease == null) {
			// plugin version not found
			response.sendError 404
			return
		}

		String fileToken = pluginRelease.fileToken;
		def repo = pluginRelease.repository
		InputStream inp = storageService.readFile(repo, fileToken)
		if (inp == null) {
			// file not found
			response.sendError(404)
			return
		}
		
		try {
			response.contentType = 'application/zip'
			response.outputStream << inp
			response.outputStream.flush()
		}
		finally {
			if (inp) {
				try {
					inp.close()
				}
				finally {
					inp = null
				}
			}
		}
	}
	
	def releaseinfo = {
		def version = params.version
		render text:"relaseInfo ${version}"
	}
	
	def info = {
		Plugin plugin
		
		if (params.plugin == null) {
			// plugin not found
			response.sendError 404
			return
		}
		
		plugin = Plugin.findByName(params.plugin, true)
		if (plugin == null) {
			// plugin not found
			response.sendError 404
			return
		}
		
		if (request.xhr) {
			render template:'info', model:[pluginInstance:plugin, pluginReleases:plugin?.releases]
		}
		else {
			[pluginInstance:plugin, pluginReleases:plugin?.releases]
		}
	}
	
	def upload = {
		def repositories = Repository.findAllByType(RepositoryType.INTERNAL)
		def preselectedRepository = Repository.findByName(Repository.LOCAL)
		if (!preselectedRepository) {
			preselectedRepository = repositories?.size() ? repositories[0] : null
		}
		[repositories: repositories, preselectedRepository: preselectedRepository ]
	}
	
	def store = {
		def repositories = Repository.findAllByType(RepositoryType.INTERNAL)
		
		def pluginFile
		try {
			pluginFile = request.getFile("pluginFile")
		}
		catch (ex) {
			//ex.printStackTrace()
			// exception is handled below
		}
		String error
		if (pluginFile == null) {
			error = "Invalid file!"
		}
		else if (!storageService.isZip(pluginFile)) {
			error = "Invalid file format!<br/> Expecting .zip file containing a Grails plugin."
		}
		else if (pluginFile.empty) {
			error = "File is empty"
		}
		
		def repo = Repository.get(params?.repository?.id)
		if (!repo) {
			error = "invalid repository"
		}
		else {
			// TODO check repository type, only internal repos are valid
		}
		
		if (error) {
			flash.message = error
			render(view:"upload", model:[plugin:null, repositories: repositories, preselectedRepository: repo])
		}
		
		// store file
		// TODO store to temporary location, so the plugin won't overwrite
		// an existing plugin with the same name. As an alternative, the file path
		// should be unique for each plugin.
		String fileName = pluginFile.originalFilename
		def fileToken = storageService.storeFile(repo, pluginFile)
		
		if (fileToken == null) {
			flash.message = "failed to store file!"
			render(view:"upload", model:[plugin:null, repositories: repositories, preselectedRepository: repo])
		}
		
		// inspect plugin xml
		def pluginXml = storageService.readPluginXml(repo, fileToken)
		if (pluginXml) {
			String pluginName = pluginXml.@name
			String pluginVersion = pluginXml.@version
			String grailsVersion = pluginXml.@grailsVersion
			String author = pluginXml.author
			String description = pluginXml.description
		
			// get or create plugin
			def plugin = Plugin.findByName(pluginName)
			if (!plugin) {
				plugin = new Plugin(name: pluginName, 
									author: author,
									description: description)
				if (plugin.save()) {
					log.info "found new plugin $pluginName ($author)"
				}
				else {
					log.error "failed to save new plugin $pluginName: ${plugin.errors}"
				}
			}
			
			// get or create plugin release
			//def pluginRelease = Plugin.findByNameAndPluginVersion(name, version)
			def pluginRelease = PluginRelease.find("from PluginRelease as p where p.name = ? and p.pluginVersion = ? and p.repository = ?", [ pluginName, pluginVersion, repo ])
			if (!pluginRelease) {
				pluginRelease = new PluginRelease(name: pluginName, 
					pluginVersion: pluginVersion,
					grailsVersion: grailsVersion,
					fileToken: fileToken, 
					plugin: plugin,
					repository: repo)
				
				repo.addToReleases(pluginRelease)
				
				plugin.addToReleases(pluginRelease)
				boolean defaultVersion = params?.useAsDefaultVersion ?: false
				if (defaultVersion
					|| plugin.defaultRelease == null) {
					plugin.defaultRelease = pluginRelease
				}
				
				if (repo.save()
					&& plugin.save()) {
					flash.message = "successfully uploaded plugin $pluginName (Version $pluginVersion)"
					log.info "uploaded plugin $pluginName (Version $pluginVersion)"
				}
				else {
					// TODO delete file
					
					flash.message = "failed to save new plugin $pluginName (Version $pluginVersion)"
					log.error "failed to save new plugin $pluginName (Version $pluginVersion)"
				}
			}
			else {
				// handle existing plugins
				if (params?.updateIfExists) {
					// TODO overwrite
					
					flash.message = "Plugin $pluginName (Version $pluginVersion) has been updated (same version number)"
					log.info "plugin $pluginName (Version $pluginVersion) has been updated (same version number)"
				}
				else {
					// TODO delete file
					
					flash.message = "plugin $pluginName (Version $pluginVersion) already exists! Aborted uploading"
					log.info "plugin $pluginName (Version $pluginVersion) already exists! Aborted uploading"
				}
			}
			render(view:"upload", model:[plugin: plugin, pluginVersion: pluginVersion, pluginRelease: pluginRelease, repositories: repositories, preselectedRepository: repo])
		}
		else {
			flash.message = "Invalid plugin contents!"
			render(view:"upload", model:[repositories: repositories, preselectedRepository: repo])
		}
	}
}
