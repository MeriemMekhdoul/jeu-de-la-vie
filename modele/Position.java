package modele;

public class Position {
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
    public String toString(){
        return "x = " + x + " y = " + y;
    }
}
