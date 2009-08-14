
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="plugin" />
        <title>Plugin List</title>
    </head>
    <body>
        <div class="nav">
			<span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
			<span class="menuButton"><g:link class="create" action="create">New Plugin</g:link></span>
			<span class="menuButton"><g:link controller="plugin" action="list">List</g:link></span>
			<span class="menuButton"><g:link class="create" controller="plugin" action="metadata">Plugin metadata</g:link></span>
		</div>
		<div class="body">
			<h1>Plugin Repository</h1>
			<g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${plugin}">
				<g:renderErrors bean="${plugin}" as="list" />
			</g:hasErrors>
			<p/>
			<div class="note">
				<h3>How to use</h3>
				<p>
					In your Grails project, create a file <span class="code">grails-app/conf/BuildConfig.groovy</span> with the following contents:
				</p>
				<div class="code">
					<pre>
						grails.plugin.repos.discovery.myRepository="http://localhost:8080/plugrepo/local"
						grails.plugin.repos.resolveOrder=['myRepository','core']
					</pre>
				</div>
			</div>
			
				
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
