

import modele.Environnement;
import modele.Ordonnanceur;
import vue_controleur.Simulateur;
import vue_controleur.FenetrePrincipale;

import javax.swing.SwingUtilities;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new Runnable(){
			public void run(){

				Environnement e = new Environnement(4,4);
				Ordonnanceur o = new Ordonnanceur(Simulateur.simulationSpeed, e);

				Simulateur s = new Simulateur(e,o);

				FenetrePrincipale fenetre = new FenetrePrincipale(e,s);
				s.setFenetre(fenetre);
				fenetre.setVisible(true);
				e.addObserver(fenetre);

				o.start();
			}
		});

    }

}
