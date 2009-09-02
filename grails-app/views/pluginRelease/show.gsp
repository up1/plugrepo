
<%@ page import="net.jetztgrad.plugrepo.PluginRelease" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show PluginRelease</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">PluginRelease List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New PluginRelease</g:link></span>
        </div>
        <div class="body">
            <h1>Show PluginRelease</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:pluginReleaseInstance, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Name:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:pluginReleaseInstance, field:'name')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Repository:</td>
                            
                            <td valign="top" class="value"><g:link controller="repository" action="show" id="${pluginReleaseInstance?.repository?.id}">${pluginReleaseInstance?.repository?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Plugin:</td>
                            
                            <td valign="top" class="value"><g:link controller="plugin" action="show" id="${pluginReleaseInstance?.plugin?.id}">${pluginReleaseInstance?.plugin?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Plugin Version:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:pluginReleaseInstance, field:'pluginVersion')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Grails Version:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:pluginReleaseInstance, field:'grailsVersion')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">File Token:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:pluginReleaseInstance, field:'fileToken')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Default Version:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:pluginReleaseInstance, field:'defaultVersion')}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${pluginReleaseInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
