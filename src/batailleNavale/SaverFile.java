package batailleNavale;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SaverFile {
	private File fichier;
	
	/*--------------------------------------------------------------------------------------------------------*/
	public SaverFile (String cheminFichier) {
		this.setFichier(creeFichier(cheminFichier));
	}
	/*--------------------------------------------------------------------------------------------------------*/
	public File creeFichier (String cheminFichier) {
		File fileName = null;
		try {
			fileName = new File (cheminFichier);
		} catch (Exception e) {
			System.err.println ( e.getClass().getName() + " : " + e.getMessage());
		}
		return fileName;
	}
	/*--------------------------------------------------------------------------------------------------------*/
	public void sauver (List<String> list, String nom) throws IOException {
		try (PrintWriter out = new PrintWriter ( new FileWriter (nom)))
		{
			for (String d : list) {
				out.println(d);
			}
		}
	}
	/*--------------------------------------------------------------------------------------------------------*/
	public Collection<String> lire (String nomFichier) throws IOException {
		ArrayList<String> donnee = new ArrayList<String> ();
		try ( BufferedReader br = new BufferedReader ( new FileReader (nomFichier)))
		{
			String contenu = "";
			while ((contenu = br.readLine()) != null) {
				donnee.add(contenu);
			}
		}
		
		return donnee;
	}
	/*--------------------------------------------------------------------------------------------------------*/
	public File getFichier() { return fichier; }
	public void setFichier(File fichier) { this.fichier = fichier; }

}
