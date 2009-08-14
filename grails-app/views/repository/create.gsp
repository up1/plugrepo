
<%@ page import="net.jetztgrad.plugrepo.Repository" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create Repository</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Repository List</g:link></span>
        </div>
        <div class="body">
            <h1>Create Repository</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${repositoryInstance}">
            <div class="errors">
                <g:renderErrors bean="${repositoryInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:repositoryInstance,field:'name','errors')}">
                                    <input type="text" maxlength="50" id="name" name="name" value="${fieldValue(bean:repositoryInstance,field:'name')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="repositoryURL">Repository URL:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:repositoryInstance,field:'repositoryURL','errors')}">
                                    <input type="text" id="repositoryURL" name="repositoryURL" value="${fieldValue(bean:repositoryInstance,field:'repositoryURL')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description">Description:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:repositoryInstance,field:'description','errors')}">
                                    <textarea rows="5" cols="40" name="description">${fieldValue(bean:repositoryInstance, field:'description')}</textarea>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="userName">User Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:repositoryInstance,field:'userName','errors')}">
                                    <input type="text" id="userName" name="userName" value="${fieldValue(bean:repositoryInstance,field:'userName')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="password">Password:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:repositoryInstance,field:'password','errors')}">
                                    <input type="text" id="password" name="password" value="${fieldValue(bean:repositoryInstance,field:'password')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="enabled">Enabled:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:repositoryInstance,field:'enabled','errors')}">
                                    <g:checkBox name="enabled" value="${repositoryInstance?.enabled}" ></g:checkBox>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="priority">Priority:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:repositoryInstance,field:'priority','errors')}">
                                    <input type="text" id="priority" name="priority" value="${fieldValue(bean:repositoryInstance,field:'priority')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="type">Type:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:repositoryInstance,field:'type','errors')}">
                                    <g:select  from="${net.jetztgrad.plugrepo.RepositoryType?.values()}" value="${repositoryInstance?.type}" name="type" ></g:select>
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
