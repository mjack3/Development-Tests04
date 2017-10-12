
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<h1><spring:message code="prize.win"></spring:message></h1>

<img src="https://www.letslearnspanish.co.uk/wp-content/uploads/2016/03/homer-enhorabuena.png">

<h1><spring:message code="prize.youwin"></spring:message> </h1>

<acme:acme_view entity="${prize}" skip_fields="id,version,raffle,taxonomy,user,codes"></acme:acme_view>