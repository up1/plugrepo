class UrlMappings {
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
		"/plugin/download/$plugin?/$version?" {
			controller = "plugin"
			action = "download"
		}
		"/plugin/$action?/$plugin?/$version?"{
			controller = "plugin"
		}
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		"/"(controller:"plugin", action:"index")
		"500"(view:'/error')
	}
}
