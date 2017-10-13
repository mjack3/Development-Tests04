
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<input type="text" id="textSearch" /> 
<button type="button" class="btn btn-primary" >  <spring:message code="raffle.search" /></button>

 <security:authorize access="hasRole('USER')">
 <spring:message code="raffle.logo"  var="logo"/>
 <spring:message code="raffle.title"  var="title"/>
 <spring:message code="raffle.description"  var="description"/>
  <spring:message code="raffle.participations"  var="participations"/>
  <spring:message code="raffle.prizes"  var="prize"/>
<display:table name="raffle" id="row" requestURI="${requestURI}" pagesize="8" class="table table-over">

<display:column title ="${logo}" sortable ="false">
		<img style="max-width: 80px; max-height: 80px;" src="<jstl:out value="${row.logo}"/>">
	</display:column>


<display:column property="title" title="${title}" sortable ="false" />


<display:column property="description" title="${description}" sortable ="false" />

<display:column title="${participations}" sortable="false">
		<jstl:forEach var="p" items="${row.participations}" varStatus="loop">
			<jstl:out value="${p.moment}" />: <jstl:out value="${p.usedCode}" />
			<jstl:if test="${!loop.last}">, </jstl:if>
		</jstl:forEach>
		</display:column>
		
<display:column title="${prize}" sortable="false">
<a href= "prize/user/list.do?q=${row.id}"><spring:message
							code='raffle.prizes' /></a>

</display:column>





</display:table>
</security:authorize>