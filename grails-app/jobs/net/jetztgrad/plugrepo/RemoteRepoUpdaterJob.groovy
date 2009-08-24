package net.jetztgrad.plugrepo


class RemoteRepoUpdaterJob {
    def timeout = 5000l // execute job once in 5 seconds

	// update every 4 hours
	def cronExpression = "0 0 0/4 * * ?"
	def group = "Repository"

	def remoteRepositoryService

    def execute() {
        // execute task
		remoteRepositoryService.update()
    }
}
