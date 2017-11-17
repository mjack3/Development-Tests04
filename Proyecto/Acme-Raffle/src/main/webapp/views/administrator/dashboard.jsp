<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<table class="table">
		<tr>
			<td> <spring:message code="administrator.dashboard.one" />  </td>
			<td> 
				${dashboard.get(0)}
			 </td>
		</tr>
		<tr>
			<td> <spring:message code="administrator.dashboard.two" />  </td>
			<td> ${dashboard.get(1)[0]} , ${dashboard.get(1)[1]} , ${dashboard.get(1)[2]} , ${dashboard.get(1)[3]}</td>
		</tr>
		<tr>
			<td> <spring:message code="administrator.dashboard.three" />  </td>
			<td> ${dashboard.get(2)[0]} , ${dashboard.get(2)[1]} , ${dashboard.get(2)[2]} , ${dashboard.get(2)[3]}</td>
		</tr>
		<tr>
			<td> <spring:message code="administrator.dashboard.four" />  </td>
			<td> ${dashboard.get(3)[0]} , ${dashboard.get(3)[1]} , ${dashboard.get(3)[2]} , ${dashboard.get(3)[3]}</td>
		</tr>

		<tr>
			<td> <spring:message code="administrator.dashboard.five" />  </td>
			<td> 
			<jstl:forEach var="a" items="${dashboard.get(4)}">
					${a.name} <br/>
			</jstl:forEach>
			</td>
		</tr>
	
	
	
		<!-- Dashboard Nivel B -->	
	<tr>
	 <td> <b><spring:message code="administrator.dashboard.b1" /></b></td>
	</tr>
	
	<tr>
	<td> <spring:message code="administrator.dashboard.actor" /></td>
	 <td><jstl:out value="${avgStddevNumberCommentsPerActor[0]}" />
		<jstl:out value="${avgStddevNumberCommentsPerActor[1]}" /></td>
	<tr />	
	
	<tr>
	<td><spring:message code="administrator.dashboard.raffle" /></td>

	<td><jstl:out value="${avgStddevNumberCommentsPerRaffle[0]}" />
	<jstl:out value="${avgStddevNumberCommentsPerRaffle[1]}" /></td>
	</tr>
	
		<tr>
	<td><spring:message code="administrator.dashboard.prize" /></td>

	<td><jstl:out value="${avgStddevNumberCommentsPerPrize[0]}" />
	<jstl:out value="${avgStddevNumberCommentsPerPrize[1]}" /></td>
	</tr>
	
		<tr>
	 <td> <b><spring:message code="administrator.dashboard.b2" /></b></td>
	</tr>
	
	<tr>
	<td> <spring:message code="administrator.dashboard.actor" /></td>
	 <td><jstl:out value="${avgStddevNumberStarPerActor[0]}" />
		<jstl:out value="${avgStddevNumberStarPerActor[1]}" /></td>
	<tr />	
	
	<tr>
	<td><spring:message code="administrator.dashboard.raffle" /></td>

	<td><jstl:out value="${avgStddevNumberStarPerRaffle[0]}" />
	<jstl:out value="${avgStddevNumberStarPerRaffle[1]}" /></td>
	</tr>
	
		<tr>
	<td><spring:message code="administrator.dashboard.prize" /></td>

	<td><jstl:out value="${avgStddevNumberStarPerPrize[0]}" />
	<jstl:out value="${avgStddevNumberStarPerPrize[1]}" /></td>
	</tr>
	
	
	<tr>
		<td><spring:message code="administrator.dashboard.b3" /></td>
	
	<td><jstl:forEach var="i" items="${avgNumberStarPerActorGroupByCountry}"
		varStatus="loop">
		<jstl:out value="${i[0]}" />
		<jstl:out value="${i[1]}" />
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach>
	</td>
</tr>
	
<tr>

	<td><spring:message code="administrator.dashboard.b4" /></td>

	<td><jstl:forEach var="j" items="${avgNumberStarPerActorGroupByCity}"
		varStatus="loop">
		<jstl:out value="${j[0]}" />
		<jstl:out value="${j[1]}" />
		<jstl:if test="${!loop.last}">, </jstl:if>
	</jstl:forEach> </td>

</tr>
	
	

</table>


	
	

	
	
	