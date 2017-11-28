<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table class="table table-over" name="comments" id="row" pagesize="12" requestURI="${requestURI }">
	
	<spring:message code="comment.text" var="h" />
	<display:column property="text" title="${h }" />
	
	<spring:message code="comment.rating" var="h" />
	<display:column property="rating" title="${h }" />
	
	<spring:message code="comment.moment" var="h" />
	<display:column format="{0,date,dd/MM/yyyy HH:mm}" property="moment" title="${h }" />
	
	<security:authorize access="hasRole('ADMIN')">
	
	<display:column>
		<a href="comment/administrator/delete.do?commentId=${row.id }">
			<spring:message code="comment.delete" />
		</a>
	</display:column>
	
	</security:authorize>
	
</display:table>
