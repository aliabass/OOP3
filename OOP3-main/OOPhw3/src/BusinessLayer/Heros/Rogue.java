package BusinessLayer.Heros;

import BusinessLayer.HeroicUnit;
import BusinessLayer.Tiles.Unit;
import View.IOController;
import View.IOoperation;

import java.util.LinkedList;
import java.util.List;

public class Rogue extends Hero implements HeroicUnit {
    private int cost;
    private int currentEnergy = 100;
    private IOoperation IO = new IOController();

    public Rogue(int x ,int y, String name , int healamount, int healthpool,int attackpoints,int defensepoints, int cost){
        super(x,y,name,healamount,healthpool,attackpoints,defensepoints,1);
        this.cost = cost;
    }


    public boolean gainEXP(int xp) {
        if(super.gainEXP(xp))
        return this.LevelUps();
        return  false;
    }

    @Override
    public List<Unit> EnmsINrange(int range) {
        List<Unit> enemies = getEnemies();
        List<Unit> inrange = new LinkedList<>();
        for (Unit e:enemies)
        {
            double currRange = Math.sqrt((Math.pow(this.getXcord() - e.getXcord(),2))+(Math.pow(this.getYcord() - e.getYcord(),2)));
            if(currRange < range){inrange.add(e);}
        }
        return inrange;
    }

    public boolean LevelUps(){
        currentEnergy = 100;
        setAttack(getAttack() + (3*getLevel()));
        IO.Write("Rogue bonus: Energy filled, " + 3*getLevel() + " attackpoints" );
        return true;
    }

    @Override
    public void castAbility() {
        if(currentEnergy<cost){
            IO.Write(getName() +" tried to cast Fan of Knives but there is not enough energy!" );
            return;
        }
        currentEnergy = currentEnergy - cost;
        List<Unit> inrange = EnmsINrange(2);
        if(!inrange.isEmpty()) {
            for (Unit e : inrange) {
                int def = e.Roll(0);
                int damage=getAttack() - def;
                if (damage > 0) {
                    IO.Write(getName() +" cast Fan of Knives on " + e.getName() +" and dealt " + damage +" damage");
                    if (e.impair(damage)){
                        gainEXP(e.getExp());
                        inrange.remove(e);
                        getEnemies().remove(e);
                    }
                    IO.Write(e.description());
                } else {
                    IO.Write(e.getName() + " Blocked Fan of Knives damage");
                }
            }
        }else{IO.Write(getName()+ "cast Fan of Knives but there is no enemies in range!");}

    }
    public String description(){
        return super.description() + " Current energy: " + currentEnergy  +"/" + 100;
    }

    public int [] Action(){
        currentEnergy = Math.min(currentEnergy+10, 100);
        return super.Action();
    }
}
