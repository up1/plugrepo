

package net.jetztgrad.plugrepo

class RepositoryController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ repositoryInstanceList: Repository.list( params ), repositoryInstanceTotal: Repository.count() ]
    }

    def show = {
        def repositoryInstance = Repository.get( params.id )

        if(!repositoryInstance) {
            flash.message = "Repository not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ repositoryInstance : repositoryInstance ] }
    }

    def delete = {
        def repositoryInstance = Repository.get( params.id )
        if(repositoryInstance) {
            try {
                repositoryInstance.delete(flush:true)
                flash.message = "Repository ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Repository ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "Repository not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def repositoryInstance = Repository.get( params.id )

        if(!repositoryInstance) {
            flash.message = "Repository not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ repositoryInstance : repositoryInstance ]
        }
    }

    def update = {
        def repositoryInstance = Repository.get( params.id )
        if(repositoryInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(repositoryInstance.version > version) {
                    
                    repositoryInstance.errors.rejectValue("version", "repository.optimistic.locking.failure", "Another user has updated this Repository while you were editing.")
                    render(view:'edit',model:[repositoryInstance:repositoryInstance])
                    return
                }
            }
            repositoryInstance.properties = params
            if(!repositoryInstance.hasErrors() && repositoryInstance.save()) {
                flash.message = "Repository ${params.id} updated"
                redirect(action:show,id:repositoryInstance.id)
            }
            else {
                render(view:'edit',model:[repositoryInstance:repositoryInstance])
            }
        }
        else {
            flash.message = "Repository not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def repositoryInstance = new Repository()
        repositoryInstance.properties = params
        return ['repositoryInstance':repositoryInstance]
    }

    def save = {
        def repositoryInstance = new Repository(params)
        if(!repositoryInstance.hasErrors() && repositoryInstance.save()) {
            flash.message = "Repository ${repositoryInstance.id} created"
            redirect(action:show,id:repositoryInstance.id)
        }
        else {
            render(view:'create',model:[repositoryInstance:repositoryInstance])
        }
    }
}
