<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<title>CrÃ©ation d'une commande</title>
<link type="text/css" rel="stylesheet" href="inc/visuel.css" />
</head>
<body>
	<div>

		<form method="post" action="creationCommande"
			enctype="multipart/form-data">

			<label for="listUser">Nouveau client?</label> <input type="radio"
				id="oui" value="oui" name="newUser" onClick="teste()">oui <input
				type="radio" id="non" value="non" name="newUser" onClick="teste()">Non<br>

			<legend>Informations client</legend>

			<fieldset id="user">
				<c:import url="formulaireClient.html"></c:import>
				<label for="fichier">Emplacement du fichier <span
					class="requis">*</span></label> <input type="file" id="fichier"
					name="fichier" /> <br>
			</fieldset>

			<fieldset id="users">
				<select name="idClient" id="usersList">
					<c:forEach items="${sessionScope.listClient}" var="elem">
						<option value="${elem.key}">${elem.key}---
							${elem.value.nomClient}</option>
					</c:forEach>
				</select>


			</fieldset>

			<fieldset id="commande">

				<legend>Informations commande</legend>

				<label for="montantCommande">Montant <span class="requis">*</span></label>
				<input type="text" id="montantCommande" name="montantCommande"
					value="" size="20" maxlength="20" /> <br /> <label
					for="modePaiementCommande">Mode de paiement <span
					class="requis">*</span></label> <input type="text"
					id="modePaiementCommande" name="modePaiementCommande" value=""
					size="20" maxlength="20" /> <br /> <label
					for="statutPaiementCommande">Statut du paiement</label> <input
					type="text" id="statutPaiementCommande"
					name="statutPaiementCommande" value="" size="20" maxlength="20" />
				<br /> <label for="modeLivraisonCommande">Mode de livraison
					<span class="requis">*</span>
				</label> <input type="text" id="modeLivraisonCommande"
					name="modeLivraisonCommande" value="" size="20" maxlength="20" />
				<br /> <label for="statutLivraisonCommande">Statut de la
					livraison</label> <input type="text" id="statutLivraisonCommande"
					name="statutLivraisonCommande" value="" size="20" maxlength="20" />
				<br />
			</fieldset>
			<input type="submit" value="Valider" /> <input type="reset"
				value="Remettre Ã  zÃ©ro" /> <br />
		</form>
	</div>

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
