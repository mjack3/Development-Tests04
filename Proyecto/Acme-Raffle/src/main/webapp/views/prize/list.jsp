<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<spring:message code="prize.name" var="name" />
<spring:message code="prize.description" var="description" />
<spring:message code="prize.properties" var="property" />

<display:table name="prize" id="row" requestURI="${requestURI}"
	pagesize="8" class="table table-over">

	<display:column property="name" title="${name}" sortable="false" />
	<display:column property="description" title="${description}"
		sortable="false" />
	<display:column title="${property}" sortable="false">
		<jstl:forEach var="p" items="${row.properties}" varStatus="loop">
			<jstl:out value="${p.name}" />
			<jstl:if test="${!loop.last}">, </jstl:if>
		</jstl:forEach>
	</display:column>
	<jstl:if test="${editable}">
		<security:authorize access="hasRole('MANAGER')">

			<display:column>

				<a href="prize/manager/edit.do?prizeId=${row.id }"> <spring:message
						code="prize.edit" />
				</a>
			</display:column>
			<display:column>

				<a href="prize/manager/editProperties.do?prizeId=${row.id }"> <spring:message
						code="prize.editProperties" />
				</a>
			</display:column>
			<display:column>

				<a href="prize/manager/regCode.do?prizeId=${row.id }"> <spring:message
						code="prize.regCode" />
				</a>
			</display:column>

		</security:authorize>
	</jstl:if>

<security:authorize access="isAuthenticated()">
<display:column>
	<a href="comment/createOnPrize.do?prizeId=${row.id }"> <spring:message code="comment.action" /> </a>
</display:column>
</security:authorize>



<security:authorize access="isAuthenticated()">
<display:column>
<jstl:if test="${not empty row.comments }">
	<a href="comment/list.do?q=${row.id }"> <spring:message code="comment.action.list" /> </a>
</jstl:if>
</display:column>
</security:authorize>




</display:table>
<%-- <jstl:if test="${editable}">
<security:authorize access="hasRole('MANAGER')">
	<a href="prize/manager/create.do?raffleId=${raffleId}"> <spring:message
			code="prize.create" />
	</a>
</security:authorize>
</jstl:if> --%>