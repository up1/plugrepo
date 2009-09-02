

package net.jetztgrad.plugrepo

class PluginReleaseController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ pluginReleaseInstanceList: PluginRelease.list( params ), pluginReleaseInstanceTotal: PluginRelease.count() ]
    }

    def show = {
        def pluginReleaseInstance = PluginRelease.get( params.id )

        if(!pluginReleaseInstance) {
            flash.message = "PluginRelease not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ pluginReleaseInstance : pluginReleaseInstance ] }
    }

    def delete = {
        def pluginReleaseInstance = PluginRelease.get( params.id )
        if(pluginReleaseInstance) {
            try {
                pluginReleaseInstance.delete(flush:true)
                flash.message = "PluginRelease ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "PluginRelease ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "PluginRelease not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def pluginReleaseInstance = PluginRelease.get( params.id )

        if(!pluginReleaseInstance) {
            flash.message = "PluginRelease not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ pluginReleaseInstance : pluginReleaseInstance ]
        }
    }

    def update = {
        def pluginReleaseInstance = PluginRelease.get( params.id )
        if(pluginReleaseInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(pluginReleaseInstance.version > version) {
                    
                    pluginReleaseInstance.errors.rejectValue("version", "pluginRelease.optimistic.locking.failure", "Another user has updated this PluginRelease while you were editing.")
                    render(view:'edit',model:[pluginReleaseInstance:pluginReleaseInstance])
                    return
                }
            }
            pluginReleaseInstance.properties = params
            if(!pluginReleaseInstance.hasErrors() && pluginReleaseInstance.save()) {
                flash.message = "PluginRelease ${params.id} updated"
                redirect(action:show,id:pluginReleaseInstance.id)
            }
            else {
                render(view:'edit',model:[pluginReleaseInstance:pluginReleaseInstance])
            }
        }
        else {
            flash.message = "PluginRelease not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def pluginReleaseInstance = new PluginRelease()
        pluginReleaseInstance.properties = params
        return ['pluginReleaseInstance':pluginReleaseInstance]
    }

    def save = {
        def pluginReleaseInstance = new PluginRelease(params)
        if(!pluginReleaseInstance.hasErrors() && pluginReleaseInstance.save()) {
            flash.message = "PluginRelease ${pluginReleaseInstance.id} created"
            redirect(action:show,id:pluginReleaseInstance.id)
        }
        else {
            render(view:'create',model:[pluginReleaseInstance:pluginReleaseInstance])
        }
    }
}
