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
		List<Object[]> detailData = jpaBean.getGeographicAreasByID(id);
		List<Age> yearData = jpaBean.getCensusYearPopulation(id,2011);
		yearData.addAll(jpaBean.getCensusYearPopulation(id,2016));
    	if(detailData != null){
	        Iterator <Object[]> dataIterator = detailData.iterator();
	        while(dataIterator.hasNext())
	        {
	            Object[] item = dataIterator.next();
	        	Age age = (Age) item[0];
	        	GeographicArea geoArea = (GeographicArea) item[1];
	        	
		       
	        	
	        	int householdCount = 0;
	        	try{
		        	//Household household = (Household) householdData.get(0)[0];
		        	//householdCount = household.getNumberReported();
		        } catch(Exception e){
		        	householdCount = 0;
		        }

		        %><hr><div class="mx-5"><h2><% out.print(geoArea.getName());%></h2>
		        <table>
		        <tr><td class="font-weight-bold">Code </td><td class="text-right px-5"><% out.print(geoArea.getCode());%></td></tr>
		        <tr><td class="font-weight-bold">Level </td><td class="text-right px-5"><% out.print(geoArea.getLevel());%></td></tr>
		        <tr><td class="font-weight-bold">Alternative Code </td><td class="text-right px-5"><% out.print(geoArea.getAlternativeCode());%></td></tr>
		        <%if(level <= 1){%>
		        <tr><td class="font-weight-bold">Total Households </td><td class="text-right px-5"><% out.print(householdCount);%></td></tr>
		        <%}%>
		        </table>
		        </div><hr><div class="row">
    		<%
		        
 				for(int i = 0; i < 2; i++){%>
 				<div class="col-sm"><div class="mx-5"><h2><% out.print(yearData.get(i).getCensusYear().getCensusYear());%></h2>
		        	<table>
		        		<tr>
		        			<td>
		        			 <tr><td class="font-weight-bold">Male </td><td class="text-right px-5"><% out.print(yearData.get(i).getMale());%></td></tr>
						     <tr><td class="font-weight-bold">Female </td><td class="text-right px-5"><% out.print(yearData.get(i).getFemale());%></td></tr>
						     <tr><td class="font-weight-bold">Combined</td><td class="text-right px-5"><% out.print(yearData.get(i).getCombined());%></td></tr>
		        			</td>
		        		</tr>
		        	</table>
		        </div></div><hr>
		        <%
		        }
		        
	    	}
    	}
		List<Object[]> data = jpaBean.getGeographicAreasByParent(code, level);
    	%>
    	</div><br>
    	<h4 class="mx-5">Geographic Areas</h4>
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
	</body>
</html>