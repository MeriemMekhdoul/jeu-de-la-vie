

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

				Environnement e = new Environnement(3,3);
				Ordonnanceur o = new Ordonnanceur(Simulateur.simulationSpeed, e);

				Simulateur s = new Simulateur(e,o);

				FenetrePrincipale fenetre = new FenetrePrincipale(e,s);
				fenetre.setVisible(true);
				e.addObserver(fenetre);

				o.start();

				/*// Définition des positions pour le sous-environnement
				Position p1 = new Position(2, 2);
				Position p2 = new Position(5, 5);

				// Extraction du sous-environnement
				Environnement sousEnv = e.getSousEnv(p1, p2);

				// Affichage du sous-environnement
				System.out.println("Sous-environnement extrait:");
				for (int i = 0; i < sousEnv.getSizeX(); i++) {
					for (int j = 0; j < sousEnv.getSizeY(); j++) {
						System.out.print(sousEnv.getCase(i, j).getState() ? "1 " : "0 ");
					}
					System.out.println();
				}

				// Recharge du sous-environnement à une nouvelle position (7, 7)
				Position nouvellePosition = new Position(6, 6);
				e.setSousEnv(sousEnv, nouvellePosition);

				// Affichage de l'environnement principal après la modification
				System.out.println("\nEnvironnement principal après insertion:");
				for (int i = 0; i < e.getSizeX(); i++) {
					for (int j = 0; j < e.getSizeY(); j++) {
						System.out.print(e.getCase(i, j).getState() ? "1 " : "0 ");
					}
					System.out.println();
				}*/
			}
		});

    }

}
