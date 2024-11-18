

import modele.Environnement;
import modele.Ordonnanceur;
import vue_controleur.FenetrePrincipale;

import javax.swing.SwingUtilities;

import static java.lang.Thread.sleep;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable(){
			public void run(){

				Environnement e = new Environnement(4, 4);

				FenetrePrincipale fenetre = new FenetrePrincipale(e);
				fenetre.setVisible(true);
				e.addObserver(fenetre);

				Ordonnanceur o = new Ordonnanceur(3000, e);
				o.start();

			}
		});

    }

}
