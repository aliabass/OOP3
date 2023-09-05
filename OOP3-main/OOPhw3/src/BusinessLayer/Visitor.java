package BusinessLayer;

import   BusinessLayer.Tiles.*;
import  BusinessLayer.Enemies.*;
import  BusinessLayer.Heros.*;

public interface Visitor {

    void visit(Empty empty);
    void visit(Wall wall);
    void visit(Enemy enemy);
    void visit(Hero hero);
    void accept(Visitor visitor);
}
