package BusinessLayer.Tiles;

import BusinessLayer.Enemies.Enemy;
import BusinessLayer.Heros.Hero;
import BusinessLayer.Visitor;

public abstract class Tile implements Visitor {

    private char tile;
    private int [] cords = new int [2];

    protected Tile(char t, int x , int y) {
        this.tile = t;
        cords[0] = x; cords[1]=y;
    }
    @Override
    public void visit(Empty empty){
        int emptX = empty.getXcord(); int emptY = empty.getYcord();
        empty.setX(cords[0]); empty.setY(cords[1]);
        this.setX(emptX); this.setY(emptY);
    }
    @Override
    public void visit(Wall wall){
        //visiting a wall does nothing
    }
    @Override
    public void visit(Enemy enemy){}
    @Override
    public void visit(Hero hero){}
    @Override
    public void accept(Visitor visitor){}



    public void communicate(Tile approched){
        approched.accept(this);
    }
    public void setPlace(int x, int y){
        setX(x);
        setY(y);
    }
    public int getXcord(){return cords[0];}
    public int getYcord(){return cords[1];}
    public void setX(int x){cords[0] = x;}
    public void setY(int y){cords[1] = y;}

    public String toString(){
        return String.valueOf(tile);
    }

}