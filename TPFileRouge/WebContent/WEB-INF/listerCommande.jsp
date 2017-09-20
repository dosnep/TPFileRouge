<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<table>
		<tr>
			<th>ID</th>
			<th>Nom Client</th>
			<th>Prenom Client</th>
		</tr>

		<c:forEach items="${sessionScope.listCommande}" var="elem">

			<tr>
				<td>${elem.key}</td>
				<td>${elem.value.client.nomClient}</td>
				<td>${elem.value.client.prenomClient}</td>
				<td><a href="<c:url value="/supprimerCommande"><c:param name="idCommande" value="${elem.key}"></c:param></c:url>">supprimer</a></td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>