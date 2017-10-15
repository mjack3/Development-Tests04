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
	<acme:textbox code="prize.name" path="name" />
	<br />
	<acme:textbox code="prize.description" path="description" />
	<br />
	<display:table name="properties" id="row" pagesize="8"
		class="table table-over">

		<display:column property="name" title="${name}" sortable="false" />
		<display:column>
			<jstl:if test="${!prizeForm.properties.contains(row)}">
				<a
					href="prize/manager/addProperty.do?prizeId=${prizeForm.id}&propertyId=${row.id}">
					<spring:message code="prize.addProperty" />
				</a>
			</jstl:if>
			<jstl:if test="${prizeForm.properties.contains(row)}">
				<a
					href="prize/manager/removeProperty.do?prizeId=${prizeForm.id}&propertyId=${row.id}">
					<spring:message code="prize.removeProperty" />
				</a>
			</jstl:if>


		</display:column>



	</display:table>
	<a href="property/manager/create.do?prizeId=${prizeForm.id}"> <spring:message
			code="prize.createProperty" />
	</a>
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