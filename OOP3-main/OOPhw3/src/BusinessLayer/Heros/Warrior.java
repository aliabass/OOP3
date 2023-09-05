package BusinessLayer.Heros;

import BusinessLayer.HeroicUnit;
import View.IOController;
import View.IOoperation;

import java.awt.desktop.AboutEvent;
import BusinessLayer.Tiles.*;
import java.util.*;

public class Warrior extends Hero implements HeroicUnit {
    private int AbilityCD;
    private int Cooldown = 0;
    private IOoperation IO = new IOController();
    public Warrior(int x , int y, String name , int healamount, int healthpool,int attackpoints, int defensepoints, int abilityCD){
        super(x,y,name,healamount,healthpool,attackpoints,defensepoints,1);
        this.AbilityCD = abilityCD;
    }

    @Override
    public void castAbility(){
        if(Cooldown !=0){
            IO.Write(getName() + " tried to cast Avenger's Shield," + " Ability is on cooldown , CD = " + Cooldown + " Game ticks");
            return;
        }
        Cooldown = AbilityCD;
        int healingamt = Math.min(getHealthamount() + (10*getDefense()) , getHealthpool());
        int prev = getHealthamount();
        setHealthamount(healingamt);
        IO.Write(getName() + " has cast Avenger's Shield and healed for " + (healingamt-prev));
        double damageinit = (getHealthpool() * 0.1);
        List<Unit> inrange = EnmsINrange(3);
        if(!inrange.isEmpty()) {
            Unit e = inrange.get((int) (Math.random() * (inrange.size())));
            int def = e.Roll(0);
            int damage = (int)(damageinit - def);
            if(damage > 0) {
                IO.Write(getName() + " dealt " + damage + " damage to " + e.getName());
                if (e.impair(damage)) {
                    this.gainEXP(e.getExp());
                    inrange.remove(e);
                    getEnemies().remove(e);
                }
                IO.Write(e.description());
            }
        }

    }

    @Override
    public List<Unit> EnmsINrange(int range){
        List<Unit> enemies = getEnemies();
        List<Unit> inrange = new LinkedList<>();
        for (Unit e:enemies)
        {
         double currRange = Math.sqrt((Math.pow(this.getXcord() - e.getXcord(),2))+(Math.pow(this.getYcord() - e.getYcord(),2)));
         if(currRange < range){inrange.add(e);}
        }
        return inrange;
    }

    public boolean LevelUps()
    {
            Cooldown = 0;
            setHealthpool(getHealthpool() + (5*getLevel()));
            setAttack(getAttack() + (2*getLevel()));
            setDefense(getDefense() + getLevel());
            IO.Write(" Warrior Bouns: " + 5*getLevel() + " Healthpool " + 2*getLevel() + " Attackpoints " + getLevel() + " Defensepoints ");
            return true;
    }
    public boolean gainEXP(int Exp){
        if(super.gainEXP(Exp))
            return LevelUps();
        return false;
    }

    public String description(){
        return  super.description() + " SA Cooldown: " + Cooldown;
    }

    public int [] Action(){
        Cooldown = Cooldown - 1;
        if (Cooldown < 0) Cooldown = 0;
        return super.Action();
    }

}
