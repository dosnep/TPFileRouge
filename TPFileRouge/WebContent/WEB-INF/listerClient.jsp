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
			<th>Nom</th>
			<th>Prenom</th>
			<th>Photo</th>
			<th>Action</th>
		</tr>

		<c:forEach items="${sessionScope.listClient}" var="elem">

			<tr>
				<td>${elem.key}</td>
				<td>${elem.value.nomClient}</td>
				<td>${elem.value.prenomClient}</td>
				<c:choose>
					<c:when test="${!empty elem.value.pathProfilePic}">
						<td><a
							href="<c:url value="/download/${elem.value.pathProfilePic}"/>">Voir
								photos</a></td>
						</td>
					</c:when>
					<c:otherwise>
						<td>Aucune photos</td>
					</c:otherwise>
				</c:choose>
				<td><a href="<c:url value="/supprimerClient"> <c:param name="idClient" value="${elem.key}"/> </c:url>">
				supprimer</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>