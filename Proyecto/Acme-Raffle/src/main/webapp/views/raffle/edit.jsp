<%--
 * index.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="requestURI" modelAttribute="raffleForm">

<form:hidden path="id"/>
<form:hidden path="version"/>

<acme:textbox2 code="raffle.logo" path="logo"/>
<acme:textbox2 code="raffle.title" path="title"/>
<acme:textbox2 code="raffle.description" path="description"/>
<acme:textbox2 code="raffle.publicationTime" path="publicationTime"/>
<acme:textbox2 code="raffle.deadline" path="deadline"/>

<acme:textbox2 code="raffle.numCodes" path="num"/>
<acme:textbox2 code="raffle.numWinner" path="numWinner"/>


<acme:submit name="save" code="raffle.save"/>
<acme:cancel url="/welcome/index.do" code="raffle.cancel"/>
</form:form>