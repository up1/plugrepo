<div class="plugin">
	<div class="plugin-heading">
		<span class="plugin-heading">${fieldValue(bean:pluginInstance, field:'name')}</span>
	</div>
	<div class="plugin-info">
		Releases: 
		<g:set var="pluginReleases" value="${pluginInstance.releases?.unique { pluginRelease -> pluginRelease.pluginVersion }.sort { pluginRelease -> pluginRelease.pluginVersion }}" />
		<g:set var="pluginReleasesCount" value="${(-1 * Math.min(3, pluginReleases.size()))}" />
		<%-- show only the 3 latest releases --%>
		<g:each in="${pluginReleases[pluginReleasesCount..-1]}" var="pluginRelease">
			<%-- TODO add links to install plugin and visualize whether plugin already is installed --%>
			${pluginRelease?.pluginVersion} 
		</g:each>
	</div>
</div>
