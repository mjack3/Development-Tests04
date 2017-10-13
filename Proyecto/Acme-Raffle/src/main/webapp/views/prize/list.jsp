<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

 <security:authorize access="hasRole('USER')">
 
 <spring:message code="prize.name"  var="name"/>
 <spring:message code="prize.description"  var="description"/>
  <spring:message code="prize.taxonomy"  var="taxonomy"/>
 
<display:table name="prize" id="row" requestURI="${requestURI}" pagesize="8" class="table table-over"> 

<display:column property="name" title="${name}" sortable ="false" />
<display:column property="description" title="${description}" sortable ="false" />
<display:column title="${taxonomy}" sortable="false">
		<jstl:forEach var="p" items="${row.taxonomy}" varStatus="loop">
			<jstl:out value="${p.property}"  />
			<jstl:if test="${!loop.last}">, </jstl:if>
		</jstl:forEach>
		</display:column>




</display:table>
</security:authorize>