package BusinessLayer.Tiles;


import View.IOController;
import View.IOoperation;

import static java.lang.Math.sqrt;

public abstract class Unit extends Tile {

    private String name;
    private int healthpool;
    private int healthamount;
    private int Attackpoints;
    private int Defensepoints;
    private int XP;
    private IOoperation IO;

    public Unit(String name, int healthpool,int healthamount,int attackpoints,int defensepoints ,int x, int y, char symbol,int xp){
        super(symbol,x,y);
        this.name = name;
        this.healthpool = healthpool;
        this.healthamount = healthamount;
        this.Attackpoints = attackpoints;
        this.Defensepoints = defensepoints;
        this.XP = xp;
        this.IO = new IOController();
    }

    public int getExp(){return XP;}
    public String description() {
        return String.format("%s: ---> \t\tHealth: %s\t/\t%s\t\tAttack: %d\t\tDefense: %d", getName(), getHealthamount(),getHealthpool(), getAttack(), getDefense());
    }

    public String getName(){return name;}
    public int getHealthpool(){return  healthpool;}
    public void setHealthpool(int healthpool){this.healthpool = healthpool;}
    public int getHealthamount(){return  healthamount;}
    public void setHealthamount(int health){this.healthamount = health;}
    public int getAttack(){return Attackpoints;}
    public void setAttack(int attack){this.Attackpoints = attack;}
    public int getDefense(){return Defensepoints;}
    public void setDefense(int def){this.Defensepoints = def;}


    //things happen on tick !!!!!....
    public abstract int [] Action();


    public String toString(){
        return super.toString();
    }

    public  int Roll(int attdef){
        if(attdef == 1) {
            int rolled = (int) (Math.random() * (getAttack() + 1));
            IO.Write(name + " has rolled " + rolled + " to Attack.");
            return rolled;
        }
        else {
            int rolled = (int) (Math.random() * (getDefense() + 1));
            IO.Write(name + " has rolled " + rolled + " to Defend.");
            return rolled;
        }
    }
    //combat related implementations
    public boolean impair(int amount) {
        healthamount -= amount;
        if (healthamount <= 0) {
            healthamount = 0;
            IO.Write(name + " has died!.");
            return true;
        }
        return false;
    }
    public void engage(Unit opponent){
        IO.Write(name + " engaged " + opponent.getName() + " to battle");
        int attackerDamage = this.Roll(1);
        int attackedDefense = opponent.Roll(0);

        int hmode=attackerDamage - attackedDefense;
        if(hmode > 0){
            IO.Write(name +" dealt damage equal to " + hmode + " to " + opponent.getName() + ".");
            opponent.impair(hmode);
            IO.Write(opponent.description());
            return;
        }
        else{IO.Write(opponent.getName() +" blocked the damage");}
        IO.Write(opponent.description());
    }
    public double range(Unit other){
        return sqrt(((this.getXcord()-other.getXcord())*(this.getXcord()-other.getXcord()))+((this.getYcord()-other.getYcord())*(this.getYcord()-other.getYcord())));
    }
}
