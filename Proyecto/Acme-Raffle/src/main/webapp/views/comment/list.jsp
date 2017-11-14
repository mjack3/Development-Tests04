<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table class="table table-over" name="comments" id="row" requestURI="comment/list.do">
	
	<spring:message code="comment.text" var="h" />
	<display:column property="text" title="${h }" />
	
	<spring:message code="comment.rating" var="h" />
	<display:column property="rating" title="${h }" />
	
	<spring:message code="comment.momment" var="h" />
	<display:column property="momment" title="${h }" />
	
</display:table>
