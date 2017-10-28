<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:acme_form url="raffle/managers/saveEdit.do" hiddenFields="comments" skip_fields="deadline,publicationTime" entity="${raffle}" type="edit">

<acme:textbox2 code="raffle.publicationTime" path="publicationTime"/>
<acme:textbox2 code="raffle.deadline" path="deadline"/>

</acme:acme_form>