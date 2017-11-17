
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<input type="text" id="textSearch" />
<button type="button" class="btn btn-primary">
	<spring:message code="raffle.search" />
</button>


<acme:list entityUrl="{prizes:prize/list.do}" requestURI="${requestURI}"
	list="${raffle}"
	hidden_fields="raffle,codes,user,taxonomy,manager,participations,comments"
	image_fields="logo" variable="e">

	<security:authorize access="hasRole('USER')">
		<td><jstl:if
				test="${today.before(e.deadline)&& today.after(e.publicationTime)}">
				<a href="participation/user/create.do?q=${e.id}"> <spring:message
						code='particiption.user' /></a>
			</jstl:if></td>
		<td>
	</security:authorize>
	<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${today.before(e.publicationTime)}">
			<td><a href="raffle/managers/edit.do?q=${e.id}"> <spring:message
						code='acme.edit' /></a></td>
			<td><a href="raffle/managers/delete.do?q=${e.id}"> <spring:message
						code='acme.delete' /></a></td>
		</jstl:if>


		<td>
	</security:authorize>
	<security:authorize access="hasRole('AUDITOR')">
		<td>
				<a href="auditReport/auditor/create.do?q=${e.id}"> <spring:message
						code='auditReport.create' /></a>
		<td>
	</security:authorize>

<security:authorize access="permitAll()">
<a href="auditReport/auditor/list2.do?raffleId=${e.id }">AuditReport</a>
</security:authorize>



</acme:list>




<script>
	$(document).ready(function() {
		$("button").click(function() {
			$.ajax({
				success : function(result) {
					var input, filter, table, tr, tdTitle, tdDescription, i;
					input = document.getElementById("textSearch");
					filter = input.value.toUpperCase();
					table = document.getElementById("row");
					tr = table.getElementsByTagName("tr");
					for (i = 0; i < tr.length; i++) {
						tdTitle = tr[i].getElementsByTagName("td")[1];
						tdDescription = tr[i].getElementsByTagName("td")[2];
						if (tdTitle || tdDescription) {
							if (tdTitle.innerHTML.toUpperCase().indexOf(filter) > -1 || tdDescription.innerHTML.toUpperCase().indexOf(filter) > -1) {
								tr[i].style.display = "";
							} else {
								tr[i].style.display = "none";
							}
						}
					}
				}
			});
		});
	});
</script>
