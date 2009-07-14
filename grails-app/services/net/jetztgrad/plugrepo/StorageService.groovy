package net.jetztgrad.plugrepo

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.OutputStream;

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.web.context.ServletContextHolder

import org.springframework.web.multipart.MultipartFile

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
    String storeFile(InputStream stream, String name) {
		String token = getNextToken(name)
		String path = getPathForToken(token)
		
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
	String storeFile(MultipartFile file) {
		String token = getNextToken(file.originalFilename)
		String path = getPathForToken(token)
		
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
	InputStream readFile(String token) {
		String path = getPathForToken(token)
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
	def deleteFile(String token) {
		String path = getPathForToken(token)
		File file = new File(path)
		if (file.exists()) {
			return file.delete()
		}
		
		return false
	}
	
	String getFileName(String token) {
		String path = getPathForToken(token)
		File file = new File(path)
		return file.name
	}
	
	int getFileSize(String token) {
		String path = getPathForToken(token)
		File file = new File(path)
		return file.size
	}
	
	protected String getPathForToken(String token) {
		"${dataDirectory}/${token}"
	}
	
	protected String getNextToken(String fileName) {
		return fileName
	}
	
	String getDataDirectory() {
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
