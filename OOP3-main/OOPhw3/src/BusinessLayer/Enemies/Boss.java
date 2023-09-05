package BusinessLayer.Enemies;

import BusinessLayer.HeroicUnit;
import BusinessLayer.Tiles.Unit;
import View.IOController;
import View.IOoperation;

public class Boss extends Monster implements HeroicUnit {
    private int abilityfrequency;
    private int combatTicks = 0;
    private IOoperation IO = new IOController();

    public Boss(String name, int healthpool, int healthamount, int attackpoints, int defensepoints, int x, int y, char symbol, int experienceValue,int visionRange, Unit hero,int abFreq){
        super(name,healthpool,healthamount,attackpoints,defensepoints,x,y,symbol,experienceValue,visionRange,hero);
        this.abilityfrequency = abFreq;

    }



    @Override
    public int[] Action(){
        int [] cords = new int[2];
        cords[0] = getXcord();
        cords[1] = getYcord();
        if(this.range(getHero())<getVisionRange()){
            if (combatTicks == abilityfrequency){
                combatTicks = 0;
                this.castAbility();
                return cords;
            }else {
                combatTicks +=1;
                return super.Action();
            }
        }else{
            combatTicks = 0;
            return super.Action();
        }
    }
    @Override
    public void castAbility() {
        IO.Write("Boss " + getName() +" cast an ability!");
        int res = getAttack() - getHero().Roll(0);
        if(res>0){
            IO.Write("Boss " + getName() +" dealt " + getAttack() +" damage to " + getHero().getName());
            getHero().impair(getAttack());
        }else{IO.Write(getHero().getName() +" blocked damage from the ability ");}
    }
}
