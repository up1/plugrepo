class PlugRepoUrlMappings {
	static mappings = {
		"/local/.plugin-meta/plugins-list.xml" {
			controller = "plugin"
			action = "metadata"
			includeUpstream = false
		}
		"/proxied/.plugin-meta/plugins-list.xml" {
			controller = "plugin"
			action = "metadata"
			includeUpstream = true
		}
		"/plugin/download/$plugin/$version?" {
			controller = "plugin"
			action = "download"
		}
		"/plugin/$action?/$id?"{
			controller = "plugin"
		}
	}
}
