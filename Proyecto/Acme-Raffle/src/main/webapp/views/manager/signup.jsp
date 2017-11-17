<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<security:authorize access="isAnonymous()" >



<form:form action="${url}" modelAttribute="manager">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="userAccount.authorities" />
		<form:hidden path="userAccount.banned" />
		<form:hidden path="raffles" />


	    <div class="form-group" style="width: 20%;"> 
	    
	    <label> <spring:message code="mana.userAccount.username"/> </label><br />
		<input class="form-control" value="${manager.userAccount.username}" type="text" name="userAccount.username"/>
		<form:errors cssClass="error" path="userAccount.username" /> <br />
		
		<label> <spring:message code="mana.userAccount.password"/> </label><br />
		<input class="form-control" value="${manager.userAccount.password}" type="password" name="userAccount.password"/>
		<form:errors cssClass="error" path="userAccount.password" /> <br />
	    
		<label> <spring:message code="mana.actorName"/> </label>
		<br />
		<input class="form-control" value="${manager.name}" type="text" name="name"/>
		<form:errors cssClass="error" path="name" /> <br />
		
		<label> <spring:message code="mana.surname"/> </label><br />
		<input class="form-control" value="${manager.surname}" type="text" name="surname"/>
		<form:errors cssClass="error" path="surname" /> <br />
		
		<label> <spring:message code="mana.email"/> </label><br />
		<input class="form-control" value="${manager.email}" type="text" name="email"/>
		<form:errors cssClass="error" path="email" /> <br />
		
		<label> <spring:message code="mana.phone"/> </label><br />
		<input class="form-control" value="${manager.phone}" type="text" name="phone"/>
		<form:errors cssClass="error" path="phone" /> <br />
		
		<label> <spring:message code="mana.postalAddress"/> </label><br />
		<input class="form-control" value="${manager.postal}" type="text" name="postal"/>
		<form:errors cssClass="error" path="postal" /> <br />
		
		
		<label> <spring:message code="mana.city"/> </label><br />
		<input class="form-control" value="${manager.city}" type="text" name="city"/>
		<form:errors cssClass="error" path="city" /> <br />
		
		
		<label> <spring:message code="mana.country"/> </label><br />
		<input class="form-control" value="${manager.country}" type="text" name="country"/>
		<form:errors cssClass="error" path="country" /> <br />
		
		</div>
		
		<spring:message code="actor.save" var="actorSaveHeader"/>
		<spring:message code="actor.cancel" var="actorCancelHeader"/>
		<input type="submit" class="btn btn-primary" name="save" value="${actorSaveHeader}" />
		<input onclick="window.history.back()" class="btn btn-warning" type="button" name="cancel" value="${actorCancelHeader}"/>
		
		
	</form:form>
	
	</security:authorize>