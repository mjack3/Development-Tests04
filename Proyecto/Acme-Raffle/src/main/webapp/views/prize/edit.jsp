<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="${requestParam}" modelAttribute="prizeForm">

	<form:hidden path="raffleId" />
	<form:hidden path="id" />
	<form:hidden path="prizeId" />
	<form:hidden path="version" />
	<form:hidden path="allProperties" />
	<form:hidden path="comments" />
	<acme:textbox code="prize.name" path="name" />
	<br />
	<acme:textbox code="prize.description" path="description" />
	<br />

	
	<br />


	<!-- Buttons -->

	<acme:submit name="save" code="prize.save" />
	<button type="submit"
		onclick="return confirm('Are you sure you want to continue')"
		name="delete" class="btn btn-primary">
		<spring:message code="prize.delete" />
	</button>


	<acme:cancel url="/raffle/list.do" code="prize.cancel" />


</form:form>