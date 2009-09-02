package net.jetztgrad.plugrepo

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.OutputStream;

import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.web.context.ServletContextHolder

import org.springframework.web.multipart.MultipartFile

import net.jetztgrad.plugrepo.Repository
import net.jetztgrad.plugrepo.Plugin
import net.jetztgrad.plugrepo.PluginRelease

class StorageService {

    boolean transactional = true

	/**
	* Store file.
	*
	* @param stream input stream, will be closed
	* @param name file name (excluding path)
	*
	* @return token, which can be used to read or delete the file
	*/
    String storeFile(Repository repository, InputStream stream, String name) {
		String token = getNextToken(name)
		String path = getPathForToken(repository, token)
		
		log.debug "storing file ${path}"
		
		File file = new File(path)
		OutputStream out
		try {
			out = new FileOutputStream(file)
			out << stream
		}
		finally {
			if (out) {
				try {
					out.close
				}
				finally {
					out = null
				}
			}
			if (stream) {
				try {
					stream.close
				}
				finally {
					stream = null
				}
			}
		}
		return token
    }

	/**
	* Store file.
	*
	* @param file multipart file
	*
	* @return token, which can be used to read or delete the file
	*/
	String storeFile(Repository repository, MultipartFile file) {
		String token = getNextToken(file.originalFilename)
		String path = getPathForToken(repository, token)
		
		log.debug "storing file ${path}"
		
		file.transferTo(new File(path))
		return token
	}
	
	/**
	* Store file.
	*
	* @param file multipart file
	*
	* @return token, which can be used to read or delete the file
	*/
	InputStream readFile(Repository repository, String token) {
		String path = getPathForToken(repository, token)
		File file = new File(path)
		return new FileInputStream(file)
	}
	
	/**
	* Store file.
	*
	* @param file multipart file
	*
	* @return token, which can be used to read or delete the file
	*/
	def deleteFile(Repository repository, String token) {
		String path = getPathForToken(repository, token)
		File file = new File(path)
		if (file.exists()) {
			return file.delete()
		}
		
		return false
	}
	
	boolean isZip(def multipartFile) {
		switch (multipartFile.contentType) {
		case 'application/zip':
		case 'application/x-zip-compressed':
			return true;
		}
		return false
	}
	
	/**
	* Read a plugin's plugin.xml, which contains information 
	* such as author, description, version, etc.
	*
	* @param token file token of the plugin as returned by storeFile()
	*/
	def readPluginXml(Repository repository, String token) {
		def pluginXml
		
		InputStream inp
		try {
			// read plugin .zip
			inp = readFile(repository, token)
			if (inp) {
				ZipInputStream zin = new ZipInputStream(inp)

				// find plugin.xml
				ZipEntry entry
				while ((entry = zin.getNextEntry())) {
					if (entry.name == 'plugin.xml') {
						// TODO read and parse plugin.xml
						pluginXml = new XmlSlurper().parse(zin)
						break;
					}
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
		
		return pluginXml
	}
	
	String getFileName(Repository repository, String token) {
		String path = getPathForToken(repository, token)
		File file = new File(path)
		return file.name
	}
	
	int getFileSize(Repository repository, String token) {
		String path = getPathForToken(repository, token)
		File file = new File(path)
		return file.size
	}
	
	protected String getPathForToken(Repository repository, String token) {
		String dataDirectory = getDataDirectory(repository)
		"${dataDirectory}/${token}"
	}
	
	protected String getNextToken(String fileName) {
		return fileName
	}
	
	String getDataDirectory(Repository repository) {
		// TODO get path from repository/data location
		
		def path = ConfigurationHolder.config.plugrepo.plugins.dir

		if (path != null) {
			File file = null;
			if (path instanceof File) {
				file = (File) path
			}
			else {
				path = path.toString();
				file = new File(path)
			}

			if (!file.exists()
			|| !file.isDirectory()) {
				path = null;
			}
		}

		if (path == null) {
			String docroot = ServletContextHolder.getServletContext()?.getRealPath( "/" );
			if (!docroot) {
				// use current dir
				docroot = System.getProperty('user.dir')
			}
			File root = new File(docroot)
			File dataDir = new File(root, 'WEB-INF/data/plugrepo')
			if (!dataDir.exists()) {
				dataDir.mkdirs();
			}

			path = dataDir.absolutePath
		}

		return path

	}
}
