package batailleNavale;

public class Navire {
	private String proprietaire;		//DETERMINE A QUI APPARTIENT LE NAVIRE (ORDINATEUR, OU JOUEUR)
	private String initial;				//DETERMINE LES INITIALES DE NOTRE NAVIRE (POURRAIS SERVIR POUR L'AFFICHAGE AFIN DE DISTINGUER LES NAVIRES ENNEMIES ET DU JOUEUR)
	private String typeNavire;			//DETERMINE LE TYPE DE NAVIRE (porte-avions (5 cases), croiseurs (4 cases), contre-torpilleurs (3 cases), torpilleurs (2 cases)
	private int nbCaseNavire;			//DETERMINE LE NOMBRE DE CASE POUR UN TYPE DE NAVIRE
	/*---------------------------------------------------*/
	public Navire (String proprietaire, String typeNavire) {
		this.proprietaire = proprietaire;
		this.typeNavire = typeNavire;
		if (this.typeNavire == "porte-avions") { this.nbCaseNavire = 5; this.initial = "PA"; }
		else if (this.typeNavire == "croiseurs") { this.nbCaseNavire = 4; this.initial = "Cr"; }
		else if (this.typeNavire == "contre-torpilleurs") { this.nbCaseNavire = 3; this.initial = "CT"; }
		else { this.nbCaseNavire = 2; this.initial = "To"; }
	}
	/*---------------------------------------------------*/
	public String getProprietaire() { return this.proprietaire; }
	public void setProprietaire(String proprio) { this.proprietaire = proprio; }
	public String getInitial() { return this.initial; }
	public void setInitial(String initial) { this.initial = initial; }
	public String getTypeNavire() { return this.typeNavire; }
	public void setTypeNavire(String type) { this.typeNavire = type; }
	public int getNbCaseNavire() { return this.nbCaseNavire; }
	public void setNbCaseNavire(int cases) { this.nbCaseNavire = cases; }
}