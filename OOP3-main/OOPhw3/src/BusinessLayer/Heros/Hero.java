package BusinessLayer.Heros;

import BusinessLayer.Enemies.Enemy;
import BusinessLayer.HeroicUnit;
import BusinessLayer.Tiles.*;
import BusinessLayer.Visitor;
import View.*;
import java.util.*;

public abstract class Hero extends Unit implements HeroicUnit {
    protected static char symbol = '@';
    private int Experience = 0;
    private int HeroLevel = 1;
    private List<Unit> ingameEnemy = new LinkedList();
    private IOoperation IO = new IOController();

    public Hero(int x, int y, String name, int healthamount, int healthpool, int attackpoints, int defensepoints,int xp) {
        super(name, healthpool, healthamount, attackpoints, defensepoints, x, y, symbol,xp);
    }
    public int getEXP() {
        return Experience;
    }
    public abstract List<Unit> EnmsINrange(int range);

    public void addEnemy(Unit enemy){ingameEnemy.add(enemy);}
    public List<Unit> getEnemies(){return ingameEnemy;}

    public int [] Action(){
        int [] newCords = new int[2];
        newCords[0] = getXcord(); newCords[1] = getYcord();
        char input = IO.Read().charAt(0);
        switch (input){
            case 'w':
                newCords[1] = getYcord() - 1; break;
            case 's':
                newCords[1] = getYcord() + 1;break;
            case 'a':
                newCords[0] = getXcord() - 1;break;
            case 'd':
                newCords[0] = getXcord() + 1;break;
            case 'e':
                castAbility();break;
            default:
                return newCords;
        }

        return newCords;
    }

    public int getLevel() {
        return HeroLevel;
    }

    public String description() {
        return super.description() + "  Level: " + HeroLevel + "   Experience: " + Experience;
    }

    public boolean LevelUp() {
        int milestone = HeroLevel * 50;
        if (Experience >= milestone) {
            Experience -= milestone;
            HeroLevel += 1;
            setHealthpool(getHealthpool() + (10 * HeroLevel));
            setHealthamount(getHealthpool());
            setAttack(getAttack() + (4 * HeroLevel));
            setDefense(getDefense() + HeroLevel);
            IO.Write(getName() + " has levelled up! and new Level is " + HeroLevel +
                    " and gained stats:  " + 10 * HeroLevel + " Healthpool, "
                    + 4 * getAttack() + " AttackPoints, " + HeroLevel + " DefensePoints.");
            return true;
        }
        return false;
    }

    public boolean gainEXP(int xp) {
        this.Experience += xp;
        return this.LevelUp();
    }

    public void accept(Visitor v) {
        v.visit(this);
    }


    public void visit(Enemy opponent) {
        this.engage(opponent);
        if (opponent.getHealthAmount() <= 0) {
            gainEXP(opponent.getExp());
            int tmpx=this.getXcord(),tmpy=this.getYcord();
            this.setX(opponent.getXcord());
            this.setY(opponent.getYcord());
            opponent.setX(tmpx);
            opponent.setY(tmpy);
        }
    }


    public int getExperience() {return Experience;}

    public int getHeroLevel() {return HeroLevel;}

    public String toString() {
        if (getHealthamount() == 0) return "X";
        return super.toString();
    }
}

