package BusinessLayer.Tiles;


import BusinessLayer.Visitor;

public class Wall extends Tile {
    public  static final char symbol = '#';

    public Wall(int x , int y){
        super(symbol,x,y);
    }

    @Override
    public  void accept(Visitor v){
        v.visit(this);
    }

}
