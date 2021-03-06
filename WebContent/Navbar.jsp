<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <!doctype html>
	<html lang="en">
	<head>
	  	<% //NON SI PUO' FARE QUI IL CHECK DEL LOGIN PERCHE' al codice chiamato con include non è permesso modificare status code ed header e di conseguenza non è possibile effettuare una redirezione
	  	
	  		String titolo = (String) application.getAttribute("titolo");
	  		
	  		int active = 0;
	  		
	  		switch(titolo){
	  			case "Home": active = 0;
	  			break;
	  			
	  			case "Create Activity": active = 1;
	  			break;
	  			
	  			case "Schedule": active = 2;
	  			break;
	  			
	  			case "HomeLogin": active = 3;
	  			break;
	  		};
	  	%>
	  	
	  	<title>Dovado - <%= titolo %> </title>

	<!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">

    <!-- nostro css -->
    <link rel="stylesheet" href="css/dovado.css">
    
  	</head>
	  <body>
	    <!-- inizio navbar -->
	
	    <nav class="navbar navbar-expand-lg navbar-dark">
	      <div class="container-fluid">
	        <a class="navbar-brand" href="sample.jsp">
	        	 <img src="logo/DovadoLogo(3).png" alt="" width="auto" height="50vh"> 
	        </a>
	        <%	
	        String path = ((HttpServletRequest) request).getRequestURI();
	        System.out.println(path);
	        
	      
	        
	        if (true) { //qua se non è loggato si mette a false così non compaiono i pulsanti %>
	        
	        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
	          <span class="navbar-toggler-icon"></span>
	        </button>
	        <div class="collapse navbar-collapse" id="navbarNav">
	          <ul class="navbar-nav">
	            <li class="nav-item">
	              <a class="nav-link <% if(active == 0) out.print("active"); %>" aria-current="page" href="sample.jsp">Home</a>
	            </li>
	            <li class="nav-item">
	              <a class="nav-link <% if(active == 1) out.print("active"); %>" href="CreateActivity.jsp">Create Activity</a>
	            </li>
	            <li class="nav-item">
	              <a class="nav-link <% if(active == 2) out.print("active"); %>" href="Schedule.jsp">Schedule</a>
	            </li>
	          </ul>
	        </div>
	        <% } %>
	      </div>
	    </nav>
	
	
	  <!-- fine navbar -->
	
	    <!-- Optional JavaScript; choose one of the two! -->
	
	    <!-- Option 1: Bootstrap Bundle with Popper -->
	    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
	
	    <!-- Option 2: Separate Popper and Bootstrap JS -->
	    <!--
	    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
	    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js" integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT" crossorigin="anonymous"></script>
	    -->