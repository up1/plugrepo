class PluginTagLib {
	static namespace = "plug"
	
	def latestReleases = { attrs, body ->
		def maxReleases = attrs.max?.toInteger() ?: 3
		if (attrs?.all?.toBoolean()) {
			maxReleases = Integer.MAX_VALUE
		}
		def plugin = attrs.plugin
		
		// get plugin releases, filter and sort them
		def pluginReleases=plugin?.releases?.unique { pluginRelease -> pluginRelease.pluginVersion }.sort { pluginRelease -> pluginRelease.pluginVersion }
		if (!pluginReleases) {
			pluginReleases = []
		}
		// determine lower range
		def releasesLowerRange=(-1 * Math.min(maxReleases, pluginReleases.size()))
		def latestPluginReleases = pluginReleases[releasesLowerRange..-1]
		def var = attrs.var ? attrs.var : "pluginRelease"
		
		latestPluginReleases?.eachWithIndex { pluginRelease, index ->
			def params = [:]
			params.putAll attrs
			['var', 'status'].each { params.remove it }
			// set plugin release
			params[(var)] = pluginRelease
			// set status
			if (attrs?.status) {
				params[(attrs?.status)] = index
			}
			
			out << body(params)
	    }
	}
}
