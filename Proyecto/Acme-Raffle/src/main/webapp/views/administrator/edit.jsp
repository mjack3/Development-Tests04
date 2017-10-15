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

	<security:authorize access="hasRole('ADMIN')">
		<jstl:set var="url" value="administrator/save-administrator.do" />
	</security:authorize>

<form:form action="${url}" modelAttribute="administrator">

		
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="userAccount.username" />
		<form:hidden path="userAccount.password" />
		<form:hidden path="userAccount.authorities" />
		<form:hidden path="userAccount.banned" />
		<form:hidden path="userAccount.socialIdentities" />


	    <div class="form-group" style="width: 20%;"> 
	    
		<label> <spring:message code="admin.actorName"/> </label>
		<br />
		<input class="form-control" value="${administrator.name}" type="text" name="name"/>
		<form:errors cssClass="error" path="name" /> <br />
		
		<label> <spring:message code="admin.surname"/> </label><br />
		<input class="form-control" value="${administrator.surname}" type="text" name="surname"/>
		<form:errors cssClass="error" path="surname" /> <br />
		
		<label> <spring:message code="admin.email"/> </label><br />
		<input class="form-control" value="${administrator.email}" type="text" name="email"/>
		<form:errors cssClass="error" path="email" /> <br />
		
		<label> <spring:message code="admin.phone"/> </label><br />
		<input class="form-control" value="${administrator.phone}" type="text" name="phone"/>
		<form:errors cssClass="error" path="phone" /> <br />
		
		<label> <spring:message code="admin.postalAddress"/> </label><br />
		<input class="form-control" value="${administrator.postal}" type="text" name="postal"/>
		<form:errors cssClass="error" path="postal" /> <br />
		
		
		<label> <spring:message code="admin.city"/> </label><br />
		<input class="form-control" value="${administrator.city}" type="text" name="city"/>
		<form:errors cssClass="error" path="city" /> <br />
		
		
		<label> <spring:message code="admin.country"/> </label><br />
		<input class="form-control" value="${administrator.country}" type="text" name="country"/>
		<form:errors cssClass="error" path="country" /> <br />
		
		</div>
		
		<spring:message code="actor.save" var="actorSaveHeader"/>
		<spring:message code="actor.cancel" var="actorCancelHeader"/>
		<input type="submit" class="btn btn-primary" name="save" value="${actorSaveHeader}" />
		<input onclick="window.history.back()" class="btn btn-warning" type="button" name="cancel" value="${actorCancelHeader}"/>
		
		
	</form:form>

</security:authorize>





