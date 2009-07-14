package net.jetztgrad.plugrepo

import java.io.InputStream;
import java.io.OutputStream;

import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;

import net.jetztgrad.plugrepo.Plugin;
import net.jetztgrad.plugrepo.PluginVersion;
import net.jetztgrad.plugrepo.Repository;

class PluginController {
	
	def storageService

    def index = {
	}
	
	def list = {
		def plugins = Plugin.list();
		def pluginInstanceTotal = Plugin.count();
		
		[plugins:plugins, pluginInstanceTotal: pluginInstanceTotal]
	}

	def metadata = {
		def plugins = Plugin.list();
		
		render contentType:"text/xml", model:[plugins:plugins]
	}
	
	def download = {
		Plugin plugin = Plugin.getByName(params.plugin)
		
		if (plugin == null) {
			// plugin not found
			response.sendError 404
			return
		}

		PluginVersion plugVersion = plugin.defaultVersion
		
		if (params.version) {
			plugVersion = PluginVersion.findByVersionString(params.version)
		}
		
		if (plugVersion == null) {
			// plugin version not found
			response.sendError 404
			return
		}

		String token = plugVersion.fileToken;
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
	
	protected isZip(def multipartFile) {
		switch (multipartFile.contentType) {
		case 'application/zip':
		case 'application/x-zip-compressed':
			return true;
		}
		return false
	}
	
	def upload = {
		def pluginFile = request.getFile("pluginFile")
		String error
		if (pluginFile == null) {
			error = "Invalid file!"
		}
		else if (!isZip(pluginFile)) {
			println pluginFile.contentType
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
		String pluginName
		String versionString
		InputStream inp
		try {
			// read plugin .zip
			inp = storageService.readFile(token)
			ZipInputStream zin = new ZipInputStream(inp)

			pluginName = fileName
			versionString = "1.0"
			
			// find plugin.xml
			ZipEntry entry
			while ((entry = zin.getNextEntry())) {
				if (entry.name == 'plugin.xml') {
					// TODO read and parse plugin.xml
					break;
				}
			}
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
		
		if (pluginName && versionString) {
			Plugin plugin = Plugin.findByName(pluginName)
			if (plugin == null) {
				plugin = new Plugin(name: pluginName)
			}

			// create new pluginVersion
			// TODO check for duplicates
			PluginVersion pluginVersion = new PluginVersion(versionString: versionString, defaultVersion: true, fileToken: token)
			plugin.addToVersions(pluginVersion)
			if (!plugin.defaultVersion) {
				plugin.defaultVersion = versionString
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
