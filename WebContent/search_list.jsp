<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border="1">
	<tr>
		<th>이름(상호)</th>
		<th>전화</th>
		<!-- <th>주소(지번)</th> -->
		<th>주소(도로명)</th>
		<!-- <th>위치(위도)</th>
		<th>위치(경도)</th> -->
		<th>거리(m)</th>
		<th>위치보기</th>
	</tr>

    <!-- for(LocationItem item : list) -->
    <c:forEach var="item" items="${ list }">
	    <tr>
	    	<td>${ item.name }</td>
	    	<td>${ item.telephone }</td>
	    	<%-- <td>${ item.address }</td> --%>
	    	<td>${ item.road_address }</td>
	    	<%-- <td>${ item.latitude }</td>
	    	<td>${ item.longitude }</td> --%>
	    	<td>${ item.distance }</td>
	    	<td align="center">
		    	<form action="map_view.do">
		    	   <input type="hidden" name="name" value="${ item.name }">
		    	   <input type="hidden" name="latitude" value="${ item.latitude }">
		    	   <input type="hidden" name="longitude" value="${ item.longitude }">
		    	   <input type="hidden" name="address" value="${ item.address }">
		    	   <input type="hidden" name="telephone" value="${ item.telephone }">
		    	   <input type="submit" value="보기">
		    	</form>   
	    	</td>
	    </tr>
    </c:forEach>


</table>



</body>
</html>