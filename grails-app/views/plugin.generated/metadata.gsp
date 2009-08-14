<%@ page contentType="text/xml; charset=UTF-8" %>

<%-- TODO get reversion --%>
<plugins revision="9014">
	<g:each in="${plugins}" status="i" var="plugin">
		<plugin release="${fieldValue(bean:plugin, field:'pluginVersion')}" name="${fieldValue(bean:plugin, field:'name')}">
			<release tag="REL_${fieldValue(bean:plugin, field:'pluginVersion')}" type="svn" version="${fieldValue(bean:plugin, field:'pluginVersion')}">
				<title>${fieldValue(bean:plugin, field:'name')}</title>
				<author>${fieldValue(bean:plugin, field:'author')}</author>
				<authoremail>${fieldValue(bean:plugin, field:'author')}</authoremail>
				<description>${fieldValue(bean:plugin, field:'description')}</description>
				<documentation><g:createLink action="docs" absolute="true" params="[plugin:fieldValue(bean:plugin, field:'name'), version:fieldValue(bean:plugin, field:'pluginVersion')]"/></documentation>
				<file><g:createLink action="download" absolute="true" params="[plugin:fieldValue(bean:plugin, field:'name'), version:fieldValue(bean:plugin, field:'pluginVersion')]"/></file>
			</release>
		</plugin>
	</g:each>
</plugins>