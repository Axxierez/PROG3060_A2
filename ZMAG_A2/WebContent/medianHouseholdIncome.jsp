<!--
// Zack Meadows & Alex Galka
// PROG3060 Assignment 1
// 2018-02-09  
-->
<jsp:include page="header.jsp" />
<%@ page import="java.util.*" %>
<%@ page import="prog3060.zmag_a2.*" %>
<%@ page import="java.sql.Connection" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Population by Age</title>
		<link href=”bootstrap/css/bootstrap.min.css” rel=”stylesheet” type=”text/css” />
		<script type=”text/javascript” src=”bootstrap/js/bootstrap.min.js”></script>
	</head>
	<body>
		<jsp:useBean id="jpaBean" class="prog3060.zmag_a2.JPABean" scope="session"/>
		<%
    	if(!jpaBean.isValid()){
        	response.sendRedirect("./login.jsp");
        	return;
        }
		List<Household> data = jpaBean.getMedianHouseholdIncome();
    	%>
		<table class="table table-hover table-dark table-sm table-striped">
		  <thead>
		    <tr>
		      <th class="text-center">Name</th>
		      <th>Income</th>		     
		    </tr>
		  </thead>
		  <tbody>
		  
		  <%
		  if(data != null){
			  
			  for(int i= 0; i < data.size(); i++){
		        	Household household = data.get(i);
		        	
			    	%><td class="text-center"><% out.print(household.getGeographicArea().getName());%></td><% 
			        %><td><% out.print(household.getNumberReported());%></td><% 
			        %></tr><%
			  }
    	}
        %></tbody></table>
		
	</body>
</html>
