class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		//"/"(view:"/index")
        "/"(controller:"guestbookEntry")
		"500"(view:'/error')
        "404"(view:'/notfound')
	}
}
