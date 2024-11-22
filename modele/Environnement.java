package modele;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class Environnement extends Observable implements Runnable {
    private final Case[][] tab;
    private final Map<Case, Position> mapDonnees;
    private final int sizeX;
    private final int sizeY;

    public Environnement(int _sizeX, int _sizeY) {

        sizeX = _sizeX;
        sizeY = _sizeY;

        mapDonnees = new HashMap<>();
        tab = new Case[sizeX][sizeY];

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                Case c = new Case(this);
                tab[i][j] = c;
                mapDonnees.put(c, new Position(i, j));
            }
        }
        //System.out.println("print hashmap");
        // Pour afficher le contenu de la HashMap
        for (Map.Entry<Case, Position> entry : mapDonnees.entrySet()) {
            Case key = entry.getKey();
            Position value = entry.getValue();
            //System.out.println("Position: " + value + "     etat = " + key.getState());
        }

    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void rndState() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                tab[i][j].rndState();
            }
        }
    }
    public void blankState() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                tab[i][j].setState(false);
            }
        }
    }
    public void nextState() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                //System.out.println();
                tab[i][j].nextState();
            }
        }
    }

    public void updateState() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                //System.out.println();
                tab[i][j].updateState();
            }
        }
    }

    public Position getPos(Case cell){
        return mapDonnees.get(cell);
    }
    public boolean getState(int x, int y) {
        return tab[x][y].getState();
    }
    public Case getCase(int x, int y) {
        return tab[x][y];
    }

    public int getNbCases(Case source) {
        int somme = 0;
        for (Direction d : Direction.values()) {
            Case c = getCase(source,d);
            if (c!= null && c.getState()) {
                somme++;
            }
        }
        return somme;
    }

    /**
     * Rechercher la case voisine de la case source selon la direction demandée, en ne dépassant pas les limites du tableau
     * @param source la case initiale qui fait appel à cette fonction
     * @param d la direction de la case voisine
     * @return la case voisine
     */

    public Case getCase(Case source, Direction d) {
        Position pos = mapDonnees.get(source); // Obtenir la position actuelle
        if (pos == null) {
            return null; //si la position n'est pas trouvée
        }

        int x = pos.getX();
        int y = pos.getY();

        switch (d) {
            case h -> { // Haut
                if (y > 0) return tab[x][y - 1];
            }
            case hd -> { // Haut-droite
                if (y > 0 && x < sizeX - 1) return tab[x + 1][y - 1];
            }
            case d -> { // Droite
                if (x < sizeX - 1) return tab[x + 1][y];
            }
            case db -> { // Bas-droite
                if (x < sizeX - 1 && y < sizeY - 1) return tab[x + 1][y + 1];
            }
            case b -> { // Bas
                if (y < sizeY - 1) return tab[x][y + 1];
            }
            case bg -> { // Bas-gauche
                if (x > 0 && y < sizeY - 1) return tab[x - 1][y + 1];
            }
            case g -> { // Gauche
                if (x > 0) return tab[x - 1][y];
            }
            case gh -> { // Haut-gauche
                if (x > 0 && y > 0) return tab[x - 1][y - 1];
            }
        }

        return null; // Retourner null si hors limites
    }


    @Override
    public void run() {
        //rndState();
        nextState();
        updateState();
        // notification de l'observer
        setChanged();
        notifyObservers();
        //System.out.println("/////////////////////////////////////////////");
    }
}
