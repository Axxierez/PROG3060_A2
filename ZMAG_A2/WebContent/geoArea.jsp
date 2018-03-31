<!--
// Zack Meadows & Alex Galka
// PROG3060 Assignment 2
// 2018-03-30 
-->
<jsp:include page="header.jsp" />
<%@ page import="java.util.*" %>
<%@ page import="prog3060.zmag_a2.*" %>
<%@ page import="java.sql.Connection" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Geographic Area</title>
		<link href=”bootstrap/css/bootstrap.min.css” rel=”stylesheet” type=”text/css” />
		<script type=”text/javascript” src=”bootstrap/js/bootstrap.min.js”></script>
	</head>
	<body>
        <jsp:useBean id="connectionBean" class="prog3060.zmag_a2.ConnectionBean" scope="session"/>
    	<%
    	String id = (String) session.getAttribute("id");
    	String name = (String) session.getAttribute("geoAreaName");
    	String code = (String) session.getAttribute("code");
    	String level = (String) session.getAttribute("level");
    	String altCode = (String) session.getAttribute("altCode");
    	String ACTION = (String) session.getAttribute("action");
    	if(null == session.getAttribute("dbConnection")){
        	response.sendRedirect("./login.jsp");
        	return;
        }
    	Connection dbConnection = (Connection) session.getAttribute("dbConnection");
    	
		List<GeographicArea> childAreas = connectionBean.getGeographicAreasByParent(id, code, level, dbConnection);
    	%>
		<h2><% out.print(name); %></h2>
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
        for(GeographicArea item : childAreas)
        {
        	String detailParameters = "?id=" + item.getGeographicAreaID() +
        	        "&code=" + item.getCode() +
        	        "&level=" + item.getLevel() +
        	        "&altCode=" + item.getAlternativeCode() +
        	        "&geoAreaName=" + item.getName().replace("'","");
        	
	    	%><tr onclick="window.location=<%out.print("'./MenuManagerServlet" + detailParameters + 
	        "&action=" + "VIEW_DETAILS" + "\'");%>"><%
	        %><td class="text-center"><% out.print(item.getGeographicAreaID());%></td><% 
	        %><td><% out.print(item.getName());%></td><% 
	        %><td><% out.print(item.getCode());%></td><% 
	        %><td><% out.print(item.getLevel());%></td><% 
	        %><td><% out.print(item.getAlternativeCode());%></td><% 
	        %><td><% out.print(item.getTotalPopulation());%></td><% 
	        %><td><% out.print(item.getMalePopulation());%></td><% 
	        %><td><% out.print(item.getFemalePopulation());%></td><% 
	        %></tr><%
    	}
        %></tbody></table>
	</body>
</html>