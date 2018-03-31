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
		List<Object[]> data = jpaBean.getAgeGroupPopulation(2016);
    	%>
    	<div class="row">
    		<div class="col-sm">
			<h2 class="mx-5">Census Year 2016</h2>
		    	<table class="table table-hover table-dark table-sm table-striped">
					  <thead>
					    <tr>
					      <th>Age Group</th>
					      <th>Male Population</th>
					      <th>Female Population</th>
					      <th>Total Population</th>
					    </tr>
					  </thead>
					<tbody>
					
					<%
			    	if(data != null){
				        Iterator <Object[]> dataIterator = data.iterator();
				        while(dataIterator.hasNext())
				        {
				            Object[] item = dataIterator.next();
				        	Age age = (Age) item[0];
				        	AgeGroup ageGroup = (AgeGroup) item[1];
				        	
					        %><tr><%
					        %><td><% out.print(ageGroup.getDescription());%></td><% 
					        %><td><% out.print(age.getMale());%></td><% 
					        %><td><% out.print(age.getFemale());%></td><% 
					        %><td><% out.print(age.getCombined());%></td><% 
					        %></tr><%
			    		}
			    	}
			        %>
			        </tbody>
		        </table>
    		</div>
    		<div class="col-sm">
		        <% 
				data = jpaBean.getAgeGroupPopulation(2011);
				%>
		        <h2 class="mx-5">Census Year 2011</h2>
		    	<table class="table table-hover table-dark table-sm table-striped">
					  <thead>
					    <tr>
					      <th>Age Group</th>
					      <th>Male Population</th>
					      <th>Female Population</th>
					      <th>Total Population</th>
					    </tr>
					  </thead>
					<tbody>
					
					<%
			    	if(data != null){
				        Iterator <Object[]> dataIterator = data.iterator();
				        while(dataIterator.hasNext())
				        {
				            Object[] item = dataIterator.next();
				        	Age age = (Age) item[0];
				        	AgeGroup ageGroup = (AgeGroup) item[1];
				        	
					        %><tr><%
					        %><td><% out.print(ageGroup.getDescription());%></td><% 
					        %><td><% out.print(age.getMale());%></td><% 
					        %><td><% out.print(age.getFemale());%></td><% 
					        %><td><% out.print(age.getCombined());%></td><% 
					        %></tr><%
			    		}
			    	}
			        %>
			        </tbody>
		        </table>
    		</div>
    	</div>
	</body>
</html>