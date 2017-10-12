
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:list requestURI="${requestURI}" list="raffle" hidden_fields="codes,participations,manager,prizes" image_fields="logo" variable="e">

				<security:authorize access="hasRole('USER')">
				<td>
				<jstl:if test="${today.before(e.deadline)}">
				<a href="participation/user/create.do?q=${e.id}"> <spring:message
						code='particiption.user' /></a>
					</jstl:if>	
			</td>
		<td>
</security:authorize>

</acme:list>
