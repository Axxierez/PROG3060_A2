<!--
// Zack Meadows & Alex Galka
// PROG3060 Assignment 2
// 2018-03-27
-->
<jsp:include page="header.jsp" />
<%@ page import="java.util.*" %>
<%@ page import="prog3060.zmag_a2.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Geographic Area Classification</title>
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
        %>
    	<table class="table table-hover table-dark table-sm table-striped">
		  <thead>
		    <tr>
		      <th class="text-center">Level</th>
		      <th>Description</th>
		    </tr>
		  </thead>
		  <tbody>
		  	<tr onclick="window.location='./MenuManagerServlet?level=0&action=VIEW_BY_LEVEL'">
			  	<td class="text-center">0</td>
			  	<td class="text-center">The Country of Canada</td>
		  	</tr>
		  	<tr onclick="window.location='./MenuManagerServlet?level=1&action=VIEW_BY_LEVEL'">
			  	<td class="text-center">1</td>
			  	<td class="text-center">Provinces and Territories</td>
		  	</tr>
		  	<tr onclick="window.location='./MenuManagerServlet?level=2&action=VIEW_BY_LEVEL'">
			  	<td class="text-center">2</td>
			  	<td class="text-center">Census metropolitan areas (CMA) and census agglomerations (CA)</td>
		  	</tr>
		  	<tr onclick="window.location='./MenuManagerServlet?level=3&action=VIEW_BY_LEVEL'">
			  	<td class="text-center">3</td>
			  	<td class="text-center">CMA & CA regions</td>
		  	</tr>
		  	</tbody>
		</table>
	</body>
</html>