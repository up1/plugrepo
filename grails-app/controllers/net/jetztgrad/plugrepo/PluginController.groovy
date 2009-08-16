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
		def plugins = Plugin.list();
		def pluginInstanceTotal = Plugin.count();
		
		[pluginInstanceList:plugins, pluginInstanceTotal: pluginInstanceTotal]
	}
	
	def list = {
		def plugins = Plugin.list();
		def pluginInstanceTotal = Plugin.count();
		
		[pluginInstanceList:plugins, pluginInstanceTotal: pluginInstanceTotal]
	}

	def metadata = {
		def plugins = Plugin.list();
		def pluginTotal = Plugin.count();
		
		if (params.includeUpstream) {
			// TODO include (cached) meta data from upstream repos
		}
		
		[plugins:plugins, pluginTotal:pluginTotal]
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
		
		if (params.version) {
			plugin = Plugin.findByNameAndPluginVersion(params.plugin, params.version)
		}
		else {
			plugin = Plugin.findByNameAndDefaultVersion(params.plugin, true)
		}
		
		if (plugin == null) {
			// plugin version not found
			response.sendError 404
			return
		}
		
		[plugin:plugin]
	}
	
	def download = {
		Plugin plugin
		
		if (params.version) {
			plugin = Plugin.findByNameAndPluginVersion(params.plugin, params.version)
		}
		else {
			plugin = Plugin.findByNameAndDefaultVersion(params.plugin, true)
		}
		
		if (plugin == null) {
			// plugin version not found
			response.sendError 404
			return
		}

		String token = plugin.fileToken;
		InputStream inp = storageService.readFile(token)
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
	
	def upload = {
		def repositories = Repository.findAllByType(RepositoryType.INTERNAL)
		def preselectedRepository = Repository.findByName(Repository.LOCAL)
		if (!preselectedRepository) {
			preselectedRepository = repositories?.size() ? repositories[0] : null
		}
		[repositories: repositories, preselectedRepository: preselectedRepository ]
	}
	
	def store = {
		def pluginFile
		try {
			pluginFile = request.getFile("pluginFile")
		}
		catch (ex) {
			
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
		
		if (error) {
			flash.message = error
			render(view:"index", model:[plugin:null])
		}
		
		// store file
		String fileName = pluginFile.originalFilename
		def token = storageService.storeFile(pluginFile)
		
		if (token == null) {
			flash.message = "failed to store file!"
			render(view:"index", model:[plugin:null])
		}
		
		// inspect plugin xml
		def pluginXml = storageService.readPluginXml(token)
		if (pluginXml) {
			String pluginName = pluginXml.@name
			String pluginVersion = pluginXml.@version
			String grailsVersion = pluginXml.@grailsVersion
			String author = pluginXml.author
			String description = pluginXml.description
		
			Plugin plugin = Plugin.findByNameAndPluginVersion(pluginName, pluginVersion)
			if (plugin == null) {
				def count = Plugin.findAllByName(pluginName)
				boolean defaultVersion = false
				if (count == 0) {
					defaultVersion = true
				}
				plugin = new Plugin(name: pluginName, pluginVersion: pluginVersion, grailsVersion: grailsVersion, fileToken: token, defaultVersion:defaultVersion, author:author, description:description)
			}

			if (plugin.save(flush:true)) {			
				flash.message = "successfully uploaded plugin"
			}
			else {
				flash.message = "failed to upload plugin"
			}
			render(view:"index", model:[plugin: plugin, pluginVersion: pluginVersion])
		}
		else {
			flash.message = "Invalid plugin contents!"
			render(view:"index")
		}
	}
}
