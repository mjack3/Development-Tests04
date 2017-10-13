
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<input type="text" id="textSearch" /> 
<button type="button" class="btn btn-primary" >  <spring:message code="raffle.search" /></button>


<acme:list requestURI="${requestURI}"  list="raffle" hidden_fields="codes,participations,manager,prizes" image_fields="logo" variable="e">

				<security:authorize access="hasRole('USER')">
				<td>
			
				<a href="participation/user/create.do?q=${e.id}"> <spring:message
						code='particiption.user' /></a>
					
			</td>
		<td>
</security:authorize>

<security:authorize access="hasRole('MANAGER')">
<a class="btn btn-primary"
		href="prize/manager/list.do?prizeId=${e.id}"> <spring:message
			code="raffle.prizes" />
	</a>
</security:authorize>
</acme:list>




<script>
$(document).ready(function(){
    $("button").click(function(){
        $.ajax({success: function(result){
        	var input, filter, table, tr, tdTitle,tdDescription,i;
        	input = document.getElementById("textSearch");
        	filter = input.value.toUpperCase();
        	table = document.getElementById("row");
        	tr = table.getElementsByTagName("tr");
        	for (i = 0; i < tr.length; i++) {
        		tdTitle = tr[i].getElementsByTagName("td")[1];
        		tdDescription = tr[i].getElementsByTagName("td")[2];
        			if (tdTitle || tdDescription ) {
            			if (tdTitle.innerHTML.toUpperCase().indexOf(filter) > -1 || 
            					tdDescription.innerHTML.toUpperCase().indexOf(filter) > -1
            				) {
    	          	        tr[i].style.display = "";
    	          	      } else {
    	          	        tr[i].style.display = "none";
    	          	      }            			
            			}
        		}
        }});
    });
});
</script>
