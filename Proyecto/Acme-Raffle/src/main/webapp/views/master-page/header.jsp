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
				          	<li><a href="user/signup.do"><spring:message code="master.page.user.signup" /> </a></li>
							<li><a href="mana/signup.do"><spring:message code="master.page.manager.signup" /> </a></li>
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
							<li><a href="socialidentity/actor/list.do"><spring:message
											code="master.page.socialIdentity" /> </a></li>
					</security:authorize>
					
					<security:authorize access="permitAll()">
						
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false">
	<!-- RAFFLES -->
							<spring:message code="master.page.raffles" /><span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
							<security:authorize access="!hasRole('MANAGER')">
								<li><a href="raffle/list.do"><spring:message code="master.page.raffles"></spring:message></a></li>
							</security:authorize>
							
							<security:authorize access="hasRole('MANAGER')">
								<li><a href="raffle/managers/list.do"><spring:message code="master.page.raffles.manager"></spring:message></a></li>
							</security:authorize>
							</ul>
							
							
	<!-- DESPLEGABLE DE RAFFLES -->
								<security:authorize access="hasRole('MANAGER')">
								<li><a href="mana/edit.do?userAccountID=${id}"><spring:message code="master.page.actor.edit" /></a></li>
								<li><a href="raffle/managers/create.do"><spring:message
											code="master.page.create" /> </a></li>
								</security:authorize>
								
								<security:authorize access="hasRole('USER')">
								<li><a href="user/edit.do?userAccountID=${id}"><spring:message code="master.page.user.edit" /></a></li>
								<li><a href="raffle/user/listparticipation.do"><spring:message code="master.page.listparticipation" /> </a></li>
								
								</security:authorize>
							
							
							
							
							<security:authorize access="hasRole('ADMIN')">
								<li><a href="administrator/edit.do?userAccountID=${id}"><spring:message code="master.page.administrator.edit" /></a></li>
								<li><a href="user/administrator/list.do"><spring:message
											code="master.page.user.list" /> </a></li>
								<li><a href="administrator/dashboard.do"><spring:message
											code="master.page.administrator.dashboard" /> </a></li>
								<li><a href="property/administrator/list.do"><spring:message
											code="master.page.administrator.property" /> </a></li>
								<li><a href="tabooword/administrator/list.do"><spring:message
											code="master.page.administrator.tabooword" /> </a></li>
								<li><a href="auditor/administrator/create.do"><spring:message
											code="master.page.administrator.auditor" /> </a></li>
							</security:authorize>
							
							
							<security:authorize access="hasRole('AUDITOR')">
								<li><a href="auditReport/auditor/list.do"><spring:message code="master.page.auditReport.list" /></a></li>
							</security:authorize>
						
					</security:authorize>
					
					
					
					
					
					
					
					
				</ul>
			</div>
		</div>
	</nav>
	<a href="?language=en"> <img src="images/flag_en.png" />
	</a> <a href="?language=es"> <img src="images/flag_es.png" />
	</a>
</div>


