package net.jetztgrad.plugrepo

class Repository {
	String name
	String description
	String repositoryURL
	String userName
	String password
	Integer priority

    static constraints = {
		userName(nullable:true)
		password(nullable:true)
    }
}
