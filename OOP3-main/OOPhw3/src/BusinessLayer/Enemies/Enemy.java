package BusinessLayer.Enemies;

import BusinessLayer.Heros.Hero;
import BusinessLayer.Tiles.Unit;
import BusinessLayer.Visitor;

public abstract class Enemy extends Unit {
    private Unit hero;

    public Enemy(String name, int healthpool, int healthamount, int attackpoints, int defensepoints, int x, int y, char symbol,int experienceValue, Unit hero) {
        super(name, healthpool, healthamount, attackpoints, defensepoints, x, y, symbol,experienceValue);
        this.hero = hero;
    }

    public int getHealthAmount() {
        return super.getHealthamount();
    }
    public Unit getHero(){return hero;}

    public void visit(Hero player){this.engage(player);}
    public void accept(Visitor v){v.visit(this);}

    public abstract int [] Action();
}
