
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<h1><spring:message code="prize.win"></spring:message></h1>

<img src="https://www.letslearnspanish.co.uk/wp-content/uploads/2016/03/homer-enhorabuena.png">

<input onclick="window.location='/Acme-Raffle/raffle/user/listparticipation.do'" type="button" class="btn btn-warning" value="<spring:message code="prize.listraffle" />">

<h1><spring:message code="prize.youwin"></spring:message> </h1>

<acme:acme_view entity="${prize}" skip_fields="id,version,raffle,user,codes,properties,comments">

<tr>
  <td><spring:message code="prize.properties"/></td>
  <td>
   <table class="table">
    <jstl:forEach var="e" items="${prize.properties}">
     <tr>
      <td><jstl:out value="${e.name}" /></td>
     </tr>
    </jstl:forEach>
   </table>
  </td>
 </tr>
 <tr>


</acme:acme_view>

