<html>
	<head>
		<title><g:layoutTitle default="Grails" /></title>
		<link rel="stylesheet" href="${resource(dir:'css',file:'plugin.css')}" />
		<link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
		<link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
		<g:layoutHead />
		<g:javascript library="application" />				
    </head>
    <body>
		<div id="spinner" class="spinner" style="display:none;">
			<img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
		</div>	
		<div class="logo"><img src="${resource(dir:'images',file:'grails_logo.jpg')}" alt="Grails" /></div>
		<%--
		<g:render template="search"/>
		--%>
		<%--
		<div class="search">
			<g:form url="[controller:'plugin', action:'search']" id="searchableForm" name="searchableForm" method="get">
		        <g:textField class="search" name="q" value="${params.q}" size="20"/> <input type="submit" value="Search" />
		    </g:form>
		</div>
		--%>
		<div class="nav">
			<span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
			<span class="menuButton"><g:link class="create" controller="plugin" action="upload">Upload Plugin</g:link></span>
			<span class="menuButton"><g:link class="create" controller="repository" action="scan">Update Plugins from Repository</g:link></span>
			<span class="menuButton"><g:link class="create" controller="plugin" action="browse">Install Plugin from Repository</g:link></span>
			<span class="menuButton"><g:link class="list" controller="plugin" action="list">List</g:link></span>
			<span class="menuButton"><g:link class="list" controller="plugin" action="metadata">Plugin metadata</g:link></span>
		</div>
		<div class="centerbox">
			<h1>Plugin Repository</h1>
			<g:layoutBody />
		</div>
	</body>	
</html>