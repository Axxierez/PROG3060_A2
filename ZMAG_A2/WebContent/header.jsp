<!--
// Zack Meadows & Alex Galka
// PROG3060 Assignment 1
// 2018-02-09  
-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
	<head>
			<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
			<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
	</head>
	<body style="background-color:#343a40">
		<div class="col-sm-12">
			<div style="padding:10px;">
			<button class=" btn btn-dark" onclick="window.location='./MenuManagerServlet?action=VIEW_ALL_CLASSIFICATIONS'">View Classifications</button>
			<button class=" btn btn-dark" onclick="window.location='./MenuManagerServlet?action=VIEW_ALL_GEOAREAS'">View All Geographic Areas</button>
			<button class=" btn btn-dark" onclick="window.location='./popByAge.jsp'">View Population by Age</button>
			</div>
		</div>
	</body>
</html>