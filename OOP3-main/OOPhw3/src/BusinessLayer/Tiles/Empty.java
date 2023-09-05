package BusinessLayer.Tiles;


import BusinessLayer.Visitor;

public class Empty extends Tile {

    public static final char symbol = '.';
    public Empty(int x , int y){
        super(symbol, x, y);
    }

//ovride
//    public String toString() {
//        return  String.valueOf(symbol);
//    }

    @Override
    public void accept(Visitor v){v.visit(this);}

}
