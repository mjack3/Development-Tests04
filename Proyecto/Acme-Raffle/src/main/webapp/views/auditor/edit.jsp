<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form modelAttribute="auditorForm" action="${actionParam }">

<acme:textbox2 code="admin.userAccount.username" path="username"/>
<acme:password2 code="admin.userAccount.password" path="password"/>
<acme:password2 code="auditor.repeatPassword" path="repeatPassword"/>
<acme:textbox2 code="admin.name" path="name"/>
<acme:textbox2 code="admin.surname" path="surname"/>
<acme:textbox2 code="admin.email" path="email"/>
<acme:textbox2 code="admin.phone" path="phone"/>
<acme:textbox2 code="admin.postal" path="postal"/>
<acme:textbox2 code="admin.city" path="city"/>
<acme:textbox2 code="admin.country" path="country"/>

<br/>

<acme:submit name="save" code="actor.save"/>
<acme:cancel url="/welcome.index" code="actor.cancel"/>

</form:form>




