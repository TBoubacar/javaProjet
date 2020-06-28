package batailleNavale;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;


public class MyWindow extends JFrame {
	private static final long serialVersionUID = 1494939705385334371L;
	private static JTextArea commentaire;
	private static JTextArea jtextArea;
	/*____________________________________________________CONSTRUCTEUR__________________________________________________________*/
	public MyWindow () {
		super ( "Bataille Navale TBoubacar I3 " );
		this.setLayout( new BorderLayout());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize( 1000, 550);
		this.setLocationRelativeTo( null );
		commentaire = new JTextArea(7,20);
		jtextArea = new JTextArea(2,20);
	}
	/*------------------------------------------------GETTERS && SETTERS----------------------------------------------------------*/
	public static JTextArea getJTextArea () { return jtextArea; }
	public static void setJTextArea (JTextArea ta) { jtextArea = ta;}
	public static JTextArea getCommentaire () { return commentaire; }
	public static void setCommentaire (JTextArea com) { commentaire = com;}
	

	/*####################################################################################
	 * 								PROGRAMME PRINCIPALE 
	 *####################################################################################
	 */
	public static void main(String[] args) throws InputMismatchException, Exception {
		GrilleBataille gBataille = new GrilleBataille();
/*						EXEMPLE D'AFFICHAGE DANS LA CONSOLE
 * ###########################################################################
 *    		    A   B   C   D   E   F   G   H   I   J
		   1    @   @   @   @   @   @   @  JPA  @   @  
		   2   JCT  @   @   @   @   @   @  JPA  @   @  
		   3   JCT  @   @  JCr  @   @   @  JPA  @   @  
		   4   JCT  @   @  JCr  @   @   @  JPA  @   @  
		   5    @  OCT  @  JCr  @   @   @  JPA  @   @  
		   6    @  OCT  @  JCr  @   @   @   @   @   @  
		   7    @  OCT JTo  @   @  OPA OPA OPA OPA OPA 
		   8    @   @  JTo  @   @   @   @   @   @   @  
		   9   OTo  @   @  OCT OCT OCT  @   @   @   @
		  10   OTo  @   @   @   @   @   @   @   @   @  
		   
	VOUS ALLEZ DES FOIS VOIR DES NOTATIONS COMME CECI {OX X OX OX}
	CELA SIGNIFIE TOUT SIMPLEMENT QUE LE NAVIRE A ETE FRAPPE DE PLEIN FOYET
	A L'ENDROIT OU IL EST MARQUE X ET LES L'AUTRE MARQUAGE INDIQUE JUSTE QUE 
	LE NAVIRE A ETE ENDOMMAGE MAIS PAS TOTALEMENT DETRUIT. SEUL LES CELLULES
	MARQUE PAR LA CROIX X SONT DETRUITES.
 * ###########################################################################
*/
/*		gBataille.batailleNavale(10);		// 10 CORRESPOND AU NOMBRE DE BOMBARDEMENTS ACCORDE (5 A CHACUN)
 * 		POUR POUVOIR JOUER EN CONSOLE IL FAUDRAIT RECOPIER AVANT LE COMMENTAIRE EXEMPLE LA  PLUS GRAND PARTIE DU CODE DANS LE BOUTON PLAY
 * 		CAR CE BOUT DE CODE PERMET D'INITIALISER LE JEU
 * 		ON UTILISERA LA FONCTION BATAILLE NAVALE POUR JOUER 
 */
		System.out.println("\n_____________________________DEBUT DU JEU SUR L'INTERFACE GRAPHIQUE_____________________________\n");
		
		MyWindow window = new MyWindow();
		JPanel bn = new JPanel ( new FlowLayout () );
		JScrollPane jp = new JScrollPane(commentaire);
		JScrollPane jt = new JScrollPane(jtextArea);
		
		String info = "";
		info += "###############################\n";
		info += "# APPUYER SUR PLAY\n";
		info += "# POUR DEMARRER LA PARTIE\n";
		info += "# ET DETERMINER LES DONNEES\n";
		info += "# DANS LA CONSOLE (Je vous prie)\n";
		info += "###############################\n";
		commentaire.setText(info);
		
		JButton btn = new JButton ( "PLAY" );
		btn.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				btn.setBackground( new Color(34, 179, 191) );
				//-------POUR JOUER EN CONSOLE REPRENER LE CODE A PARTIR DE LA
				System.out.println("-------CREATION DES NAVIRES DU JOUEUR-------");
				GrilleBataille.getDonneJeu().add("---DEBUT DU JEU SUR L'INTERFACE GRAFIQUE---");
				btn.setText( "JOUER..." );
				String info = "";

				int n = 4;		//LE NOMBRE DE NAVIRE UTILE
				int i = 1;
				/*------------------------------------------------------------------------------------------------------------------------------------*/
				do {
					System.out.println("POUR UNE INSERTION MANUELLE DE VOS NAVIRES, TAPEZ		====>		1\n" +
										"POUR UNE INSERTION AUTOMATIQUE DE VOS NAVIRES, TAPEZ		====>		2");
					try
					{
						@SuppressWarnings("resource")
						Scanner clavier = new Scanner(System.in);
						System.out.print("QUEL EST VOTRE CHOIX ? (compris entre [1,2]) : ");
						i = clavier.nextInt();
						if (i >= 3 || i <= 0) throw new Exception ("Le nombre de choix est limité à 1 et 2");
						break;
					}
					catch(InputMismatchException e1) { System.err.println("Erreur de saisi (La valeur doit être un entier)");}
					catch(Exception e1) { System.err.println(e1.getClass().getName() + " : " + e1.getMessage());}
					finally { System.out.println(); }
				} while (true);
				/*------------------------------------------------------------------------------------------------------------------------------------*/
				if (i == 2) {
					i = 0;
					while (i < n) {
						String type = "";
						if (i == 0) {
							type = "porte-avions";
							GrilleBataille.setJoueurVie(GrilleBataille.getJoueurVie()+5);
						}
						else if (i == 1) {
							type = "croiseurs";
							GrilleBataille.setJoueurVie(GrilleBataille.getJoueurVie()+4);
						}
						else if (i == 2) {
							type = "contre-torpilleurs";
							GrilleBataille.setJoueurVie(GrilleBataille.getJoueurVie()+3);
						}
						else if (i == 3){
							type = "torpilleurs";
							GrilleBataille.setJoueurVie(GrilleBataille.getJoueurVie()+2);
						}
						Navire navire = new Navire("joueur",type);
						gBataille.ajoutNavireAutomatique(navire);
						System.out.println("Le navire de type " + type + " a été créé pour le Joueur");
						++i;
					} System.out.println();
				} else {
					/*##########################################################################################################
					 * LE PRINCIPE DE L'INSERTION EST SIMPLE DE VOTRE NAVIRE EST SIMPLE. IL SUFFIT TOUT SIMPLEMENT DE DETERMINER 
					 *  LES COORDONNEES (x,y) AINSI QUE LE TYPE DE NAVIRE ET SON ALIGNEMENT DANS LA GRILLE (HORIZONTAL OU VERTICAL)
					 *##########################################################################################################
					 */
					i = 0;
					String y = "", align = "V";
					int x = 0, k = 0;
					while (i < n) {
						System.out.println("POUR UN ALIGNEMENT VERTICAL TAPEZ		===>		V\n"+"POUR UN ALIGNEMENT HORIZONTAL TAPEZ		===>		H");
						@SuppressWarnings("resource")
						Scanner clavier = new Scanner(System.in);
						int pa = 0, cr = 0, ct = 0, to = 0;		//CETTE MANOEUVRE M'ASSURE QUE L'UTILISATEUR NE TRICHERAIT PAS POUR PRENDRE 2 FOIS UN MEME NAVIRE
						do {
							try {
								System.out.print("ALIGNEMENT (EN MAJUSCULE JE VOUS PRIE) : ");
								align = clavier.next();
								if (align.matches("[^VH]")) throw new Exception ("La valeur de l'alignement doit être soit V ou soit H");
								break;
							}
							catch (Exception e1) {
								System.err.println(e1.getClass().getName() + " : " + e1.getMessage());
							}
						} while (true);
						System.out.println("SAISISSEZ LES COORDONNEES (X,Y) COMPRIS ENTRE [1,10] ET [A,J] (Ex : (1,A),...,(10,J) TOUT EN MAJUSCULE) : ");
						x = gBataille.saisieX();
						y = gBataille.saisieY();
						System.out.println("LES CHOIX DE NAVIRE POSSIBLE (nombre de cases correspond au nombre total de votre vie) : \n" +
								"Pour les porte-avions(PA)		===>			5 (cases)\n" + 
								"Pour les croiseurs(Cr)			===>			4 (cases)\n" + 
								"Pour les contre-torpilleurs(CT)		===>			3 (cases)\n" + 
								"Pour les torpilleurs(To)		===>			2 (cases)\n"+
								"SI VOUS TENTEZ DE TRICHER EN DISPOSANT DE 2 NAVIRES DE MEME TYPE, VOUS EN SUBIREZ LES CONSEQUENCES (^-^)");
						do {
							try
							{
								clavier = new Scanner(System.in);
								System.out.print("FAITES VOTRE CHOIX POUR LE TYPE (ex : 5 pour le porte-Avion) : ");
								k = clavier.nextInt();
								if (k > 5 || k <= 1 || pa > 1 || cr > 1 || ct > 1 || to > 1) throw new Exception ("ON NE DISPOSE QUE DE 4 TYPES DONT LES VALEURS POSSIBLES SONT (2,3,4,5). VOUS N'AVEZ ACCES QU'A UN EXEMPLAIRE POUR CHAQUE TYPE (°_°)");
								break;
							}
							catch(InputMismatchException e1) { System.err.println("Erreur de saisi (La valeur doit être un entier)");}
							catch(Exception e1) { System.err.println(e1.getClass().getName() + " : " + e1.getMessage());}
							finally { System.out.println(); }
						} while (true);
						String type = "";
						if (k == 5) {
							type = "porte-avions"; ++pa;
							GrilleBataille.setJoueurVie(GrilleBataille.getJoueurVie()+5);	
						}
						else if (k == 4) {
							type = "croiseurs";	++cr;
							GrilleBataille.setJoueurVie(GrilleBataille.getJoueurVie()+4);
						}
						else if (k == 3) {
							type = "contre-torpilleurs"; ++ct;
							GrilleBataille.setJoueurVie(GrilleBataille.getJoueurVie()+3);
						}
						else {
							type = "torpilleurs"; ++to;
							GrilleBataille.setJoueurVie(GrilleBataille.getJoueurVie()+5);
						}
						Navire navire = new Navire("joueur",type);
						gBataille.ajoutNavireManuel(navire, align, x, y);
						++i;
					}
				}
				/*------------------------------------------------------------------------------------------------------------------------------------*/
				System.out.println("-------CREATION DES NAVIRES ENNEMIES-------");
				i = 0;
				while (i < n) {
					String type = "";
					if (i == 0) {
						type = "porte-avions";
						GrilleBataille.setOrdinateurVie(GrilleBataille.getOrdinateurVie()+5);
					}
					else if (i == 1) {
						type = "croiseurs";
						GrilleBataille.setOrdinateurVie(GrilleBataille.getOrdinateurVie()+4);
					}
					else if (i == 2) {
						type = "contre-torpilleurs";
						GrilleBataille.setOrdinateurVie(GrilleBataille.getOrdinateurVie()+3);
					}
					else {
						type = "torpilleurs";
						GrilleBataille.setOrdinateurVie(GrilleBataille.getOrdinateurVie()+2);
					}
					Navire navire = new Navire("ordinateur",type);
					gBataille.ajoutNavireAutomatique(navire);
					System.out.println("Le navire de type " + type + " a été créé pour l'ordinateur");
					++i;
				}
				gBataille.choixDifficulte();
				System.out.println("BIEN VOUS ETES PRET A DEMARRER DANS L'INTERFACE (^_^)\n");
				gBataille.afficheGrille();
				//----------------VOUS DEVEZ PRENDRE LE CODE JUSQU'ICI POUR POUVOIR JOUER DANS LA CONSOLE
				info = "";
				info += "#############################################\n";
				info += "# DEBUT DU JEU : \n";
				info += "# VOS NAVIRES SONT : VERT\n";
				info += "# NIVEAU DE VIE JOUEUR : " + GrilleBataille.getJoueurVie() +"\n";
				info += "# NIVEAU DE VIE ORDINATEUR : " + GrilleBataille.getOrdinateurVie()+"\n";
				info += "# NIVEAU DE DIFFICULTE : " + gBataille.getNiveau()+"\n";
				info += "# LE JEU PEUT COMMENCER \n";
				info += "# ALLEZ-Y\n";
				info += "#############################################\n";
				commentaire.setText(info);


			}
		});
		btn.addMouseListener( new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {
				if (btn.isEnabled()) {
					btn.setBackground( new Color(253, 203, 1) );
					btn.setForeground( Color.RED );
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (btn.isEnabled()) {
					btn.setBackground( new Color(34, 179, 191) );	
					btn.setForeground( null );
				}
			}
		});
		
		JButton btnV = new JButton ("BombeV");
		btnV.setBackground( new Color(11, 11, 11) );
		btnV.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String info = "DEMANDE D'USAGE DE LA BOMBE V PAR LE JOUEUR\n";
				jtextArea.setText(info);
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
				GrilleBataille.getDonneJeu().add("DEMANDE D'USAGE DE LA BOMBE V PAR LE JOUEUR");
				btnV.setBackground( Color.LIGHT_GRAY);
				btnV.setText( "Utilisée.." );
				btnV.setEnabled( false );
				GrilleBataille.setTypeBombe("bombeVert");
			}
		});
		btnV.addMouseListener( new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {
				if (btnV.isEnabled())
				btnV.setBackground( new Color(253, 203, 1) );				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (btnV.isEnabled())
				btnV.setBackground( new Color(11, 11, 11) );
			}
			
		});
		JButton btnH = new JButton ("BombeH");
		btnH.setBackground( new Color(11, 11, 11) );
		btnH.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String info = "DEMANDE D'USAGE DE LA BOMBE H PAR LE JOUEUR\n";
				jtextArea.setText(info);
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
				GrilleBataille.getDonneJeu().add("DEMANDE D'USAGE DE LA BOMBE H PAR LE JOUEUR");
				btnH.setBackground( Color.LIGHT_GRAY);
				btnH.setText( "Utilisée.." );
				btnH.setEnabled( false );
				GrilleBataille.setTypeBombe("bombeHori");
			}
		});
		btnH.addMouseListener( new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {
				if (btnH.isEnabled())
				btnH.setBackground( new Color(253, 203, 1) );
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (btnH.isEnabled())
				btnH.setBackground( new Color(11, 11, 11) );
			}
			
		});
		
		JButton btnX = new JButton ("BombeX");
		btnX.setBackground( new Color(11, 11, 11) );
		btnX.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String info = "DEMANDE D'USAGE DE LA BOMBE X PAR LE JOUEUR\n";
				jtextArea.setText(info);
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
				GrilleBataille.getDonneJeu().add("DEMANDE D'USAGE DE LA BOMBE X PAR LE JOUEUR");
				btnX.setBackground( Color.LIGHT_GRAY);
				btnX.setText( "Utilisée.." );
				btnX.setEnabled( false );
				GrilleBataille.setTypeBombe("bombeCroix");
			}
		});
		btnX.addMouseListener( new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {
				if (btnX.isEnabled())
				btnX.setBackground( new Color(253, 203, 1) );				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (btnX.isEnabled())
				btnX.setBackground( new Color(11, 11, 11) );
			}
			
		});	
		
		JButton sauvReset = new JButton ("SAVE/RESET");
		sauvReset.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String info = "";
				info += "#################################\n";
				info += "#   SAUVEGARDE DES DONNEES\n";
				info += "#   PARTIE TERMINER\n";
				info += GrilleBataille.batailleGagner();
				info += "#################################\n";
				info += "#################################\n";
				info += "   FIN DE LA PARTIE  \n";
				info += "#################################\n";
				commentaire.setText(info);
				info = "#	MERCI ET AUREVOIR  #\n";
				jtextArea.setText(info+"(^_^)");
				sauvReset.setBackground( Color.LIGHT_GRAY);
				sauvReset.setText( "sauvegardé..." );
				sauvReset.setEnabled( false );
				gBataille.initGBataille();
				try {
					SaverFile f = new SaverFile("monFichierJava.txt");
					f.sauver(GrilleBataille.getDonneJeu(), "monFichierJava.txt");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		sauvReset.addMouseListener( new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {
				if (sauvReset.isEnabled()) {
					sauvReset.setBackground( new Color(253, 203, 1) );
					sauvReset.setForeground( Color.RED );
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (sauvReset.isEnabled()) {
					sauvReset.setBackground( new Color(243, 62, 9) );
					sauvReset.setForeground( null );
				}
			}
			
		});

		bn.add(btnH);
		bn.add(btnV);
		bn.add(btnX);
		bn.add(jp);
		bn.add(sauvReset);
		bn.add(btn);
		
		/*_____________GESTION DES COULEURS ET TAILLE_________________*/
		commentaire.setPreferredSize( new Dimension(175,45) );
		commentaire.setAlignmentX(CENTER_ALIGNMENT);
		commentaire.setForeground( Color.WHITE);
		commentaire.setBackground( Color.BLACK);
		jp.setAlignmentX(CENTER_ALIGNMENT);
		jtextArea.setAlignmentX(CENTER_ALIGNMENT);
		jtextArea.setAlignmentY(CENTER_ALIGNMENT);
		jtextArea.setForeground( Color.WHITE);
		jtextArea.setBackground( Color.BLACK);
		jtextArea.setPreferredSize( new Dimension(145,45) );
		btn.setBackground( new Color(34, 179, 191) );
		btn.setPreferredSize( new Dimension (225,45) );
		btnV.setPreferredSize( new Dimension (215,45) );
		btnH.setPreferredSize( new Dimension (215,45) );
		btnX.setPreferredSize( new Dimension (215,45) );
		btnX.setForeground( Color.WHITE );
		btnH.setForeground( Color.WHITE );
		btnV.setForeground( Color.WHITE );
		bn.setPreferredSize( new Dimension(275,0) );
		bn.setBackground(new Color(43, 127, 130) );
		sauvReset.setPreferredSize( new Dimension (225,35) );
		sauvReset.setBackground( new Color(243, 62, 9) );
		
		/*_____________GESTION DE L'AFFICHAGE DE LA FENETRE_________________*/
		window.add(btn, BorderLayout.NORTH);
		window.add(bn,BorderLayout.WEST);
		window.add(jt, BorderLayout.SOUTH);
		window.add(gBataille, BorderLayout.CENTER);
		UIManager.setLookAndFeel( new NimbusLookAndFeel() );
		window.setVisible( true );
	}
}
