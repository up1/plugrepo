
<%@ page import="net.jetztgrad.plugrepo.Plugin" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit Plugin</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Plugin List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Plugin</g:link></span>
        </div>
        <div class="body">
            <h1>Edit Plugin</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${pluginInstance}">
            <div class="errors">
                <g:renderErrors bean="${pluginInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${pluginInstance?.id}" />
                <input type="hidden" name="version" value="${pluginInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pluginInstance,field:'name','errors')}">
                                    <input type="text" id="name" name="name" value="${fieldValue(bean:pluginInstance,field:'name')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="repository">Repository:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pluginInstance,field:'repository','errors')}">
                                    <g:select optionKey="id" from="${net.jetztgrad.plugrepo.Repository.list()}" name="repository.id" value="${pluginInstance?.repository?.id}" noSelection="['null':'']"></g:select>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description">Description:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pluginInstance,field:'description','errors')}">
                                    <input type="text" id="description" name="description" value="${fieldValue(bean:pluginInstance,field:'description')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="author">Author:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pluginInstance,field:'author','errors')}">
                                    <input type="text" id="author" name="author" value="${fieldValue(bean:pluginInstance,field:'author')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="documentation">Documentation:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pluginInstance,field:'documentation','errors')}">
                                    <input type="text" id="documentation" name="documentation" value="${fieldValue(bean:pluginInstance,field:'documentation')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="defaultVersion">Default Version:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pluginInstance,field:'defaultVersion','errors')}">
                                    <g:checkBox name="defaultVersion" value="${pluginInstance?.defaultVersion}" ></g:checkBox>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fileToken">File Token:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pluginInstance,field:'fileToken','errors')}">
                                    <input type="text" id="fileToken" name="fileToken" value="${fieldValue(bean:pluginInstance,field:'fileToken')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="grailsVersion">Grails Version:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pluginInstance,field:'grailsVersion','errors')}">
                                    <input type="text" id="grailsVersion" name="grailsVersion" value="${fieldValue(bean:pluginInstance,field:'grailsVersion')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="pluginVersion">Plugin Version:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:pluginInstance,field:'pluginVersion','errors')}">
                                    <input type="text" id="pluginVersion" name="pluginVersion" value="${fieldValue(bean:pluginInstance,field:'pluginVersion')}"/>
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
