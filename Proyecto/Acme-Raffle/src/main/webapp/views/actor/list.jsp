<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<security:authorize access="isAuthenticated()">

	<display:table class="table table-over" name="actors" requestURI="actor/list.do" id="row" pagesize="5" keepStatus="true">
	
		<spring:message code="actor.actorName" var ="h" />
		<display:column property="name" title="${h }" />
	
		<spring:message code="actor.surname" var ="h" />
		<display:column property="surname" title="${h }" />
		
		<spring:message code="actor.email" var ="h" />
		<display:column property="email" title="${h }" />
		
		<spring:message code="actor.phone" var ="h" />
		<display:column property="phone" title="${h }" />
		
		<spring:message code="actor.postalAddress" var ="h" />
		<display:column property="postal" title="${h }" />
		
		<spring:message code="actor.city" var ="h" />
		<display:column property="city" title="${h }" />
		
		<spring:message code="actor.country" var ="h" />
		<display:column property="country" title="${h }" />
		
		
<display:column>
	<a href="comment/createOnActor.do?actorId=${row.id }"> <spring:message code="comment.action" /> </a>
</display:column>

<display:column>
<jstl:if test="${not empty row.comments }">
	<a href="comment/list.do?q=${row.id }"> <spring:message code="comment.action.list" /> </a>
</jstl:if>
</display:column>
		
		
</display:table>


</security:authorize>





