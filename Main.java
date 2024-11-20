

import modele.Environnement;
import modele.Ordonnanceur;
import modele.Simulateur;
import vue_controleur.FenetrePrincipale;

import javax.swing.SwingUtilities;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable(){
			public void run(){

				Environnement e = new Environnement(15, 15);
				Ordonnanceur o = new Ordonnanceur(3000, e);

				Simulateur s = new Simulateur(e,o);

				FenetrePrincipale fenetre = new FenetrePrincipale(e,s);
				fenetre.setVisible(true);
				e.addObserver(fenetre);

				o.start();

			}
		});

    }

}
