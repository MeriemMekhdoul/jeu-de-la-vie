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

}
