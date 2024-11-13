package modele;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class Environnement extends Observable implements Runnable {
    private Case[][] tab;
    private Map<Case,Integer> mapDonnees;

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    private int sizeX, sizeY;

    public boolean getState(int x, int y) {
        return tab[x][y].getState();
    }

    public Case getCase(Case source, Direction d) {
        // TODO : une case utilisera obligatoirement cette fonction pour percevoir son environnement, et définir son état suivant
        return null;
    }

    public int getNbCases(Case source) {
        return 0;
    }
    public Environnement(int _sizeX, int _sizeY) {

        sizeX = _sizeX;
        sizeY = _sizeY;

        mapDonnees = new HashMap<>();
        tab = new Case[sizeX][sizeY];

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                Case c = new Case(this);
                tab[i][j] = c;
                mapDonnees.put(c,i);
            }

        }

    }

    public void rndState() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                tab[i][j].rndState();

            }
        }

    }

    @Override
    public void run() {
        rndState();
        // notification de l'observer
        setChanged();
        notifyObservers();
    }
}
