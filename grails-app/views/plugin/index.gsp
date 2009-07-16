
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Plugin List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Plugin</g:link></span>
        </div>
        <div class="body">
            <h1>Plugin List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
			<g:hasErrors bean="${plugin}">
				<g:renderErrors bean="${plugin}" as="list" />
			</g:hasErrors>
			<g:link action="list">List</g:link>
			<h2>Upload Plugin</h2>
			<div class="list">
				<g:uploadForm action="upload">
					<input type="file" name="pluginFile"/><br/>
					<input type="checkbox" name="updateIfExists"/> Update if exists<br/>
					<input type="submit" value="Upload"/><br/>
				</g:uploadForm>
            </div>
        </div>
    </body>
</html>
