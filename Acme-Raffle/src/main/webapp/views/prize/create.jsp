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
	<form:hidden path="comments" />
	<acme:textbox code="prize.name" path="name" />
	<br />
	<acme:textbox code="prize.description" path="description" />
	<br />
	<acme:textbox code="prize.total" path="total" />
	<br />
	<acme:textbox code="prize.winners" path="winners" />
	<br />
	
	<acme:select multiple="true" items="${propertiesList}" itemLabel="name" code="prize.properties" path="propertiesSelected"/>
	


	<!-- Buttons -->

	<acme:submit name="save" code="prize.save" />


	<acme:cancel url="/raffle/list.do" code="prize.cancel" />


</form:form>