<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body style="background-color:#343a40">
<jsp:useBean id="jpaBean" class="prog3060.zmag_a2.JPABean" scope="session"/>
<%
if(jpaBean.isValid()){
	response.sendRedirect("./geoClassifications.jsp");
	return;
}
%>
<div class="col-xs-4"></div>
	<div class="col-xs-4">
	<div style="height:250px;display:block;"></div>
	  <div class="panel panel-info">
		  <div class="panel-heading">Login</div>
		  <div class="panel-body">
		    <form action="LoginServlet" method="POST">     
			      <input type="text" class="form-control" name="username" placeholder="Username" autofocus/>
			      <input type="password" class="form-control" name="password" placeholder="Password" />      
		      <button class="btn btn-lg btn-info btn-block" type="submit">Login</button>   
		    </form>
		    <span class="text-danger"><%if(null != session.getAttribute("error")){out.print("<br>" + session.getAttribute("error"));}%></span>
		  </div>
	  </div>
	</div>
<div class="col-xs-4"></div>
</body>
</html>