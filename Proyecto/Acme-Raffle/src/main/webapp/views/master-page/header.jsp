<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.jpg" alt="Acme-Raffle Co., Inc." />
</div>


<div style="width: 60%">
	<nav class="navbar navbar-default" style="margin-bottom: 0px">
		<div class="container-fluid">
			<div class="navbar-header">
				<ul class="nav navbar-nav">
					
					<security:authorize access="isAnonymous()">
						<li><a class="fNiv" href="security/login.do"><spring:message
									code="master.page.login" /></a></li>
						<li class="dropdown">
				          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><spring:message code="master.page.signup" /><span class="caret"></span></a>
				          <ul class="dropdown-menu">
				          	<li><a href="actor/signup.do?q=1"><spring:message code="master.page.user.signup" /> </a></li>
							<li><a href="actor/signup.do?q=2"><spring:message code="master.page.manager.signup" /> </a></li>
				          </ul>
				        </li>
				        

						
					</security:authorize>

					<security:authorize access="isAuthenticated()">
						<security:authentication property="principal.id" var="id" />
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false"><security:authentication
									property="principal.username" /><span class="caret"></span></a>
							<ul class="dropdown-menu">
								
								<li><a href="j_spring_security_logout"><spring:message
											code="master.page.logout" /> </a></li>
							</ul></li>

						<li><a href="actor/edit.do?userAccountID=${id}"><spring:message code="master.page.actor.edit" /></a></li>
					</security:authorize>
					
					<security:authorize access="permitAll()">
						
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false">
	<!-- RAFFLES -->
							<spring:message code="master.page.raffles" /><span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
							<li><a href="raffle/list.do"><spring:message code="master.page.raffle"></spring:message></a></li>
							
							
							
	<!-- DESPLEGABLE DE RAFFLES -->
								<security:authorize access="hasRole('MANAGER')">
								<li><a href="raffle/manager/create.do"><spring:message
											code="master.page.create" /> </a></li>
								</security:authorize>
							</ul></li>
							
						
					</security:authorize>
					
					
					
					
					
					
				</ul>
			</div>
		</div>
	</nav>
	<a href="?language=en"> <img src="images/flag_en.png" />
	</a> <a href="?language=es"> <img src="images/flag_es.png" />
	</a>
</div>


