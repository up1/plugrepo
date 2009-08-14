
<%@ page import="net.jetztgrad.plugrepo.Plugin" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show Plugin</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Plugin List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Plugin</g:link></span>
        </div>
        <div class="body">
            <h1>Show Plugin</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:pluginInstance, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Name:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:pluginInstance, field:'name')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Repository:</td>
                            
                            <td valign="top" class="value"><g:link controller="repository" action="show" id="${pluginInstance?.repository?.id}">${pluginInstance?.repository?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Description:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:pluginInstance, field:'description')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Author:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:pluginInstance, field:'author')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Documentation:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:pluginInstance, field:'documentation')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Default Version:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:pluginInstance, field:'defaultVersion')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">File Token:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:pluginInstance, field:'fileToken')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Grails Version:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:pluginInstance, field:'grailsVersion')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Plugin Version:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:pluginInstance, field:'pluginVersion')}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${pluginInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
