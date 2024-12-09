package modele;

import java.io.*;

public class Position implements Serializable {
    private int x;
    private int y;

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
    public boolean equals(Object obj) {
        if(obj instanceof Position)
            return x==((Position) obj).getX() && y==((Position) obj).getY();
        return false;
    }
    public void setPos(int _x, int _y){
        x = _x;
        y = _y;
    }
    @Override
    public String toString(){
        return "x = " + x + " y = " + y;
    }

    public void sauve(String s) throws IOException {
        System.out.println("homepath :" + System.getProperty("user.home"));
        // Chemin du répertoire
        File directory = new File(System.getProperty("user.home") + "\\JeuDeLaVie");

        // Vérifie si le répertoire existe, sinon le crée
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Répertoire créé : " + directory.getAbsolutePath());
            } else {
                throw new IOException("Impossible de créer le répertoire : " + directory.getAbsolutePath());
            }
        }

        // Création du fichier
        File pos = new File(directory, s);
        if (pos.createNewFile()) {
            System.out.println("Fichier créé : " + pos.getAbsolutePath());
        } else {
            System.out.println("Le fichier existe déjà : " + pos.getAbsolutePath());
        }

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

        File pos= new File(System.getProperty("user.home")+"\\JeuDeLaVie\\"+s);

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
