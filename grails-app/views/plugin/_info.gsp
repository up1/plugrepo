<div class="plugin">
	<div class="plugin-heading">
		<span class="plugin-heading">${fieldValue(bean:pluginInstance, field:'name')}</span>
		<g:remoteLink controller="plugin" action="info" params="[plugin:pluginInstance]" update="plugin-info-${pluginInstance.name}">Details</g:remoteLink>
	</div>
	<div class="plugin-info" id="plugin-info-${pluginInstance.name}">
		Releases: 
		<plug:latestReleases plugin="${pluginInstance}" var="rel" status="index" max="3">
			<g:set var="isSnapshot" value="${rel?.pluginVersion?.endsWith('SNAPSHOT')}"/>
			<g:set var="snapshotHint" value="Snapshot-Release: may change any time"/>
			<g:set var="cssClass" value="plugin-release ${if (isSnapshot) 'plugin-release plugin-release-snapshot' }"/>
			<g:set var="title" value="${isSnapshot ? snapshotHint : 'Release ' + rel?.pluginVersion }"/>
			<g:remoteLink controller="plugin" action="releaseinfo" params="[plugin:pluginInstance, version:rel?.pluginVersion]" class="${cssClass}" title="$title" update="plugin-releaseinfo-${pluginInstance.name}">${rel?.pluginVersion}</g:remoteLink>
		</plug:latestReleases>
		<div class="plugin-info" id="plugin-release-${pluginInstance.name}">
		</div>
	</div>
</div>
