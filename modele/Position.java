package modele;

import java.io.*;

public class Position implements Serializable {
    private final int x;
    private final int y;

    public Position(int _x, int _y){
        x = _x;
        y = _y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString(){
        return "x = " + x + " y = " + y;
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
