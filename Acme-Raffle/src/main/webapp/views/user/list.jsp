<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:list requestURI="user/administrator/list.do" list="${users}" 
hidden_fields="prizes,participations,userAccount,validCodes" variable="a" pagesize="12">

<jstl:if test="${bannedUsers.contains(a) }">
	<td>
		<a href="user/administrator/readmit.do?q=${a.id}"> <spring:message code="user.readmit" /> </a>
	</td>
</jstl:if>

<jstl:if test="${!bannedUsers.contains(a) }">
<td>
		<a href="user/administrator/banned.do?q=${a.id}"> <spring:message code="user.banned" /> </a>
	</td>
</jstl:if>

</acme:list>