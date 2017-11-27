<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="auditReport/auditor/saveEdit.do" modelAttribute="auditReport">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="raffle" />
		<form:hidden path="moment" />
		<form:hidden path="auditor" />
	
		<acme:textarea code="auditreport.text" path="text"/>
		<br/>
		FINAL? <form:checkbox  path="finalMode"/> <br/>
		
		<spring:message code="actor.save" var="actorSaveHeader"/>
		<spring:message code="actor.cancel" var="actorCancelHeader"/>
		<input type="submit" class="btn btn-primary" name="save" value="${actorSaveHeader}" />
		<input onclick="window.location = 'raffle/list.do'" class="btn btn-warning" type="button" name="cancel" value="${actorCancelHeader}"/>
		

</form:form>





