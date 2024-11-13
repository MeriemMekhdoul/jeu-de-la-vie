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
        System.out.println("print hashmap");
        // Pour afficher le contenu de la HashMap
        for (Map.Entry<Case, Position> entry : mapDonnees.entrySet()) {
            Case key = entry.getKey();
            Position value = entry.getValue();
            System.out.println("Case: " + key + ", Position: " + value);
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
        return new Position(mapDonnees.get(cell).getX(),mapDonnees.get(cell).getY());
    }

    public boolean getState(int x, int y) {
        return tab[x][y].getState();
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
        Position pos = mapDonnees.get(source);
        int x = pos.getX();
        int y = pos.getY();
        System.out.println("POS =" + pos + " direction = " + d.toString());

        //vérifier si la position n'est pas en dehors du tableau
        switch (d) {
            case h -> {
                if (y > 0)
                    return tab[x][y - 1];
            }
            case hd -> {
                if (y > 0 && (x < sizeX - 1))
                    return tab[x + 1][y - 1];
                //throw new IndexOutOfBoundsException();
            }
            case d -> {
                if (x < sizeX - 1)
                    return tab[x + 1][y];
                //throw new IndexOutOfBoundsException();
            }
            case db -> {
                if (x < sizeX - 1 && (y < sizeY - 1))
                    return tab[x + 1][y + 1];
                // new IndexOutOfBoundsException();
            }
            case b -> {
                if (y < sizeY - 1)
                    return tab[x][y + 1];
                //throw new IndexOutOfBoundsException();
            }
            case bg -> {
                if (x > 0 && (y < sizeY - 1))
                    return tab[x - 1][y + 1];
                //throw new IndexOutOfBoundsException();
            }
            case g -> {
                if (x > 0)
                    return tab[x - 1][y];
                //throw new IndexOutOfBoundsException();
            }
            case gh -> {
                if (x > 0 && y > 0)
                    return tab[x - 1][y - 1];
                //throw new IndexOutOfBoundsException();
            }
        }
        return null;
    }

    @Override
    public void run() {
        //rndState();
        nextState();
        updateState();
        // notification de l'observer
        setChanged();
        notifyObservers();
        System.out.println("/////////////////////////////////////////////");
    }
}
