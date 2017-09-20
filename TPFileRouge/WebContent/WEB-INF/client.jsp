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
	<div>
		<form method="post" action="creationClient"
			enctype="multipart/form-data">
			<fieldset>
				<legend>Informations client</legend>
				<c:import url="formulaireClient.html"></c:import>
				<label for="fichier">Emplacement du fichier <span
					class="requis">*</span></label> <input type="file" id="fichier"
					name="fichier" /> <br> 
					<input type="submit" value="Valider" /> <input
					type="reset" value="Remettre Ã  zÃ©ro" /> <br />
			</fieldset>
		</form>
	</div>
</body>
</html>

