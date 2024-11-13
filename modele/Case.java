package modele;

import java.util.ArrayList;
import java.util.Random;

public class Case {

    private boolean state;
    private Environnement env;

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
        int nb=env.getNbCases(this);
        if (!state && nb==3) {
            state = true;
        } else {
            if (state && !(nb == 2 || nb == 3)) // Si la case est vivante est qu'elle n'est ni égale à deux ou trois, elle meurt, la condition de survie n'est pas nécessaire
                state = false;
        }
    }
}
