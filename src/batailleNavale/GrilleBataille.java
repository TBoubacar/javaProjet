package batailleNavale;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JPanel;

public class GrilleBataille extends JPanel{
	private static final long serialVersionUID = -7054238736159071500L;
	/*######################################################
	 * ON CREE LE CHAMP DE BATAILLE A L'AIDE D'UNE GRILLE A 
	 * 2 DIMENSIONS DANS LAQUELLE ON POURRAIT INSERER DES
	 * CELLULES CONTENANT DES PARTIES DE NAVIRES. (BOUTONS)
	 * L'OBJECTIFS DE CETTE MANOEUVRE EST DE POUVOIR SAVOIR
	 * SI UNE CELLULE CONTENANT UN NAVIRE EST DETRUITE OU PAS
	 * #####################################################
	 */
	private static Cellule [][] grilles;					//REPRESENTE LE CHAMP DE BATAILLE POUR LES JOUEURS (LE CHALLENGER ET L'ORDINATEUR)
	private static int [] bombeHori;						//UNE BOMBE QUI INFLIGE DES DEGATS SUR TOUTE LA LIGNE (TAILLE = nbJoueur "2")
	private static int [] bombeVert;						//UNE BOMBE QUI INFLIGE DES DEGATS SUR TOUTE LA COLONNE
	private static int []  bombeCroix;						//UNE BOMBE QUI INFLIGE DES DEGATS SUR 4 CASES ADJASCENTES SUR LA LIGNE OU LA COLONNE
	private static String typeBombe;						//DETERMINE LE TYPE DE BOMBES QUI VA ETRE UTILISE
	private static int tour;								//DETERMINE LE TOUR DE JEU ENTRE L'ORDINATEUR ET LE JOUEUR
	private static int joueurVie;							//DETERMINE LA VIE (LE NOMBRE DE CASES DE NAVIRES RESTANT AU JOUEUR)
	private static int ordinateurVie;						//DETERMINE LA VIE (LE NOMBRE DE CASES DE NAVIRES RESTANT AU JOUEUR)
	private static int niveau;								//DETERMINE LE NIVEAU DE DIFFICULTE DU JEU (1,2,3) RESPECTIVEMENT (FAIBLE,NORMAL,ELEVE)
	private static ArrayList<String> donneeJeu;				//AVEC CETTE VARIABLE JE POURRAIS STOCKER TOUTES LES INFORMATIONS PENDANT LE DEROULEMENT DU JEU
	/*---------------------------------------------------*/
	public GrilleBataille() {
		/*##################################################
		 * TOUT D'ABORD ON INITIALISE LES CHAMPS DE NOTRE 
		 * CHAMP DE BATAILLE DE TELLE SORTE QU'ON SACHE
		 * QU'AUCUN NAVIRE N'OCCUPE DE PLACE DANS (GRILLES)
		 *##################################################
		 */
		super ( new GridLayout(11, 11) );
		initGBataille();
		tour = 1;					//SI 1 ALORS LE JOUEUR PEUT BOMBARDER SINON (0) ALORS C'EST A L'ORDINATEUR
		joueurVie = 0;				//LA VIE DES JOUEURS (ORDINATEUR, JOUEUR) EST INITIALISEE A 0
		ordinateurVie = 0;
	}
	/*---------------------GETTERS & SETTERS------------------------*/
	public Cellule[][] getGrilles() { return grilles; }
	public static int[] getBombeHori() { return bombeHori; }
	public static void setBombeHori(int indJoueur, int nbBombe) { bombeHori[indJoueur] = nbBombe; }
	public static int[] getBombeVert() { return bombeVert; }
	public static void setBombeVert(int indJoueur, int nbBombe) { bombeVert[indJoueur] = nbBombe; }
	public static int[] getBombeCroix() { return bombeCroix; }
	public static void setBombeCroix(int indJoueur, int nbBombe) { bombeCroix[indJoueur] = nbBombe; }
	public static String getTypeBombe() { return typeBombe; }
	public static void setTypeBombe(String bombe) { typeBombe = bombe; }
	public static int getTour() { return tour; }
	public static void setTour(int monTour) { tour = monTour; }
	public static int getJoueurVie() { return joueurVie; }
	public static void setJoueurVie(int vie) { joueurVie = vie; }
	public static int getOrdinateurVie() { return ordinateurVie; }
	public static void setOrdinateurVie(int vie) { ordinateurVie = vie; }
	public int getNiveau() { return niveau; }
	public void setNiveau(int niv) { niveau = niv; }
	public static ArrayList<String> getDonneJeu() { return donneeJeu; }
	public static void setDonneJeu(ArrayList<String> donnee) { donneeJeu = donnee; }
	
	/*-----------------------------------------------------------------*/
	public void initGBataille() {		//INITIALISE NOTRE GRILLE AVEC LES BORDURES (A,B,C,...) (1,2,3,...)
		grilles = new Cellule [10][10];
		bombeHori = new int [2];		//LA CASE D'INDICE 0 CORRESPOND AUX BOMBES DE L'ORDINATEUR
		bombeVert = new int [2];		//LA CASE D'INDICE 1 CORRESPOND AUX BOMBES DU JOUEUR
		bombeCroix = new int [2];
		typeBombe = "bombeClassique";
		niveau = 1;
		donneeJeu = new ArrayList<String>();
		for (int i = -1; i < grilles.length; i++) {
			for (int j = -1; j < grilles[0].length; j++) {
				Cellule celluleVide = new Cellule(i,j);
				add(celluleVide);
				if (i >= 0 && j >= 0) grilles[i][j] = celluleVide;
			}
		}
		for (int i = 0; i < 2; i++) {
			bombeHori[i] = 1;
			bombeVert[i] = 1;
			bombeCroix[i] = 1;
		}
	}
	
	/*---------------------------------------------------*/
	public static boolean inserHorizontalPossible (Navire nav, int ligne, int colonne) {
		/*#################################################################
		 * CETTE FONCTION PERMET DE DETERMINER SI NOUS AVONS LA POSSIBILITE
		 * D'INSERER UN NAVIRE DANS UNE CELLULE DE NOTRE GRILLE 
		 * (CHAMP DE BATAILLE) DE MANIERE HORIZONTALE 
		 *#################################################################
		 */
		if (ligne >= 0 && ligne < grilles.length && colonne >= 0 && colonne < grilles[0].length) {
			if ((colonne+nav.getNbCaseNavire()-1) < grilles.length) {
				for (int i = colonne; i < colonne+nav.getNbCaseNavire(); i++) {
					if (grilles[ligne][i].getNavire() != null) {
						return false;
					} 
				}
				return true;
			} else if ((colonne-nav.getNbCaseNavire()) >= 0) {
				for (int i = colonne; i > colonne-nav.getNbCaseNavire() ; i--) {
					if (grilles[ligne][i].getNavire() != null) {
						return false;
					}
				} return true;
			} else return false;
		} else return false;
	}
	/*---------------------------------------------------*/
	public static boolean inserVerticalPossible (Navire nav, int ligne, int colonne) {
		/*#################################################################
		 * CETTE FONCTION PERMET DE DETERMINER SI NOUS AVONS LA POSSIBILITE
		 * D'INSERER UN NAVIRE DANS NOTRE GRILLE (CHAMP DE BATAILLE)
		 * 					 DE MANIERE VERTICALE 
		 *#################################################################
		 */
		if (ligne >= 0 && ligne < grilles.length && colonne >= 0 && colonne < grilles[0].length) {
			if ((ligne+nav.getNbCaseNavire()-1) < grilles.length) {
				for (int i = ligne; i < ligne+nav.getNbCaseNavire(); i++) {
					if (grilles[i][colonne].getNavire() != null) {
						return false;
					}
				}
				return true;
			} else if ((ligne-nav.getNbCaseNavire()) >= 0) {
				for (int i = ligne; i > ligne-nav.getNbCaseNavire(); i--) {
					if (grilles[i][colonne].getNavire() != null) {
						return false;
					}
				} return true;
			}
			else return false;
		} else return false;
	}
	/*---------------------------------------------------*/
	public void ajoutNavireManuel (Navire nav, String alignement, int ligne, String col) {
		int colonne = transformeChaineEnChiffre(col);
		/*###############################################################################
		 * CETTE FONCTION NOUS PERMET D'INSERER MANUELLEMENT UN NAVIRE DANS LES CELLULES
		 * DE LA GRILLE DE MANIERE VERTICALE OU HORIZONTALE INDIQUEE, EN FONCTION DE VOTRE
		 * PREFERENCE ET DES PLACES DISPONIBLES (LE JOUEUR A LE LIBRE CHOIX). L'INSERTION
		 * POUR L'ORDINATEUR SE FERAIT APRES POUR EVITER QUE LE JOUEUR NE REPERE LE POSITION
		 * DES NAVIRES ENNEMIES DURANT L'INSERTION DES SIENS. CAR PENDANT LA SAISIE, IL
		 * LUI SERA INDIQUE SI UNE PLACE EST DISPONIBLE OU PAS.
		 *###############################################################################
		 */
		if (!inserVerticalPossible(nav, ligne, colonne) && alignement.equals("V")) {
			System.out.println( "IMPOSSIBLE D'INSERER VERTICALEMENT A CETTE POSITION OUUPPS !!! (°_°)");
		} if (!inserHorizontalPossible(nav, ligne, colonne) && alignement.equals("H")) {
			System.out.println( "IMPOSSIBLE D'INSERER HORIZONTALEMENT A CETTE POSITION OUUPPS !!! (°_°)");
		} if (inserVerticalPossible(nav,ligne,colonne) && alignement.equals("V")) {
			if (ligne+nav.getNbCaseNavire()-1 < grilles[0].length) {
				for (int i = ligne; i < ligne+nav.getNbCaseNavire(); i++) {
					grilles[i][colonne].setNavire(nav);
					col = transformeChiffreEnChaine(colonne);
					System.out.println( "PARTIES DU NAVIRE INSEREE VERTICALEMENT EN POSITION ("+i+","+col+") YEAH !!! (^_^)");
				}
			} else {
				for (int i = ligne; i > ligne-nav.getNbCaseNavire(); i--) {
					grilles[i][colonne].setNavire(nav);
					col = transformeChiffreEnChaine(colonne);
					System.out.println( "PARTIE DU NAVIRE INSEREE VERTICALEMENT EN POSITION ("+i+","+col+") YEAH !!! (^_^)");
				}
			}
		} if (inserHorizontalPossible(nav,ligne,colonne) && alignement.equals("H")) {
			if (colonne+nav.getNbCaseNavire()-1 < grilles.length) {
				for (int i = colonne; i < colonne+nav.getNbCaseNavire(); i++) {
					grilles[ligne][i].setNavire(nav);
					col = transformeChiffreEnChaine(i);
					System.out.println( "PARTIE DU NAVIRE INSEREE HORIZONTALEMENT EN POSITION ("+ligne+","+col+") YEAH !!! (^_^)");
				}
			} else {
				for (int i = colonne; i > colonne-nav.getNbCaseNavire() ; i--) {
					grilles[ligne][i].setNavire(nav);
					col = transformeChiffreEnChaine(i);
					System.out.println( "PARTIE DU NAVIRE INSEREE HORIZONTALEMENT EN POSITION ("+ligne+","+col+") YEAH !!! (^_^)");
				}
			}
		}
		afficheGrille(); setJoueurVie(14);
	}
	public void ajoutNavireAutomatique (Navire nav) {
		/*#################################################################
		 * CETTE FONCTION NOUS PERMET D'INSERER ALEATOIREMENT UN NAVIRE 
		 * PRESENT DANS LES CELLULES DE LA GRILLE DE MANIERE VERTICALE 
		 * OU HORIZONTALE EN FONCTION DES PLACES DISPONIBLES
		 *#################################################################
		 */
		int ligne = (int) (Math.random() * 10);
		int colonne = (int) (Math.random() * 10);
		
		if (inserVerticalPossible(nav,ligne,colonne)) {
			if (ligne+nav.getNbCaseNavire()-1 < grilles[0].length) {
				for (int i = ligne; i < ligne+nav.getNbCaseNavire(); i++) {
					grilles[i][colonne].setNavire(nav);
				}
			} else {
				for (int i = ligne; i > ligne-nav.getNbCaseNavire(); i--) {
					grilles[i][colonne].setNavire(nav);
				}
			}
		} else if (inserHorizontalPossible(nav,ligne,colonne)) {
			if (colonne+nav.getNbCaseNavire()-1 < grilles.length) {
				for (int i = colonne; i < colonne+nav.getNbCaseNavire(); i++) {
					grilles[ligne][i].setNavire(nav);
				}
			} else {
				for (int i = colonne; i > colonne-nav.getNbCaseNavire() ; i--) {
					grilles[ligne][i].setNavire(nav);
				}
			}
		} else ajoutNavireAutomatique (nav); //ON FAIT UN APPEL RECURSIF LORSQUE NOUS NE POUVONS PAS INSERER DE NAVIRES A LA POSITION CHOISI ALEATOIREMENT
	}
	/*---------------------------------------------------*/
	public static boolean estToucher (int ligne, int colonne) {	//UNE CASE EST TOUCHEE SI ELLE EST OCCUPEE PAR UN NAVIRE ET QU'ELLE A ETE BOMBARDEE
		return grilles[ligne][colonne].getEtat() == true && grilles[ligne][colonne].getNavire() != null;
	}
	public static boolean estOccuper (int ligne, int colonne) {	//UNE CASE EST OCCUPEE SI ELLE N'EST PAS TOUCHEE ET QU'ELLE CONTIENT UN NAVIRE
		return grilles[ligne][colonne].getNavire() != null && !estToucher(ligne,colonne);
	}
	public static boolean estProprio (String nom, int ligne, int colonne) { //DETERMINE LE PROPRIETAIRE DU NAVIRE D'UNE CASE OCCUPEE
		return estOccuper(ligne,colonne) && grilles[ligne][colonne].getNavire().getProprietaire().equals(nom);
	}
	/*---------------------------------------------------*/
	public void afficheGrille () {
		/*#################################################################
		 * CETTE METHODE NOUS PERMETTRA D'AFFICHER LE CHAMP DE BATAILLE 
		 * EN DETERMINANT LES NAVIRES DU JOUEUR EST CELUI DE L'ORDINATEUR
		 * JPA	=> JOEUR PORTE-AVION			|		OPA		ORDINATEUR PORTE-AVION
		 * JCr	=> JOEUR CROISSEUR				|		OCr		ORDINATEUR CROISSEUR		
		 * JCT	=> JOEUR CONTRE-TORPILLEUR		|		OCT		ORDINATEUR CONTRE-TORPILLEUR
		 * JTo	=> JOEUR TORPILLEUR				|		OTo		ORDINATEUR TORPILLEUR
		 *#################################################################
		 */
		
		int x = 1;
		System.out.println("    A   B   C   D   E   F   G   H   I   J");		//POUR POUVOIR GERER L'AFFICHAGE DES COLONNES
		for (int i = 0; i < grilles.length; i++) {
			if (x < 10) System.out.print(" " + x + " ");						//POUR POUVOIR GERER L'AFFICHAGE DES LIGNES (SANS DECALAGE)
			else System.out.print(x + " ");
			for (int j = 0; j < grilles[0].length; j++) {
				if (estOccuper(i,j)  && grilles[i][j].getNavire().getProprietaire() == "joueur") {
					System.out.print("J"+grilles[i][j].getNavire().getInitial()+" ");
				}
				else if (estOccuper(i,j)  && grilles[i][j].getNavire().getProprietaire() == "ordinateur") {
		//POUR NE PLUS VOIR LES POSITIONS DES NAVIRES ENNEMIES VOUS POUVEZ COMMENTER JUSTE EN BAS
					System.out.print("O"+grilles[i][j].getNavire().getInitial()+" ");
				} else if (estToucher(i,j)) {
					System.out.print(" X  ");
				} else System.out.print(" @  ");
			} System.out.println();
			++x;
		} System.out.println();
	}
	/*---------------------------------------------------*/
	public static int transformeChaineEnChiffre(String col) {
		int colonne = -1;
		if (col.equals("A")) colonne = 0;
		else if (col.equals("B")) colonne = 1;
		else if (col.equals("C")) colonne = 2;
		else if (col.equals("D")) colonne = 3;
		else if (col.equals("E")) colonne = 4;
		else if (col.equals("F")) colonne = 5;	//*########################################################################
		else if (col.equals("G")) colonne = 6;	//*		POUR POUVOIR GERER LES COORDONNEES DE LA MANIERE DEMANDEE
		else if (col.equals("H")) colonne = 7;	//*########################################################################
		else if (col.equals("I")) colonne = 8;
		else if (col.equals("J"))colonne = 9;
		return colonne;
	}
	public static String transformeChiffreEnChaine (int i) {
		String lettre = "";
		if (i == 0) lettre = "A";
		else if (i == 1) lettre = "B";
		else if (i == 2) lettre = "C";
		else if (i == 3) lettre = "D";
		else if (i == 4) lettre = "E";
		else if (i == 5) lettre = "F";	//*########################################################################
		else if (i == 6) lettre = "G";	//*		POUR POUVOIR GERER LES COORDONNEES DE LA MANIERE DEMANDEE
		else if (i == 7) lettre = "H";	//*########################################################################
		else if (i == 8) lettre = "I";
		else if (i == 9) lettre = "J";
		return lettre;
	}
	/*---------------------------------------------------*/
	public static String bestChoix (int ligne, int colonne, int tailleMaxDroit, int tailleMaxGauche, int tailleMaxHaut, int tailleMaxBas ) {	
		/*#########################################################################
		 * DETERMINE LA MEILLEURE DIRECTION DE TIR TRES UTILE POUR (BOMBE EN CROIX)
		 * RETOURNE "D" POUR SIGNALER QU'IL EST JUDICIEUX DE TIRER VERS LA DROITE
		 * RETOURNE "G" POUR SIGNALER QU'IL EST JUDICIEUX DE TIRER VERS LE GAUCHE
		 * RETOURNE "H" POUR SIGNALER QU'IL EST JUDICIEUX DE TIRER VERS LE HAUT
		 * RETOURNE "B" POUR SIGNALER QU'IL EST JUDICIEUX DE TIRER VERS LE BAS
		 *#########################################################################
		 */
		int compteurDroit = 0, compteurGauche = 0, comptHaut = 0, comptBas = 0;
		int plusGrand1 = 0, plusGrand2 = 0;
		String indice = "", indice1 = "", indice2 = "";
		
		for (int i = 0; i < tailleMaxDroit; i++) {
			if (getTour() == 0 && estOccuper(ligne,i) && estProprio("joueur",ligne,i)) ++compteurDroit;
			if (getTour() == 1 && estOccuper(ligne,i) && estProprio("ordinateur",ligne,i)) ++compteurDroit;
		}
		for (int i =  grilles[0].length-1; i >= tailleMaxGauche; i--) {
			if (getTour() == 0 && estOccuper(ligne,i) && estProprio("joueur",ligne,i)) ++compteurGauche;
			if (getTour() == 1 && estOccuper(ligne,i) && estProprio("ordinateur",ligne,i)) ++compteurGauche;
		}
		if (compteurDroit < compteurGauche) { plusGrand1 = compteurGauche; indice1 = "G"; }
		else { plusGrand1 = compteurDroit; indice = "D"; }
		for (int i = grilles.length-1; i >= tailleMaxHaut; i--) {
			if (getTour() == 0 && estOccuper(i,colonne) && estProprio("joueur",i,colonne)) ++comptHaut;
			if (getTour() == 1 && estOccuper(i,colonne) && estProprio("ordinateur",i,colonne)) ++comptHaut;
		}
		for (int i = 0; i < tailleMaxBas; i++) {
			if (getTour() == 0 && estOccuper(i,colonne) && estProprio("joueur",i,colonne)) ++comptBas;
			if (getTour() == 1 && estOccuper(i,colonne) && estProprio("ordinateur",i,colonne)) ++comptBas;
		}
		if (comptHaut < comptBas) { plusGrand2 = comptBas; indice2 = "B"; }
		else { plusGrand2 = comptHaut;  indice2 = "H"; }
		if (plusGrand1 < plusGrand2) { indice = indice2; }
		else { indice = indice1; }
		return indice;
	}
	public void choixDifficulte () {
		int i = 2;
		System.out.println("\nCHOIX DU NIVEAU DE DIFFICULTE : \n" +
			"FACILE		===>		1\n" + 
			"NORMALE		===>		2\n" + 
			"DIFFICILE	===>		3\n" + 
			"PAR DEFAUT LE NIVEAU EST FACILE (^-^)");
		@SuppressWarnings("resource")
		Scanner clavier = new Scanner (System.in);
		do {
			try
			{
				System.out.print("FAITES VOTRE CHOIX (ex : 1 pour facile) : ");
				i = clavier.nextInt();
				if (i > 4 || i < 1) throw new Exception ("ON NE DISPOSE QUE DE 3 TYPES DONT LES VALEURS POSSIBLES SONT (1,2,3)");
				break;
			}
			catch(InputMismatchException e) { System.err.println("Erreur de saisi (La valeur doit être un entier)");}
			catch(Exception e) { System.err.println(e.getClass().getName() + " : " + e.getMessage());}
			finally { System.out.println("\n"); }
		} while (true);
		niveau = i;
		donneeJeu.add("La difficulté du jeu est le niveau : " + niveau);
	}
	public static void detruitNavire (int ligne, int colonne, String nomJoueur) { //CECI VA ALLEGER LE CONTENU DE MON CODE
		String lettre = "";
		if (nomJoueur.equals("ordinateur") && !estToucher(ligne, colonne)) {
			grilles[ligne][colonne].getNavire().setInitial("X ");
			grilles[ligne][colonne].setEtat(true);
			lettre = GrilleBataille.transformeChiffreEnChaine(colonne);

			grilles[ligne][colonne].setText("OX");
			grilles[ligne][colonne].setBackground( new Color(177, 70, 16) );
			grilles[ligne][colonne].setEnabled( false );
			System.out.println("Cible atteint #" + grilles[ligne][colonne].getNavire().getTypeNavire() + "# en position (" + (ligne+1) + "," + lettre +") PAR LE JOUEUR");
			donneeJeu.add("Cible atteint #" + grilles[ligne][colonne].getNavire().getTypeNavire() + "# en position (" + (ligne+1) + "," + lettre +") PAR LE JOUEUR");
			--ordinateurVie;
		} if (nomJoueur.equals("joueur") && !estToucher(ligne, colonne)) {
			grilles[ligne][colonne].getNavire().setInitial("X ");
			grilles[ligne][colonne].setEtat(true);
			lettre = transformeChiffreEnChaine(colonne);

			grilles[ligne][colonne].setText("JX");
			grilles[ligne][colonne].setBackground( new Color(180, 10, 10) );
			grilles[ligne][colonne].setEnabled( false );
			String msg = "TOUR DE L'ORDINATEUR : \nCible atteint #" + grilles[ligne][colonne].getNavire().getTypeNavire() + "# en position (" + (ligne+1) + "," + lettre +") PAR L'ORDINATEUR";
			MyWindow.getJTextArea().setText(msg);

			System.out.println("Cible atteint #" + grilles[ligne][colonne].getNavire().getTypeNavire() + "# en position (" + (ligne+1) + "," + lettre +") PAR L'ORDINATEUR");
			donneeJeu.add("Cible atteint #" + grilles[ligne][colonne].getNavire().getTypeNavire() + "# en position (" + (ligne+1) + "," + lettre +") PAR L'ORDINATEUR");
			--joueurVie;
		}
		setTypeBombe("bombeClassique");	
		//AU CAS OU L'ORDINATEUR OU LE JOUEUR DEMANDE L'USAGE D'UNE BOMBE, AFIN DE NE PAS OBLIGEE L'AUTRE A L'UTILISER ON FAIT CECI
	}
	/*-------------------------------------------------------------------------------------------------------------------*/
	public static void bombardePosition (int ligne, String col) {		//METHODE DE BOMBARDEMENT POUR LE JOUEUR
		int colonne = transformeChaineEnChiffre(col);
		if (typeBombe.equals("bombeHori") && bombeHori[getTour()] >= 1) {				//UNE BOMBE QUI INFLIGE DES DEGATS SUR TOUTE LA LIGNE
			System.out.println("---USAGE DE LA BOMBE HORIZONTALE PAR LE JOUEUR (^_^) EN POSITION ("+(ligne+1)+","+col+")---");
			donneeJeu.add("---USAGE DE LA BOMBE HORIZONTALE PAR LE JOUEUR (^_^) EN POSITION ("+(ligne+1)+","+col+")---");
			setBombeHori(getTour(),getBombeHori()[getTour()]-1);
			for (int i = 0; i < grilles[0].length; i++) {
				if (getTour() == 1 && estOccuper(ligne,i) && estProprio("ordinateur",ligne,i)) {
					detruitNavire(ligne, i, "ordinateur");
				}
			} 
		} else if (typeBombe.equals("bombeVert") && bombeVert[0] >= 1) {			//UNE BOMBE QUI INFLIGE DES DEGATS SUR TOUTE LA COLONNE
			System.out.println("---USAGE DE LA BOMBE VERTICALE PAR LE JOUEUR (^_^) EN POSITION ("+(ligne+1)+","+col+")---");
			donneeJeu.add("---USAGE DE LA BOMBE VERTICALE PAR LE JOUEUR (^_^) EN POSITION ("+(ligne+1)+","+col+")---");
			MyWindow.getJTextArea().setText("DEMANDE D'USAGE DE LA BOMBE V PAR L'ORDINATEUR");
			setBombeVert(getTour(),getBombeVert()[getTour()]-1);
			for (int i = 0; i < grilles.length; i++) {
				if (getTour() == 1 && estOccuper(i,colonne) && estProprio("ordinateur",i,colonne)) {
					detruitNavire(i,colonne, "ordinateur");
				}
			}
		} else if (typeBombe.equals("bombeCroix") && bombeCroix[getTour()] >= 1) {		
			/*#####################################################################################
			 * UNE BOMBE QUI INFLIGE DES DEGATS SUR 4 CASES ADJASCENTES SUR LA LIGNE OU LA COLONNE
			 * JE VAIS AMELIORER CETTE METHODE DE TELLE SORTE QUE L'USAGE DE CETTE BOMBE SOIT AUSSI
			 * DEVASTATRICE QUE LES DEUX PREMIERS EN M'ASSURANT QUE LORS DE LA FRAPPE LE PLUS GRAND
			 * NOMBRE DE NAVIRE PUISSE TOMBEE. POUR CELA JE VAIS FERAIS UN TEST QUI ME PERMETTRA
			 * DE SAVOIR SI JE FRAPPE VERS LA DROITE ET LA GAUCHE LE COTE QUI ME SERA LE PLUS UTILE
			 * PUIS JE FERAI DE MEME POUR LE BAS ET LA HAUT ET APRES JE CHOISIRAI LE MEILLEUR COUP
			 * PARMI LES DEUX MEILLEURS POSSIBLES.
			 *#####################################################################################
			 */
			System.out.println("---USAGE DE LA BOMBE EN CROIX PAR LE JOUEUR (^_^) EN POSITION ("+(ligne+1)+","+col+")---");
			donneeJeu.add("---USAGE DE LA BOMBE EN CROIX PAR LE JOUEUR (^_^) EN POSITION ("+(ligne+1)+","+col+")---");
			setBombeCroix(getTour(),getBombeCroix()[getTour()]-1);
			int taille = 0, taille2 = 0, taille3 = 0, taille4 = 0;
			if ((colonne+4) < grilles[0].length) taille = colonne+4;		//POUR ALLER A DROITE
			else taille = grilles.length;
			if ((colonne-4) >= 0) taille2 = colonne-4;						//POUR ALLER A GAUCHE
			else taille2 = 0;
			if ((ligne-4) >= 0) taille3 = ligne-4;							//POUR ALLER VERS LE HAUT
			else taille3 = 0;
			if ((ligne+4) < grilles.length) taille4 = ligne+4;				//POUR ALLER VERS LE BAS
			else taille4 = grilles.length;
			String indice = bestChoix(ligne, colonne,taille,taille2,taille3,taille4);

			if (indice == "D") {
				for (int i = colonne; i < taille; i++) {
					if (getTour() == 1 && estOccuper(ligne,i) && estProprio("ordinateur",ligne,i)) {
						detruitNavire(ligne, i, "ordinateur");
					}
				}
			} else if (indice == "G") {
				for (int i = colonne; i > taille2; i--) {
					if (getTour() == 1 && estOccuper(ligne,i) && estProprio("ordinateur",ligne,i)) {
						detruitNavire(ligne, i, "ordinateur");
					}
				}
			} else if (indice == "H") {
				for (int i = ligne; i > taille3; i--) {
					if (getTour() == 1 && estOccuper(i,colonne) && estProprio("ordinateur",i,colonne)) {
						detruitNavire(i, colonne, "ordinateur");
					}
				}
			} else {//5555
				for (int i = ligne; i < taille4; i++) {
					if (getTour() == 1 && estOccuper(i,colonne) && estProprio("ordinateur",i,colonne)) {
						detruitNavire(i,colonne, "ordinateur");
					}
				}
			}
			
		} else {
			if (getTour() == 1 && estOccuper(ligne,colonne) && estProprio("ordinateur",ligne,colonne)) {
				detruitNavire(ligne, colonne, "ordinateur");
			} else {
				grilles[ligne][colonne].setEtat(true);
				if (estProprio("joueur",ligne, colonne)) {
					System.out.println("Vous vous êtes auto-attaqué à la position (" + (ligne+1) + "," + col +")");
					donneeJeu.add("Vous vous êtes auto-attaqué à la position (" + (ligne+1) + "," + col +")");
					setJoueurVie(getJoueurVie()-1);
				}
				System.out.println("Cible ratée en position (" + (ligne+1) + "," + col +") PAR LE JOUEUR");
				donneeJeu.add("Cible ratée en position (" + (ligne+1) + "," + col +") PAR LE JOUEUR");
			}
		}
	}
	public static void bombardeAuto () {		//METHODE DE BOMBARDEMENT POUR L'ORDINATEUR
		int ligne;
		int colonne, choix = 0;
		/*####################################################################################################################
		 * CETTE METHODE EST MA MANIERE DE RENDRE L'ORDINATEUR TRES INTELLIGENT EN LUI DONNANT LA POSSIBILITE DE REFAIRE
		 * UN AUTRE CHOIX DE POSITION POUR SON TIR S'IL AVAIT DEJA FRAPPEE EN UN LIEU ET EVITER QU'IL NE S'ATTAQUE LUI MEME.
		 * MA SECONDE METHODE PERMETTANT DE RENDRE L'ORDINATEUR TRES PUISSANT AU POINT DE DONNER LA CHAIRE DE POULES A SON
		 * ADVERSAIRE ET DE FAIRE EN SORTE QUE TOUS LES COUPS QU'IL TENTERA AURONT UNE FORTE PROBABILITE DE DONNER SUCCES.
		 * CE PHENOMENE SE PRODUIT QUAND IL RENTRE EN ZONE DANGEREUSE. C.A.D QUAND LA DIIFERENCE DE VIE > 7 PAR EXEMPLE.
		 * SI L'ORDINATEUR EST SUR LE POINT DE PERDRE ALORS TOUS SES TIRS PEUVENT ABOUTIR AVEC UNE TRES GRANDE PROBABILITE DE
		 * REUSSITE ET IL POURRAIT FRAPPER AVEC SES BOMBES LES PLUS PUISSANTES QU'IL POSSEDE A CET INSTANT.
		 *####################################################################################################################
		 */
		ligne = (int) (Math.random()*10);
		colonne = (int) (Math.random()*10);
		String col = transformeChiffreEnChaine(colonne);
		int diff = joueurVie-ordinateurVie;
		if (ordinateurVie <= 3 || joueurVie <= 3 || diff >= 8) {
			niveau = 3;
		} if ((ordinateurVie >= 4 && ordinateurVie < 7) || (joueurVie >= 4 && joueurVie < 7) || diff >= 5) {
			niveau = 2;
		}
		if (niveau == 3) {					//3E ZONE DANGEREUSE (ZONE ROUGE)
			while (getTour() == 0 && estOccuper(ligne, colonne) && !estProprio("joueur", ligne, colonne)) {
				ligne = (int) (Math.random()*10);
				colonne = (int) (Math.random()*10);
				if (estProprio("joueur", ligne, colonne) && estOccuper(ligne, colonne)) {
					String indice = bestChoix(ligne, colonne,grilles[0].length,0,0,grilles.length);
					if ((indice == "G" || indice == "D") && getBombeHori()[getTour()] >= 1) {
						setTypeBombe("bombeHori");
					} else if ((indice == "H" || indice == "B") && getBombeVert()[getTour()] >= 1) {
						setTypeBombe("bombeVert");
					} else if (getBombeCroix()[getTour()] >= 1) setTypeBombe("bombeCroix");
					break;
				}
			} 
			while(getTour() == 1 && estOccuper(ligne, colonne) && !estProprio("ordinateur", ligne, colonne)) {
				ligne = (int) (Math.random()*10);
				colonne = (int) (Math.random()*10);
				if (estProprio("ordinateur", ligne, colonne) && estOccuper(ligne, colonne)) {
					String indice = bestChoix(ligne, colonne,grilles[0].length,0,0,grilles.length);
					if ((indice == "G" || indice == "D") && getBombeHori()[getTour()] >= 1) {
						setTypeBombe("bombeHori");
					} else if ((indice == "H" || indice == "B") && getBombeVert()[getTour()] >= 1) {
						setTypeBombe("bombeVert");
					} else if (getBombeCroix()[getTour()] >= 1) setTypeBombe("bombeCroix");
					break;
				}
			}
		}
		else if (niveau == 2) {			//2E ZONE DANGEREUSE (ZONE ORANGE)
			while (getTour() == 0 && estOccuper(ligne, colonne) && !estProprio("joueur", ligne, colonne)) {
				ligne = (int) (Math.random()*10);
				colonne = (int) (Math.random()*10);
				if (estProprio("joueur", ligne, colonne) ) { 
					choix = (int) (Math.random()*4);
					if (choix == 1) setTypeBombe("bombeVert");
					else if (choix == 2) setTypeBombe("bombeHori");
					else if (choix == 3) setTypeBombe("bombeCroix");
					else typeBombe = "bombeClassique";
					break; 
				}
			}
			while (getTour() == 1 && estOccuper(ligne, colonne) && !estProprio("ordinateur", ligne, colonne)) {
				ligne = (int) (Math.random()*10);
				colonne = (int) (Math.random()*10);
				if (estProprio("ordinateur", ligne, colonne)) { 
					choix = (int) (Math.random()*6);
					if (choix == 1) setTypeBombe("bombeVert");
					else if (choix == 2) setTypeBombe("bombeHori");
					else if (choix == 3) setTypeBombe("bombeCroix");
					else typeBombe = "bombeClassique";
					break; 
				}
			}
		}
		else {							//1RE ZONE DANGEREUSE (ZONE VERTE)
			choix = (int) (Math.random()*4); 
			if (choix == 1) setTypeBombe("bombeVert");
			else if (choix == 2) setTypeBombe("bombeHori");
			else if (choix == 3) setTypeBombe("bombeCroix");
			else typeBombe = "bombeClassique";
			while (getTour() == 0 && estOccuper(ligne,colonne) && estProprio("ordinateur", ligne, colonne)) {
				ligne = (int) (Math.random()*10);
				colonne = (int) (Math.random()*10);	
			}
			while (getTour() == 1 && estOccuper(ligne,colonne) && estProprio("joueur", ligne, colonne)) {
				ligne = (int) (Math.random()*10);
				colonne = (int) (Math.random()*10);
			}
		}
		if (ligne >= 0 && ligne < grilles.length && colonne >= 0 && colonne < grilles[0].length) {
			if (typeBombe.equals("bombeHori") && bombeHori[getTour()] >= 1) {				//UNE BOMBE QUI INFLIGE DES DEGATS SUR TOUTE LA LIGNE
				if (getTour() == 0) {
					System.out.println("---USAGE DE LA BOMBE HORIZONTALE PAR L'ORDINATEUR (^_^) EN POSITION ("+(ligne+1)+","+col+")---");
					donneeJeu.add("---USAGE DE LA BOMBE HORIZONTALE PAR L'ORDINATEUR (^_^) EN POSITION ("+(ligne+1)+","+col+")---");
					MyWindow.getJTextArea().setText("DEMANDE D'USAGE DE LA BOMBE H PAR L'ORDINATEUR EN POSITION ("+(ligne+1)+","+col+")");
				}
				else {
					System.out.println("---USAGE DE LA BOMBE HORIZONTALE PAR LE JOUEUR (^_^) EN POSITION ("+(ligne+1)+","+col+")---");
					donneeJeu.add("---USAGE DE LA BOMBE HORIZONTALE PAR LE JOUEUR (^_^) EN POSITION ("+(ligne+1)+","+col+")---");
				}
				setBombeHori(getTour(),getBombeHori()[getTour()]-1);
				for (int i = 0; i < grilles[0].length; i++) {
					if (getTour() == 0 && estOccuper(ligne,i) && estProprio("joueur",ligne,i)) {
						detruitNavire(ligne, i, "joueur");
					} else if (getTour() == 1 && estOccuper(ligne,i) && estProprio("ordinateur",ligne,i)) {
						detruitNavire(ligne, i, "ordinateur");
					}
				}
				setTour((getTour()+1)%2);		//A COMMENTER LORS DU JEU VERSION CONSOLE CAR LA FONCTION BOMBARDEMENT S'EN CHARGE DEJA
			} else if (typeBombe.equals("bombeVert") && bombeVert[0] >= 1) {			//UNE BOMBE QUI INFLIGE DES DEGATS SUR TOUTE LA COLONNE
				if (getTour() == 0) {
					System.out.println("---USAGE DE LA BOMBE VERTICALE PAR L'ORDINATEUR (^_^) EN POSITION ("+(ligne+1)+","+col+")---");
					donneeJeu.add("---USAGE DE LA BOMBE VERTICALE PAR L'ORDINATEUR (^_^) EN POSITION ("+(ligne+1)+","+col+")---");
					MyWindow.getJTextArea().setText("DEMANDE D'USAGE DE LA BOMBE V PAR L'ORDINATEUR EN POSITION ("+(ligne+1)+","+col+")");
				}
				else {
					System.out.println("---USAGE DE LA BOMBE VERTICALE PAR LE JOUEUR (^_^) EN POSITION ("+(ligne+1)+","+col+")---");
					donneeJeu.add("---USAGE DE LA BOMBE VERTICALE PAR LE JOUEUR (^_^) EN POSITION ("+(ligne+1)+","+col+")---");

				}
				setBombeVert(getTour(),getBombeVert()[getTour()]-1);
				for (int i = 0; i < grilles.length; i++) {
					if (getTour() == 0 && estOccuper(i,colonne) && estProprio("joueur",i,colonne)) {
						detruitNavire(i, colonne, "joueur");
					} else if (getTour() == 1 && estOccuper(i,colonne) && estProprio("ordinateur",i,colonne)) {
						detruitNavire(i, colonne, "ordinateur");
					}
				}
				setTour((getTour()+1)%2);		//A COMMENTER LORS DU JEU VERSION CONSOLE
			} else if (typeBombe.equals("bombeCroix") && bombeCroix[getTour()] >= 1) {		
				/*#####################################################################################
				 * UNE BOMBE QUI INFLIGE DES DEGATS SUR 4 CASES ADJASCENTES SUR LA LIGNE OU LA COLONNE
				 * JE VAIS AMELIORER CETTE METHODE DE TELLE SORTE QUE L'USAGE DE CETTE BOMBE SOIT AUSSI
				 * DEVASTATRICE QUE LES DEUX PREMIERS EN M'ASSURANT QUE LORS DE LA FRAPPE LE PLUS GRAND
				 * NOMBRE DE NAVIRE PUISSE TOMBEE. POUR CELA JE VAIS FERAIS UN TEST QUI ME PERMETTRA
				 * DE SAVOIR SI JE FRAPPE VERS LA DROITE ET LA GAUCHE LE COTE QUI ME SERA LE PLUS UTILE
				 * PUIS JE FERAI DE MEME POUR LE BAS ET LA HAUT ET APRES JE CHOISIRAI LE MEILLEUR COUP
				 * PARMI LES DEUX MEILLEURS POSSIBLES.
				 *#####################################################################################
				 */
				if (getTour() == 0) {
					System.out.println("---USAGE DE LA BOMBE EN CROIX PAR L'ORDINATEUR (^_^) EN POSITION ("+(ligne+1)+","+col+")---");
					donneeJeu.add("---USAGE DE LA BOMBE EN CROIX PAR L'ORDINATEUR (^_^) EN POSITION ("+(ligne+1)+","+col+")---");
					MyWindow.getJTextArea().setText("DEMANDE D'USAGE DE LA BOMBE X PAR L'ORDINATEUR EN POSITION ("+(ligne+1)+","+col+")");
				}
				else {
					System.out.println("---USAGE DE LA BOMBE EN CROIX PAR LE JOUEUR (^_^) EN POSITION ("+(ligne+1)+","+col+")---");
					donneeJeu.add("---USAGE DE LA BOMBE EN CROIX PAR LE JOUEUR (^_^) EN POSITION ("+(ligne+1)+","+col+")---");
				}
				setBombeCroix(getTour(),getBombeCroix()[getTour()]-1);
				int taille = 0, taille2 = 0, taille3 = 0, taille4 = 0;
				if ((colonne+4) < grilles[0].length) taille = colonne+4;		//POUR ALLER A DROITE
				else taille = grilles.length;
				if ((colonne-4) >= 0) taille2 = colonne-4;						//POUR ALLER A GAUCHE
				else taille2 = 0;
				if ((ligne-4) >= 0) taille3 = ligne-4;							//POUR ALLER VERS LE HAUT
				else taille3 = 0;
				if ((ligne+4) < grilles.length) taille4 = ligne+4;				//POUR ALLER VERS LE BAS
				else taille4 = grilles.length;
				String indice = bestChoix(ligne, colonne,taille,taille2,taille3,taille4);

				if (indice == "D") {
					for (int i = colonne; i < taille; i++) {
						if (getTour() == 0 && estOccuper(ligne,i) && estProprio("joueur",ligne,i)) {
							detruitNavire(ligne, i, "joueur");
						} else if (getTour() == 1 && estOccuper(ligne,i) && estProprio("ordinateur",ligne,i)) {
							detruitNavire(ligne, i, "ordinateur");
						}
					}
					setTour((getTour()+1)%2);		//A COMMENTER LORS DU JEU VERSION CONSOLE
				} else if (indice == "G") {
					for (int i = colonne; i > taille2; i--) {
						if (getTour() == 0 && estOccuper(ligne,i) && estProprio("joueur",ligne,i)) {
							detruitNavire(ligne, i, "joueur");
						} else if (getTour() == 1 && estOccuper(ligne,i) && estProprio("ordinateur",ligne,i)) {
							detruitNavire(ligne, i, "ordinateur");
						}
					}
					setTour((getTour()+1)%2);		//A COMMENTER LORS DU JEU VERSION CONSOLE
				} else if (indice == "H") {
					for (int i = ligne; i > taille3; i--) {
						if (getTour() == 0 && estOccuper(i,colonne) && estProprio("joueur",i,colonne)) {
							detruitNavire(i, colonne, "joueur");
						} else if (getTour() == 1 && estOccuper(i,colonne) && estProprio("ordinateur",i,colonne)) {
							detruitNavire(i, colonne, "ordinateur");
						}
					}
					setTour((getTour()+1)%2);		//A COMMENTER LORS DU JEU VERSION CONSOLE
				} else {
					for (int i = ligne; i < taille4; i++) {
						if (getTour() == 0 && estOccuper(i,colonne) && estProprio("joueur",i,colonne)) {
							detruitNavire(i, colonne, "joueur");
						} else if (getTour() == 1 && estOccuper(i,colonne) && estProprio("ordinateur",i,colonne)) {
							detruitNavire(i, colonne, "ordinateur");
						}
					}
					setTour((getTour()+1)%2);		//A COMMENTER LORS DU JEU VERSION CONSOLE
				}
				
			} else {
				if (getTour() == 0 && estOccuper(ligne,colonne) && estProprio("joueur",ligne,colonne)) {
					detruitNavire(ligne, colonne, "joueur");
				} else if (getTour() == 1 && estOccuper(ligne,colonne) && estProprio("ordinateur",ligne,colonne)) {
					detruitNavire(ligne, colonne, "ordinateur");
				} else if (getTour() == 0) {
					grilles[ligne][colonne].setEtat(true);
					System.out.println("Cible ratée en position (" + (ligne+1) + "," + col +") PAR L'ORDINATEUR");
					donneeJeu.add("Cible ratée en position (" + (ligne+1) + "," + col +") PAR L'ORDINATEUR");
			
					String lettre = transformeChiffreEnChaine(colonne);
					grilles[ligne][colonne].setText("?");
					grilles[ligne][colonne].setBackground( new Color( 23, 24, 24) );
					grilles[ligne][colonne].setForeground( Color.WHITE);
					grilles[ligne][colonne].setEnabled( false );
					String msg = "TOUR DE L'ORDINATEUR : \nCible ratée en position (" + (ligne+1) + "," + lettre +") PAR L'ORDINATEUR";
					MyWindow.getJTextArea().setText(msg);
					setTour((getTour()+1)%2);
				} else {
					grilles[ligne][colonne].setEtat(true);
					System.out.println("Cible ratée en position (" + (ligne+1) + "," + col +") PAR LE JOUEUR");
					donneeJeu.add("Cible ratée en position (" + (ligne+1) + "," + col +") PAR LE JOUEUR");
				}
			}
		}
		String msg = "";
		msg = "|*VIE POUR LE JOUEUR : " + GrilleBataille.getJoueurVie() + "\n";
		msg += "|*VIE POUR L'ORDINATEUR : " + GrilleBataille.getOrdinateurVie() + "\n";
		msg += "|*BombeX_ordi : " + GrilleBataille.getBombeCroix()[0] + ", BombeH_ordi : " + GrilleBataille.getBombeHori()[0] + "\n";
		msg += "|*BombeV_ordi : " +GrilleBataille.getBombeVert()[0] + "\n";
		msg += "|*BombeX_joueur : " + GrilleBataille.getBombeCroix()[1] + "\n";
		msg	+= "|*BombeH_joueur: " + GrilleBataille.getBombeHori()[1]+ "\n";
		msg += "|*BombeV_joueur : " +GrilleBataille.getBombeVert()[1] + "\n";
		msg += "|*FIN DE LA PARTIE\n";
		msg += "|*LE GAGNANT EST : " + GrilleBataille.batailleGagner() +"\n";
		MyWindow.getCommentaire().setText(msg);
	}
	/*---------------------------------------------------*/
	public static boolean batailleFinie () {
		return joueurVie == 0 || ordinateurVie == 0;
	}
	public static String batailleGagner () {
		if (joueurVie < ordinateurVie) return "LE VAINQUEUR EST L'ORDINATEUR\n";
		else return "LE VAINQUEUR EST LE JOUEUR\n";
	}
	/*---------------------------------------------------*/
	@SuppressWarnings("resource")
	public int saisieX() {
		Scanner clavier = new Scanner(System.in);
		int x = 0;
		do {
			try {
				System.out.print("	X : ");
				x = clavier.nextInt();
				if (x <= 0 || x > 10) throw new Exception ("La valeur du coordonnée X doit être comprise entre [1,10]");
				break;
			} 
			catch (InputMismatchException e) {
				System.err.println(e.getClass().getName() + " : Erreur de saisie du type de valeur");
			}
			catch (Exception e) {
				System.err.println(e.getClass().getName() + " : " + e.getMessage());
			}
		} while (true);
		return x-1;
	}
	/*---------------------------------------------------*/
	@SuppressWarnings("resource")
	public String saisieY() {
		Scanner clavier = new Scanner(System.in);
		String y = "A";
		do {
			try {
				System.out.print("	Y : ");
				y = clavier.next();
				if (y.matches("[^ABCDEFGHIJ]")) throw new Exception ("La valeur du coordonnée Y doit être comprise entre [A,J] (EN MAJUSCULE)");
				break;
			}
			catch (Exception e) {
				System.err.println(e.getClass().getName() + " : " + e.getMessage());
			}
		} while (true);
		return y;
	}
	/*---------------------------------------------------*/
	@SuppressWarnings("resource")
	public int saisieTypeBombe() {
		Scanner clavier = new Scanner(System.in);
		int bombe = 0;
		if (getBombeHori()[getTour()] > 0) System.out.println("Pour usage de la bombe H		==>		3");
		if (getBombeVert()[getTour()] > 0) System.out.println("Pour usage de la bombe V		==>		2");
		if (getBombeCroix()[getTour()] > 0) System.out.println("Pour usage de la bombe X		==>		1");
		System.out.println("Pour usage de la bombe Classique	==>		0");
		System.out.print("Faites votre choix : ");
		bombe = clavier.nextInt();		
		return bombe;
	}
	/*---------------------------------------------------*/
	public void bombardement (int x, String y) {
		if (!batailleFinie() && x >= 0 && x < 10) {
			if (x >= 0 && x < 10) {
				if (getTour() == 1) {
				//	bombardeAuto();			//POUR VOUVOIR JOUER AUTOMATIQUEMENT SANS AVOIR A SAISIR
					bombardePosition(x,y);
					afficheGrille();
					setTour(0);
				} else {
					bombardeAuto();
					afficheGrille();
					setTour(1);
				}
			}
			System.out.println("|*VIE POUR LE JOUEUR : " + joueurVie);
			System.out.println("|*VIE POUR L'ORDINATEUR : " + ordinateurVie);
			donneeJeu.add("|*VIE POUR LE JOUEUR : " + joueurVie);
			donneeJeu.add("|*VIE POUR L'ORDINATEUR : " + ordinateurVie);
		} else { System.out.println("Les cases doivent être comprises entre [0,9]"); }
		if (joueurVie == 0) {
			System.out.println("|*__*|LE VAINQUEUR EST L'ORDINATEUR|*__*|");
		}
		if (ordinateurVie == 0) {
			System.out.println("(^__^)LE VAINQUEUR EST LE JOUEUR(^__^)");
		}
	}
	/*---------------------------------------------------*/
	public void batailleNavale(int n) throws InputMismatchException, Exception{	// (n le nombre de bombardement pour chacun)
		int k = 0, i = 0, j = 0, choix = 0;
		String lettre = "";
		while (k < n && !batailleFinie()) {
			i = (int) (Math.random()*10);
			j = (int)(Math.random()*10);
			lettre = transformeChiffreEnChaine(j);
			choix = (int) (Math.random()*6); 
			if (choix == 1) setTypeBombe("bombeVert");
			else if (choix == 2) setTypeBombe("bombeHori");
			else if (choix == 3) setTypeBombe("bombeCroix");
			else typeBombe = "bombeClassique";
			if (getTour() == 0) {
				System.out.println("\nLE TOUR DE L'ORDINATEUR :");
				donneeJeu.add("\nLE TOUR DE L'ORDINATEUR :");
				bombardement(i, lettre);
			}
			else {
				System.out.println("\nLE TOUR DU JOUEUR :");
				donneeJeu.add("\nLE TOUR DU JOUEUR :");
				//---------LA PARTIE COMMENTER AVEC LA (FAIRE UN CHOIX...)
				int x = 0; String y = "";
				choix = saisieTypeBombe();
				if (choix == 1) setTypeBombe("bombeCroix");
				else if (choix == 2) setTypeBombe("bombeVert");
				else if (choix == 3) setTypeBombe("bombeHori");
				else setTypeBombe("bombeClassique");
				System.out.println("SAISISSEZ LES COORDONNEES (X,Y) COMPRIS ENTRE [1,10] ET [A,J] RESP : ");
				x = saisieX();
				y = saisieY();
				System.out.println();
				bombardement(x, y);
				//---------ICI EST LA PARTIE A COMMENT AVEC LA (OU BIEN COMMENTER...)
/*##########################____________FAIRE UN CHOIX ALEATOIRE EN DECOMMENTANT______________#################################
				bombardement(i, lettre);
###########################_________OU BIEN COMMENTER, POUR CHOISISR CHOISIR LE TYPE DE BOMBE UTILE_________################################*/
			}
			++k;
			System.out.println("|*BombeX_ordi : " + getBombeCroix()[0] + ", BombeH_ordi : " + getBombeHori()[0] + ", BombeV_ordi : " +getBombeVert()[0] + "\n");
			System.out.println("|*BombeX_joueur : " + getBombeCroix()[1] + ", BombeH_joueur: " + getBombeHori()[1] + ", BombeV_joueur : " +getBombeVert()[1] + "\n");
		}
		System.out.println(batailleGagner());
		donneeJeu.add(batailleGagner() + "________FIN DE LA PARTIE________");
	}
}
