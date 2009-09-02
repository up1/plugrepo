<%@ page contentType="text/xml; charset=UTF-8" %>

<%-- TODO get reversion --%>
<plugins revision="9014">
	<g:each in="${plugins}" status="i" var="plugin">
		<plugin latest-release="${fieldValue(bean:plugin?.defaultRelease, field:'pluginVersion')}" name="${fieldValue(bean:plugin, field:'name')}">
			<g:each in="${plugin?.releases}" status="j" var="pluginRelease">
				<release tag="REL_${fieldValue(bean:pluginRelease, field:'pluginVersion')}" type="svn" version="${fieldValue(bean:pluginRelease, field:'pluginVersion')}">
					<title>${fieldValue(bean:plugin, field:'name')}</title>
					<author>${fieldValue(bean:plugin, field:'author')}</author>
					<authoremail>${fieldValue(bean:plugin, field:'author')}</authoremail>
					<description>${fieldValue(bean:plugin, field:'description')}</description>
					<documentation><g:createLink controller="plugin" action="docs" absolute="true" params="[plugin:fieldValue(bean:pluginRelease, field:'name'), version:fieldValue(bean:pluginRelease, field:'pluginVersion')]"/></documentation>
					<file><g:createLink controller="plugin" action="download" absolute="true" params="[plugin:fieldValue(bean:pluginRelease, field:'name'), version:fieldValue(bean:pluginRelease, field:'pluginVersion')]"/></file>
				</release>
			</g:each>
		</plugin>
	</g:each>
</plugins>