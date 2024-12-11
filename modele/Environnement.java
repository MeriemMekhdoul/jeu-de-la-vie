package modele;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class Environnement extends Observable implements Runnable, Serializable {
    private Case[][] tab;
    private Map<Case, Position> mapDonnees;
    private int sizeX;
    private int sizeY;

    public Environnement() {}
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

    public void prevState(){
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                tab[i][j].usePrevState();
            }
        }
        setChanged();
        notifyObservers();
    }

    public void nextState() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                tab[i][j].setPrevState();
                tab[i][j].nextState();
            }
        }
    }

    public void updateState() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                tab[i][j].setPrevState();
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
        nextState();
        updateState();
        // notification de l'observer
        setChanged();
        notifyObservers();
    }




    public void setAll(Environnement e) {
        // Vérification de la taille avant de copier les données
        if (this.sizeX == e.getSizeX() && this.sizeY == e.getSizeY()) {

            // Changer l'état des cases de l'environnement
            for (int i = 0; i < sizeX; i++) {
                for (int j = 0; j < sizeY; j++) {
                    this.tab[i][j].setState(e.getState(i,j));
                }
            }

            // notification de l'observer
            setChanged();
            notifyObservers();
        } else {
            // Si les tailles ne correspondent pas, créer un nouvel environnement avec la taille de l'environnement source
            System.out.println("Les tailles des environnements ne correspondent pas, création d'un nouvel environnement.");

            // Créer un nouvel environnement avec les dimensions de l'environnement source
            this.sizeX = e.getSizeX();
            this.sizeY = e.getSizeY();
            this.tab = new Case[this.sizeX][this.sizeY];
            this.mapDonnees = new HashMap<>();

            // Initialiser les cases dans le nouvel environnement avec les données de l'environnement source
            for (int i = 0; i < this.sizeX; i++) {
                for (int j = 0; j < this.sizeY; j++) {
                    Case c = new Case(this);  // Créez de nouvelles cases selon votre logique (par exemple, vide, ou avec un état initial)
                    this.tab[i][j] = c;
                    c.setState(e.getState(i, j));  // Copier l'état de la case
                    mapDonnees.put(c, new Position(i, j));
                }
            }

            // Notifier l'observateur que le nouvel environnement a été créé
            setChanged();
            notifyObservers();
        }
    }

    public Environnement getSousEnv(Position p1, Position p2) {
        int dx = Math.abs(p1.getX() - p2.getX()) + 1;
        int dy = Math.abs(p1.getY() - p2.getY()) + 1;

        Environnement sEnv = new Environnement();
        sEnv.setSizeX(dx);
        sEnv.setSizeY(dy);

        Case[][] tab = new Case[dx][dy];
        System.out.println("dx = " + dx + " dy = " + dy );
        sEnv.setTab(tab);

        // Déterminer le point de départ (xmin, ymin)
        int xmin = Math.min(p1.getX(), p2.getX());
        int ymin = Math.min(p1.getY(), p2.getY());

        for (int i = 0; i < dx; i++) {
            for (int j = 0; j < dy; j++) {
                Case c = this.tab[xmin + i][ymin + j];
                sEnv.tab[i][j] = c; //copier la case
            }
        }

        return sEnv;
    }

    public void setSousEnv(Environnement _env, Position p) {
        int x = _env.getSizeX();
        int y = _env.getSizeY();

        //get le point de depart du parcours du tableau de valeur
        int xDebut = p.getX();
        int yDebut = p.getY();

        for (int i = xDebut; i < xDebut + x; i++) {
            for (int j = yDebut; j < yDebut + y; j++) {
                tab[i][j].setState(_env.tab[i - xDebut][j - yDebut].getState());
            }
        }

        // notification de l'observer
        setChanged();
        notifyObservers();
    }


    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public void setTab(Case[][] _tab){
        this.tab = _tab;
    }
}
