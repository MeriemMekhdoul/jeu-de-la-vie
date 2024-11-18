package modele;

import java.util.ArrayList;
import java.util.Random;

public class Case {

    private boolean state;
    private boolean nextState;
    private final Environnement env;

    public Case(Environnement _env)
    {
        state = rnd.nextBoolean();
        env=_env;
    }
    private static final Random rnd = new Random();

    public boolean getState() {
        return state;
    }


    public void rndState() {
        state = rnd.nextBoolean();
    }

    public void nextState() {
        int nb = env.getNbCases(this);
        if (!state && nb==3) {
            nextState = true;
        } else {
            if (nb < 2 || nb > 3) // Si la case est vivante et qu'elle n'est ni égale à deux ou trois, elle meurt, la condition de survie n'est pas nécessaire
                nextState = false;
            else nextState = state; //stays the same!!!!!!!!
        }
        System.out.println("Case a la position " + env.getPos(this) + " | nbvoisins vivants = " + nb + " | state = " + state + " | nextState = " + nextState);
    }

    public void updateState(){
        state = nextState;
    }



}
