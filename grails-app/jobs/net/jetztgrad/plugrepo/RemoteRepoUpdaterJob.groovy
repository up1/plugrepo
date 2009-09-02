package net.jetztgrad.plugrepo


class RemoteRepoUpdaterJob {
	// update every 4 hours
	static triggers = {
		cron name:'PeriodicRemoteRepoUpdater', startDelay:10000, cronExpression: '0 0 0/4 * * ?'
	}
	def group = 'Repository'

	def remoteRepositoryService

    def execute() {
		// execute task
		log.info "updating remote repositories"
		log.info "TODO reenable task"
		//remoteRepositoryService.update()
    }
}
