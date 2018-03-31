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
    	Connection dbConnection = (Connection) session.getAttribute("dbConnection");
    	List<AgeGroup> ageGroups = jpaBean.getAgeGroupPopulation(dbConnection);
    	%>
    	<table class="table table-hover table-dark table-sm table-striped">
			  <thead>
			    <tr>
			      <th>Age Group</th>
			      <th>Male Population (2011)</th>
			      <th>Female Population (2011)</th>
			      <th>Total Population (2011)</th>
			      <th>Male Population (2016)</th>
			      <th>Female Population (2016)</th>
			      <th>Total Population (2016)</th>
			    </tr>
			  </thead>
			<tbody>
			
			<%
	        for(AgeGroup item : ageGroups)
	        {
		        %><tr><%
		        %><td><% out.print("");%></td><% 
		        %><td><% out.print("");%></td><% 
		        %><td><% out.print("");%></td><% 
		        %><td><% out.print("");%></td><% 
		        %></tr><%
	    	}
	        %>
	        </tbody>
        </table>
	</body>
</html>