package BusinessLayer.Enemies;

import BusinessLayer.Tiles.Unit;

public class Trap extends Enemy{
    private int visibilityTime;
    private int invisibilityTime;
    private int ticksCount=0;
    private boolean visible=true;
    //Bonus trap doesnt die when special ability is casted
    public Trap(String name, int healthpool, int healthamount, int attackpoints,
                int defensepoints, int x, int y, char symbol, int experienceValue,int visibilityTime,int invisibilityTime, Unit hero) {
        super(name, healthpool, healthamount, attackpoints, defensepoints, x, y, symbol, experienceValue,hero);
        this.visibilityTime=visibilityTime;
        this.invisibilityTime=invisibilityTime;
    }


    @Override
    public int[] Action(){
        if(visible){
            if(visibilityTime==ticksCount){
                visible=false;
                ticksCount=0;
            }
            else
                ticksCount++;
        }
        else{
            if(invisibilityTime==ticksCount){
                visible=true;
                ticksCount=0;
            }
            else
                ticksCount++;
        }
        if(this.range(getHero())<2){this.engage(getHero());}
        int [] cords = new int [2];
        cords[0] = getXcord();
        cords[1] = getYcord();
        return cords;
    }


    public String toString(){
        if(visible) return super.toString();
        return String.valueOf('.');
    }

}
