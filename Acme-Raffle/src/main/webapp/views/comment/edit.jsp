<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${actionParam }" modelAttribute="comment">

<form:hidden path="actor"/>
<form:hidden path="raffle"/>
<form:hidden path="prize"/>
<form:hidden path="id"/>
<form:hidden path="version"/>



<acme:textarea2 code="comment.text" path="text"/>

<b><spring:message code="comment.rating" /></b> <form:select path="rating">

<form:option value="0">0</form:option>
<form:option value="1">1</form:option>
<form:option value="2">2</form:option>
<form:option value="3">3</form:option>
</form:select><br/><br/>

<acme:submit name="save" code="comment.save"/>
<acme:cancel url="/welcome/index.do" code="comment.cancel"/>

</form:form>