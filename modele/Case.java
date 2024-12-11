package modele;

import java.io.*;
import java.util.Random;

public class Case implements Serializable {
    private boolean prevState;
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
    public void setState(boolean b) {
        state=b;
    }

    public void switchState(){
        state = !state;
    }
    public void rndState() {
        state = rnd.nextBoolean();
    }


    public void setPrevState()
    {
        prevState = state;
    }

    public void usePrevState()
    {
        state = prevState;
    }

    public void nextState() {
        int nb = env.getNbCases(this);
        if (!state && nb==3) {
            nextState = true;
        } else {
            if (nb < 2 || nb > 3) // Si la case est vivante et qu'elle n'est ni égale à deux ou trois, elle meurt, la condition de survie n'est pas nécessaire
                nextState = false;
            else nextState = state;
        }
    }

    public void updateState(){
        state = nextState;
    }

}
