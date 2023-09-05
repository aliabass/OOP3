package BusinessLayer.Enemies;

import BusinessLayer.Tiles.Unit;

import static java.lang.Math.random;

public class Monster extends Enemy{
    private int visionRange;

    public Monster(String name, int healthpool, int healthamount, int attackpoints, int defensepoints, int x, int y, char symbol, int experienceValue,int visionRange, Unit hero) {
        super(name, healthpool, healthamount, attackpoints, defensepoints, x, y, symbol, experienceValue, hero);
        this.visionRange = visionRange;
    }

    public int getVisionRange(){return visionRange;}


    @Override
    public int [] Action(){
        int[] newCord = new int[2];
        newCord[0] = getXcord();
        newCord[1]= getYcord();
        Unit player = getHero();
        int dx,dy;
        double hmode=this.range(player);
        if(hmode<=(double)visionRange){
            dx=this.getXcord()- player.getXcord();
            dy=this.getYcord()-player.getYcord();
            int tmpx=dx,tmpy=dy;
            if(dx<0)
                tmpx=dx*-1;
            if(dy<0)
                tmpy=dy*-1;
            if(tmpx>tmpy){
                if(dx>0)
                    newCord=this.move(Movements.LEFT,newCord);
                else
                    newCord=this.move(Movements.RIGHT,newCord);
            }
            else{
                if(dy>0)
                    newCord=this.move(Movements.UP,newCord);
                else
                    newCord=this.move(Movements.DOWN,newCord);
            }
        }
        else{
            int randomize=(int)(Math.random()*(4+1));
            switch (randomize){
                case 0: newCord=this.move(Movements.NONE,newCord);
                break;
                case 1: newCord=this.move(Movements.LEFT,newCord);
                break;
                case 2: newCord=this.move(Movements.RIGHT,newCord);
                break;
                case 3: newCord=this.move(Movements.UP,newCord);
                break;
                case 4: newCord=this.move(Movements.DOWN,newCord);
                break;
                //Just for debugging;
                default:throw new IllegalArgumentException("Problem with math.random!");
            }

        }
        return newCord;
    }
    private int[] move(Movements direction,int [] newCord){
        switch (direction){
            case LEFT: newCord[0] = newCord[0] - 1;
            break;
            case DOWN: newCord[1] = newCord[1] + 1;
            break;
            case UP: newCord[1] = newCord[1] - 1;
            break;
            case RIGHT: newCord[0] = newCord[0] + 1;
            break;
            case NONE: break;
            //Just for debugging;
            default: throw new IllegalArgumentException("Illegal movement!");
        }
        return newCord;
    }
}
