<%@ page
	import="java.io.*,java.util.*, java.util.Iterator"%>
<%@ page import="it.polimi.tiw.bean.PersonBean" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List of people living in a city</title>
</head>
<body>
	<p>
		Number of persons that match the query:
		<%=request.getAttribute("number")%></p>
	<table border="1">
		<tr>
			<td>Firstname</td>
			<td>Lastname</td>
			<td>City</td>
		</tr>
		<%
			List<PersonBean> persons = (List<PersonBean>) request.getAttribute("persons");
			Iterator<PersonBean> iterator = persons.iterator();
			while (iterator.hasNext()) {
				PersonBean person = (PersonBean) iterator.next();
				String firstname = (String) person.getName();
				String lastname = (String) person.getLastname();
				String city = (String) person.getCity();
				out.print("<tr><td>" + firstname + "</td>");
				out.println("<td>" + lastname + "</td>");
				out.println("<td>" + city + "</td></tr>");
			}
		%>
	</table>
</body>
</html>
