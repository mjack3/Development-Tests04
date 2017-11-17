<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


	<display:table name="properties" id="row" pagesize="8" requestURI="${requestURI}"
		class="table table-over">

		<display:column property="name" title="${name}" sortable="false" />
		<display:column>
			<jstl:if test="${!prizeProperties.contains(row)}">
				<a
					href="prize/manager/addProperty.do?prizeId=${prizeId}&propertyId=${row.id}">
					<spring:message code="prize.addProperty" />
				</a>
			</jstl:if>
			<jstl:if test="${prizeProperties.contains(row)}">
				<a
					href="prize/manager/removeProperty.do?prizeId=${prizeId}&propertyId=${row.id}">
					<spring:message code="prize.removeProperty" />
				</a>
			</jstl:if>


		</display:column>



	</display:table>
	
	<acme:cancel url="/prize/manager/list.do?q=${raffleId.id}" code="prize.ok" />
	