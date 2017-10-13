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

<form:form action="${url}" modelAttribute="actor">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="userAccount.authorities" />
		
		
		<jstl:if test="${type==1}">
			<form:hidden path="prizes" />
			<form:hidden path="participations" />
		</jstl:if>

		<jstl:if test="${type==2}">
			<form:hidden path="raffles" />
		</jstl:if>
		



	    <div class="form-group" style="width: 20%;"> 
	    
	    <label> <spring:message code="actor.userAccount.username"/> </label><br />
		<input class="form-control" value="${actor.userAccount.username}" type="text" name="userAccount.username"/>
		<form:errors cssClass="error" path="userAccount.username" /> <br />
		
		<label> <spring:message code="actor.userAccount.password"/> </label><br />
		<input class="form-control" value="${actor.userAccount.password}" type="password" name="userAccount.password"/>
		<form:errors cssClass="error" path="userAccount.password" /> <br />
	    
		<label> <spring:message code="actor.actorName"/> </label>
		<br />
		<input class="form-control" value="${actor.name}" type="text" name="name"/>
		<form:errors cssClass="error" path="name" /> <br />
		
		<label> <spring:message code="actor.surname"/> </label><br />
		<input class="form-control" value="${actor.surname}" type="text" name="surname"/>
		<form:errors cssClass="error" path="surname" /> <br />
		
		<label> <spring:message code="actor.email"/> </label><br />
		<input class="form-control" value="${actor.email}" type="text" name="email"/>
		<form:errors cssClass="error" path="email" /> <br />
		
		<label> <spring:message code="actor.phone"/> </label><br />
		<input class="form-control" value="${actor.phone}" type="text" name="phone"/>
		<form:errors cssClass="error" path="phone" /> <br />
		
		<label> <spring:message code="actor.postalAddress"/> </label><br />
		<input class="form-control" value="${actor.postal}" type="text" name="postal"/>
		<form:errors cssClass="error" path="postal" /> <br />
		
		
		<label> <spring:message code="actor.city"/> </label><br />
		<input class="form-control" value="${actor.city}" type="text" name="city"/>
		<form:errors cssClass="error" path="city" /> <br />
		
		
		<label> <spring:message code="actor.country"/> </label><br />
		<input class="form-control" value="${actor.country}" type="text" name="country"/>
		<form:errors cssClass="error" path="country" /> <br />
		
		</div>
		
		<spring:message code="actor.save" var="actorSaveHeader"/>
		<spring:message code="actor.cancel" var="actorCancelHeader"/>
		<input type="submit" class="btn btn-primary" name="save" value="${actorSaveHeader}" />
		<input onclick="window.history.back()" class="btn btn-warning" type="button" name="cancel" value="${actorCancelHeader}"/>
		
		
	</form:form>
	
	</security:authorize>