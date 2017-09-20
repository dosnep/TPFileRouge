<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Affichage commande</title>
<link type="text/css" rel="stylesheet" href="inc/visuel.css" />

</head>
<body>

	<c:choose>
		<c:when test="${!empty form.errors}">
			<div>
				<form method="post" action="creationCommande"
					enctype="multipart/form-data">

					<label for="listUser">Nouveau client?</label> <input type="radio"
						id="oui" value="oui" name="newUser" onClick="teste()">oui
					<input type="radio" id="non" value="non" name="newUser"
						onClick="teste()">Non<br>

					<fieldset id="user">
						<legend>Informations client</legend>
						<label for="nomClient">Nom <span class="requis">*</span>
						</label> <input type="text" id="nomClient" name="nomClient"
							value="<c:out value="${commande.client.nomClient}"></c:out>"
							size="20" maxlength="20" /> <span>${form.errors['nomClient']}</span>

						<br /> <label for="prenomClient">Prénom </label> <input
							type="text" id="prenomClient" name="prenomClient"
							value="<c:out value="${commande.client.prenomClient}"></c:out>"
							size="20" maxlength="20" /> <span>${formClient.errors['prenomClient']}</span><br />
						<label for="adresseClient">Adresse de livraison <span
							class="requis">*</span>
						</label> <input type="text" id="adresseClient" name="adresseClient"
							value="<c:out value="${commande.client.adresseClient}"></c:out>"
							size="20" maxlength="20" /> <span>${form.errors['adresseClient']}</span>
						<br /> <label for="telephoneClient">Numéro de téléphone <span
							class="requis">*</span>
						</label> <input type="text" id="telephoneClient" name="telephoneClient"
							value="<c:out value="${commande.client.telephoneClient}"></c:out>"
							size="20" maxlength="20" /> <span>${form.errors['telephoneClient']}</span><br />
						<label for="emailClient">Adresse email</label> <input type="email"
							id="emailClient" name="emailClient"
							value="<c:out value="${commande.client.emailClient}"></c:out>"
							size="20" maxlength="60" /> <span>${form.errors['emailClient']}</span><br />

						<label for="fichier">Emplacement du fichier <span
							class="requis">*</span></label> <input type="file" id="fichier"
							name="fichier" /> <br>

					</fieldset>


					<fieldset id="users">
						<select name="idClient" id="usersList">
							<c:forEach items="${sessionScope.listClient}" var="elem">
								<option value="${elem.key}">${elem.key} --- ${elem.value.nomClient}</option>
							</c:forEach>
						</select>


					</fieldset>


					</fieldset>

					<fieldset>
						<legend>Informations commande</legend>

						
						<label for="montantCommande">Montant <span class="requis">*</span>
						</label> <input type="text" id="montantCommande" name="montantCommande"
							value="<c:out value="${commande.montantCommande}"></c:out>"
							size="20" maxlength="20" /> <span>${form.errors['montantCommande']}</span><br />
						<label for="modePaiementCommande">Mode de paiement <span
							class="requis">*</span></label> <input type="text"
							id="modePaiementCommande" name="modePaiementCommande"
							value="<c:out value="${commande.modePaiementCommande}"></c:out>"
							size="20" maxlength="20" /> <span>${form.errors['modePaiementCommande']}</span><br />
						<label for="statutPaiementCommande">Statut du paiement</label> <input
							type="text" id="statutPaiementCommande"
							name="statutPaiementCommande"
							value="<c:out value="${command.statutPaiementCommande}"></c:out>"
							size="20" maxlength="20" /> <span>${form.errors['statutPaiementCommande']}</span>
						<br /> <label for="modeLivraisonCommande">Mode de
							livraison <span class="requis">*</span>
						</label> <input type="text" id="modeLivraisonCommande"
							name="modeLivraisonCommande"
							value="<c:out value="${commande.modeLivraisonCommande}"></c:out>"
							size="20" maxlength="20" /> <span>${form.errors['modeLivraisonCommande']}</span><br />
						<label for="statutLivraisonCommande">Statut de la
							livraison</label> <input type="text" id="statutLivraisonCommande"
							name="statutLivraisonCommande"
							value="<c:out value="${commande.statutLivraisonCommande}"></c:out>"
							size="20" maxlength="20" /> <span>${form.errors['statutLivraisonCommande']}</span><br />
					</fieldset>
					<input type="submit" value="Valider" /> <input type="reset"
						value="Remettre Ã  zÃ©ro" /> <br />
				</form>
			</div>

		</c:when>
		<c:otherwise>

			<h1>Client :</h1>
			<br>

			<c:out value="${commande.client.nomClient}">Renseignez tous les champs</c:out>
			<br>
			<c:out value="${commande.client.prenomClient}">Renseignez tous les champs</c:out>
			<br>
			<c:out value="${commande.client.adresseClient}">Renseignez tous les champs</c:out>
			<br>
			<c:out value="${commande.client.telephoneClient}">Renseignez tous les champs</c:out>
			<br>
			<c:out value="${commande.client.emailClient}" />


			<h1>Commande :</h1>
			<br>

			<c:out value="${commande.dateCommande}">Renseignez tous les champs</c:out>
			<br>
			<c:out value="${commande.montantCommande}">Renseignez tous les champs</c:out>
			<br>
			<c:out value="${commande.modePaiementCommande}">Renseignez tous les champs</c:out>
			<br>
			<c:out value="${commande.statutPaiementCommande}">Renseignez tous les champs</c:out>
			<br>
			<c:out value="${commande.statutLivraisonCommande}" />
		</c:otherwise>
	</c:choose>

	<p>
		<c:out value="${form.resultat}"></c:out>
	</p>

	<script type="text/javascript">
		document.getElementById('user').style.display = 'none'
		document.getElementById('users').style.display = 'none'

		function teste() {
			if (document.getElementById('oui').checked == true) {
				console.log("oui")
				document.getElementById('user').style.display = 'block'
				document.getElementById('users').style.display = 'none'

			}

			else if (document.getElementById('non').checked == true) {
				console.log("non")
				document.getElementById('users').style.display = 'block'
				document.getElementById('user').style.display = 'none'

			}
		}
	</script>

</body>
</html>