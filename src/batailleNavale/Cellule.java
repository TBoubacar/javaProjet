package batailleNavale;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Cellule extends JButton {
	private static final long serialVersionUID = 5510766862510660052L;
	private int row;				//DETERMINE LES COORDONNEES DU NAVIRE EN X
	private int col;				//DETERMINE LES COORDONNEES DU NAVIRE EN Y
	private boolean etat;			//DETERMINE SI lA PARTIE DU NAVIRE PRESENTE DANS CETTE CELLULE EST DETRUITE OU PAS SUR LA GRILLEBATAILLE (CHAMP DE BATAILLE)
	private Navire navire;			//DETERMINE LE NAVIRE EST PRESENT DANS LA CELLULE
	/*-------------------------------------------------------------*/
	public Cellule(int row, int col) {
		this.row = row;
		this.col = col;
		this.navire = null;
		this.etat = false;
		this.addActionListener(new BtnListener(row, col));
		this.initBouton();
	}
	/*-------------------------------------------------------------*/
	public void initBouton () {		//UTILISER DANS LE CONSTRUCTEUR DE LA CLASSE GrilleBataille POUR GENERER LES BORDURES
		if (this.row == -1 && this.col == -1) {
			this.setBackground(Color.DARK_GRAY); 
			this.setEnabled( false );
		} else if (this.row == -1 && this.col == 0) {
			this.setBackground(new Color (243, 242, 238)); 
			this.setText("A");
			this.setEnabled( false );
		} else if (this.row == -1 && this.col == 1) {
			this.setBackground(new Color (243, 242, 238)); 
			this.setText("B");
			this.setEnabled( false );
		} else if (this.row == -1 && this.col == 2) {
			this.setBackground(new Color (243, 242, 238)); 
			this.setText("C");
			this.setEnabled( false );
		} else if (this.row == -1 && this.col == 3) {
			this.setBackground(new Color (243, 242, 238)); 
			this.setText("D");
			this.setEnabled( false );
		} else if (this.row == -1 && this.col == 4) {
			this.setBackground(new Color (243, 242, 238)); 
			this.setText("E");
			this.setEnabled( false );
		} else if (this.row == -1 && this.col == 5) {
			this.setBackground(new Color (243, 242, 238)); 
			this.setText("F");
			this.setEnabled( false );
		} else if (this.row == -1 && this.col == 6) {
			this.setBackground(new Color (243, 242, 238)); 
			this.setText("G"); 
			this.setEnabled( false );
		} else if (this.row == -1 && this.col == 7) {
			this.setBackground(new Color (243, 242, 238)); 
			this.setText("H");
			this.setEnabled( false );
		} else if (this.row == -1 && this.col == 8) {
			this.setBackground(new Color (243, 242, 238)); 
			this.setText("I");
			this.setEnabled( false );
		} else if (this.row == -1 && this.col == 9) {
			this.setBackground(new Color (243, 242, 238)); 
			this.setText("J");
			this.setEnabled( false );
		} else if (this.row == 0 && this.col == -1) {
			this.setBackground(new Color (243, 242, 238)); 
			this.setText("1");
			this.setEnabled( false );
		}else if (this.row == 1 && this.col == -1) {
			this.setBackground(new Color (243, 242, 238)); 
			this.setText("2");
			this.setEnabled( false );
		} else if (this.row == 2 && this.col == -1) {
			this.setBackground(new Color (243, 242, 238)); 
			this.setText("3");
			this.setEnabled( false );
		} else if (this.row == 3 && this.col == -1) {
			this.setBackground(new Color (243, 242, 238)); 
			this.setText("4");
			this.setEnabled( false );
		} else if (this.row == 4 && this.col == -1) {
			this.setBackground(new Color (243, 242, 238)); 
			this.setText("5");
			this.setEnabled( false );
		} else if (this.row == 5 && this.col == -1) {
			this.setBackground(new Color (243, 242, 238)); 
			this.setText("6");
			this.setEnabled( false );
		} else if (this.row == 6 && this.col == -1) {
			this.setBackground(new Color (243, 242, 238)); 
			this.setText("7");
			this.setEnabled( false );
		} else if (this.row == 7 && this.col == -1) {
			this.setBackground(new Color (243, 242, 238)); 
			this.setText("8");
			this.setEnabled( false );
		} else if (this.row == 8 && this.col == -1) {
			this.setBackground(new Color (243, 242, 238)); 
			this.setText("9");
			this.setEnabled( false );
		} else if (this.row == 9 && this.col == -1) {
			this.setBackground(new Color (243, 242, 238)); 
			this.setText("10");
			this.setEnabled( false );
		} else {
			this.setBackground( new Color(189, 195, 200)); 
			this.setText("*");
		}
	}
	/*______________________________________________GESTIONNAIRE DES EVENEMENTS______________________________________________*/
	public boolean getEtat() { return this.etat; }
	public void setEtat(boolean etat) { this.etat = etat; }
	public int getRow() {	return row; }
	public void setCol(int x) { this.row = x; }
	public Navire getNavire() { return navire; }
	public void setNavire(Navire navire) {
		this.navire = navire;
		if (this.navire.getProprietaire().equals("joueur")) {
			this.setBackground( new Color(35, 104, 32) );
			this.setEnabled( false );
			this.setText("J"+this.navire.getInitial());
		}
		else {														//SI VOUS SOUHAITEZ REPERER L'ENNEMIE, DECOMMENTEZ ICI
		//	this.setBackground(Color.WHITE);
			this.setText("*");
		}
	}
	
	/*---------------------------------------------------------------------------------------------------------------------*/
	class BtnListener implements ActionListener {
		final int ligne;
		final int colonne;

		/*------------------CONSTRUCTEUR--------------------*/
		public BtnListener(int ligne, int colonne) {
			this.ligne = ligne;
			this.colonne = colonne;
		}

		@Override
		public void actionPerformed(ActionEvent e) {			
			if(navire != null) {
				if (navire.getProprietaire().equals("joueur") && !etat) {
					btnJoueurEvent(e);
				}
				else if (navire.getProprietaire().equals("ordinateur") && !etat) {
					btnOrdiEvent (e);
				}
			} else {
				btnRASEvent(e);
			}
			
		}
		protected void btnJoueurEvent (ActionEvent e) {			//QUAND C'EST LE NAVIRE DU JOUEUR QUI EST TOUCHE
			if (navire.getProprietaire().equals("joueur")) {
				setText("JX");
				setBackground( new Color(168, 18, 18) );
				setEnabled( false );
				etat = true;
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
		}
		protected void btnOrdiEvent (ActionEvent e) {			//QUAND C'EST LE NAVIRE DE L'ORDINATEUR QUI EST TOUCHE
			if (navire.getProprietaire().equals("ordinateur")) {
				String lettre = GrilleBataille.transformeChiffreEnChaine(colonne);
				GrilleBataille.bombardePosition(this.ligne, lettre);
				String msg = "TOUR DU JOUEUR : \nCible atteint #" + navire.getTypeNavire() + "# en position (" + (ligne+1) + "," + lettre +") PAR LE JOUEUR\n";
				MyWindow.getJTextArea().setText(msg);
				System.out.println(msg);
				setText("OX");
				setBackground( new Color(177, 70, 16) );
				setEnabled( false );
				etat = true;
				GrilleBataille.setTour((GrilleBataille.getTour()+1)%2);
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
				if (GrilleBataille.batailleFinie()) {
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
				GrilleBataille.bombardeAuto();			
			}
		}
		protected void btnRASEvent(ActionEvent e) {
			String lettre = GrilleBataille.transformeChiffreEnChaine(this.colonne);
			GrilleBataille.bombardePosition(this.ligne, lettre);
			String msg = "";
			msg = "TOUR DU JOUEUR : \nCible ratée en position (" + (ligne+1) + "," + lettre +") PAR LE JOUEUR\n";
			MyWindow.getJTextArea().setText(msg);
			System.out.println(msg);
			setText("?");
			setBackground( Color.DARK_GRAY);
			setEnabled( false );
			etat = true;
			GrilleBataille.setTour((GrilleBataille.getTour()+1)%2);
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
			if (GrilleBataille.batailleFinie()) {
				msg = "|*VIE POUR LE JOUEUR : " + GrilleBataille.getJoueurVie() + "\n";
				msg += "|*VIE POUR L'ORDINATEUR : " + GrilleBataille.getOrdinateurVie() + "\n";
				msg += "|*BombeX_ordi : " + GrilleBataille.getBombeCroix()[0] + ", BombeH_ordi : " + GrilleBataille.getBombeHori()[0] + "\n";
				msg += "|*BombeV_ordi : " +GrilleBataille.getBombeVert()[0] + "\n";
				msg += "|*BombeX_joueur : " + GrilleBataille.getBombeCroix()[1] + ", BombeH_joueur: " + GrilleBataille.getBombeHori()[1]+ "\n";
				msg += "|*BombeV_joueur : " +GrilleBataille.getBombeVert()[1] + "\n";
				msg += "|*FIN DE LA PARTIE\n";
				msg += "|*LE GAGNANT EST : " + GrilleBataille.batailleGagner() +"\n";
				MyWindow.getCommentaire().setText(msg);
			}
			GrilleBataille.bombardeAuto();			
		}
	}
	
}
