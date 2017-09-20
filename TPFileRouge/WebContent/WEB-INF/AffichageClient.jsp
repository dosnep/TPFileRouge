<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Affichage client</title>
<link type="text/css" rel="stylesheet" href="inc/visuel.css" />

</head>
<body>
	<c:choose>
		<c:when test="${!empty formClient.errors}">

			<div>
				<form method="post" action="creationClient"
					enctype="multipart/form-data">
					<fieldset>
						<legend>Informations client</legend>
						<label for="nomClient">Nom <span class="requis">*</span></label> <input
							type="text" id="nomClient" name="nomClient"
							value="<c:out value="${client.nomClient}"></c:out>" size="20"
							maxlength="20" /> <span>${formClient.errors['nomClient']}</span>

						<br /> <label for="prenomClient">Prénom </label> <input
							type="text" id="prenomClient" name="prenomClient"
							value="<c:out value="${client.prenomClient}"></c:out>" size="20"
							maxlength="20" /> <span>${formClient.errors['prenomClient']}</span><br />
						<label for="adresseClient">Adresse de livraison <span
							class="requis">*</span>
						</label> <input type="text" id="adresseClient" name="adresseClient"
							value="<c:out value="${client.adresseClient}"></c:out>" size="20"
							maxlength="20" /> <span>${formClient.errors['adresseClient']}</span>
						<br /> <label for="telephoneClient">Numéro de téléphone <span
							class="requis">*</span>
						</label> <input type="text" id="telephoneClient" name="telephoneClient"
							value="<c:out value="${client.telephoneClient}"></c:out>"
							size="20" maxlength="20" /> <span>${formClient.errors['telephoneClient']}</span><br />
						<label for="emailClient">Adresse email</label> <input type="email"
							id="emailClient" name="emailClient"
							value="<c:out value="${client.emailClient}"></c:out>" size="20"
							maxlength="60" /> <span>${formClient.errors['emailClient']}</span><br />
						<label for="fichier">Emplacement du fichier <span
							class="requis">*</span></label> <input type="file" id="fichier"
							name="fichier" /> <br> <input type="submit" value="Valider" />
						<input type="reset" value="Remettre Ã  zÃ©ro" /> <br />
					</fieldset>
				</form>
			</div>



		</c:when>

		<c:otherwise>

			<c:out value="${client.nomClient}">Renseignez tous les champs</c:out>
			<br>
			<c:out value="${client.prenomClient}">Renseignez tous les champs</c:out>
			<br>
			<c:out value="${client.adresseClient}">Renseignez tous les champs</c:out>
			<br>
			<c:out value="${client.telephoneClient}">Renseignez tous les champs</c:out>
			<br>
			<c:out value="${client.emailClient}" />

		</c:otherwise>
	</c:choose>

	<p>
		<c:out value="${formClient.resultat}"></c:out>
	</p>





</body>
</html>