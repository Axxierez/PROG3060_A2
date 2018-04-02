<!--
// Zack Meadows & Alex Galka
// PROG3060 Assignment 2
// 2018-03-27 
-->
<jsp:include page="header.jsp" />
<%@ page import="java.util.*" %>
<%@ page import="prog3060.zmag_a2.*" %>
<%@ page import="java.sql.Connection" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Geographic Area List</title>
		<link href=”bootstrap/css/bootstrap.min.css” rel=”stylesheet” type=”text/css” />
		<script type=”text/javascript” src=”bootstrap/js/bootstrap.min.js”></script>
	</head>
	<body>
        <jsp:useBean id="jpaBean" class="prog3060.zmag_a2.JPABean" scope="session"/>
    	<%
    	int id = jpaBean.parseStringToInt((String)session.getAttribute("id"));
    	int code = jpaBean.parseStringToInt((String)session.getAttribute("code"));
    	int level = jpaBean.parseStringToInt((String)session.getAttribute("level"));
    	int altCode = jpaBean.parseStringToInt((String)session.getAttribute("altCode"));
    	String ACTION = (String) session.getAttribute("action");
    	
    	if(!jpaBean.isValid()){
        	response.sendRedirect("./login.jsp");
        	return;
        }
    	
		List<Object[]> data = new ArrayList<Object[]>();
		
		if("VIEW_BY_LEVEL".equals(ACTION))
			data = jpaBean.getGeographicAreasByLevel(level);
		else
			data = jpaBean.getAllGeographicAreas();

		int householdsMatchingArea = 0;
		
    	%>
    	<table class="table table-hover table-dark table-sm table-striped">
		  <thead>
		    <tr>
		      <th class="text-center">Area ID</th>
		      <th>Name</th>
		      <th>Code</th>
		      <th>Level</th>
		      <th>Alternative Code</th>
		      <th>Total Population</th>
		      <th>Male Population</th>
		      <th>Female Population</th>
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
	        	GeographicArea geoArea = (GeographicArea) item[1];
	        	String detailParameters = "?id=" + geoArea.getGeographicAreaID() +
	        	        "&code=" + geoArea.getCode() +
	        	        "&level=" + geoArea.getLevel() +
	        	        "&altCode=" + geoArea.getAlternativeCode() +
	        	        "&geoAreaName=" + geoArea.getName().replace("'","");
	        	
		    	%><tr onclick="window.location=<%out.print("'./MenuManagerServlet" + detailParameters + 
		        "&action=" + "VIEW_DETAILS" + "\'");%>"><%
		        %><td class="text-center"><% out.print(geoArea.getGeographicAreaID());%></td><% 
		        %><td><% out.print(geoArea.getName());%></td><% 
		        %><td><% out.print(geoArea.getCode());%></td><% 
		        %><td><% out.print(geoArea.getLevel());%></td><% 
		        %><td><% out.print(geoArea.getAlternativeCode());%></td><% 
		        %><td><% out.print(age.getCombined());%></td><% 
		        %><td><% out.print(age.getMale());%></td><% 
		        %><td><% out.print(age.getFemale());%></td><% 
		        %></tr><%
	    	}
    	}
        %></tbody></table>
        
        <% if(householdsMatchingArea!=0){%>
        	Households Matching Criteria:<% out.print(householdsMatchingArea);
        }%>
	</body>
</html>