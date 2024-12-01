

import modele.Environnement;
import modele.Ordonnanceur;
import modele.Position;
import modele.Simulateur;
import vue_controleur.FenetrePrincipale;

import javax.swing.SwingUtilities;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new Runnable(){
			public void run(){

				Environnement e = new Environnement(20,20);
				Ordonnanceur o = new Ordonnanceur(Simulateur.simulationSpeed, e);

				Simulateur s = new Simulateur(e,o);

				FenetrePrincipale fenetre = new FenetrePrincipale(e,s);
				fenetre.setVisible(true);
				e.addObserver(fenetre);

				o.start();

			}
		});

    }

}
