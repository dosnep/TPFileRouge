package beans;

public class CommandeB {

	private Long id;
	private ClientB client;
	private String dateCommande;
	private String montantCommande;
	private String modePaiementCommande;
	private String statutPaiementCommande;
	private String modeLivraisonCommande;
	private String statutLivraisonCommande;

	public ClientB getClient() {
		return client;
	}

	public void setClient(ClientB client) {
		this.client = client;
	}

	public String getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(String dateCommande) {
		this.dateCommande = dateCommande;
	}

	public String getMontantCommande() {
		return montantCommande;
	}

	public void setMontantCommande(String montantCommande) {
		this.montantCommande = montantCommande;
	}

	public String getModePaiementCommande() {
		return modePaiementCommande;
	}

	public void setModePaiementCommande(String modePaiementCommande) {
		this.modePaiementCommande = modePaiementCommande;
	}

	public String getStatutPaiementCommande() {
		return statutPaiementCommande;
	}

	public void setStatutPaiementCommande(String statutPaiementCommande) {
		this.statutPaiementCommande = statutPaiementCommande;
	}

	public String getModeLivraisonCommande() {
		return modeLivraisonCommande;
	}

	public void setModeLivraisonCommande(String modeLivraisonCommande) {
		this.modeLivraisonCommande = modeLivraisonCommande;
	}

	public String getStatutLivraisonCommande() {
		return statutLivraisonCommande;
	}

	public void setStatutLivraisonCommande(String statutLivraisonCommande) {
		this.statutLivraisonCommande = statutLivraisonCommande;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
