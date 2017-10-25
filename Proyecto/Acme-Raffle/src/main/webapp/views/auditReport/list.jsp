<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:list requestURI="auditReport/auditor/list.do" list="${auditReports}" 
editUrl="auditReport/auditor/edit.do" deleteUrl="auditReport/auditor/delete.do"
 hidden_fields="raffle,finalMode" variable="a">

<td>
	<a href="raffle/listAuditor.do?q=${a.id}"> <spring:message code="auditreport.raffle" /> </a>
</td>

</acme:list>

<br/>

<a href="auditReport/auditor/create.do">
	<spring:message code="auditreport.create" /> 
</a>