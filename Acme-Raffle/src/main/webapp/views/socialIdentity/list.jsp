<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:list requestURI="socialidentity/actor/list.do" list="${socialIdentity}" 
editUrl="socialidentity/actor/edit.do" deleteUrl="socialidentity/actor/delete.do" pagesize="12">
</acme:list>

<br/>

<a href="socialidentity/actor/create.do">
	<spring:message code="socialidentity.create" /> 
</a>