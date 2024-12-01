package modele;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Case implements Serializable {

    private boolean state;
    private boolean nextState;

    //private boolean prevState;
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


    public void nextState() {
        int nb = env.getNbCases(this);
        if (!state && nb==3) {
            nextState = true;
        } else {
            if (nb < 2 || nb > 3) // Si la case est vivante et qu'elle n'est ni égale à deux ou trois, elle meurt, la condition de survie n'est pas nécessaire
                nextState = false;
            else nextState = state; //stays the same!!!!!!!!
        }
        //System.out.println("Case a la position " + env.getPos(this) + " | nbvoisins vivants = " + nb + " | state = " + state + " | nextState = " + nextState);
    }

    public void updateState(){
        state = nextState;
    }


    public void sauve(String s) throws IOException {

        File pos= new File("W:\\3eme Annee Automne\\LIFAPOO\\lifapoo-jeu-de-la-vie-g06\\data\\" + s);
        pos.createNewFile();
        System.out.println("pendant: " + pos);
        System.out.println(pos.canWrite());
        if (pos.canWrite()){
            try {
                ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(pos));
                o.writeObject(this);
                o.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void charge(String s) throws IOException {

        File pos= new File("W:\\3eme Annee Automne\\LIFAPOO\\lifapoo-jeu-de-la-vie-g06\\data\\" + s);

        System.out.println("pendant: " + pos);
        System.out.println(pos.canRead());
        if (pos.canRead()){
            try {
                ObjectInputStream o = new ObjectInputStream(new FileInputStream(pos));
                Object p = o.readObject();
                if (p instanceof Position){
                    System.out.println(p);
                }
                o.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);}
        }
    }

}
