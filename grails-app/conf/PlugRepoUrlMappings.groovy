class PlugRepoUrlMappings {
	static mappings = {
		"/plugin/.plugin-meta/plugins-list.xml" {
			controller = "plugin"
			action = "metadata"
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
