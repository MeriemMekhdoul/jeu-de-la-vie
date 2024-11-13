package modele;

import java.util.ArrayList;
import java.util.Random;

public class Case {
    private static final Random rnd = new Random();

    public boolean getState() {

        return state;
    }

    private boolean state;

    public void rndState() {
        state = rnd.nextBoolean();
    }

    public void nextState() {
        // TODO
        // calcul de l'état suivant.
        // Utiliser la fonction getCase de Environnement
    }


}
