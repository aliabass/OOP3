package BusinessLayer;

import BusinessLayer.Enemies.Boss;
import BusinessLayer.Enemies.Monster;
import BusinessLayer.Enemies.Trap;
import BusinessLayer.Heros.Hero;
import BusinessLayer.Tiles.*;
import View.IOController;
import View.IOoperation;

import java.util.LinkedList;
import java.util.List;


public class GameBoard {
    private Tile[][] Tiles;
    private Hero player;
    private List<Unit> enemiesInboard = new LinkedList<>();
    private IOoperation IO = new IOController();


    public GameBoard() {}
    public List<Unit> getenemies(){return enemiesInboard;}

    public void play(Unit mob){
        int x  = mob.getXcord() , y = mob.getYcord();
        int [] newCords = new int[2];
        newCords = mob.Action();
        if(newCords[0] != x || newCords[1] !=y) {
//            System.out.println( "Xcord ------>" + newCords[0]);
//            System.out.println("Ycord ------>" + newCords[1]);
//            System.out.println("Tiles 2d length ------>" + Tiles.length);
//            System.out.println("each row in tiles ------>" + Tiles[0].length);
            Tile t = Tiles[newCords[1]][newCords[0]];
            mob.communicate(t);
            if(mob.getYcord() != y || mob.getXcord() !=x){
                //new places
                Tiles[mob.getYcord()][mob.getXcord()] = mob;
                Tiles[t.getYcord()][t.getXcord()] = t;
            }
        }
    }

    public void PrepareBoard(List<String> accepted, Hero Hero) {
        Tiles = new Tile[accepted.size()][accepted.get(1).length()];
        this.player = Hero;
        for (int i = 0; i < Tiles.length; i++) {
            String currow = accepted.get(i);
            for (int j = 0; j < currow.length(); j++) {
                char toadd = currow.charAt(j);
                if (toadd == '.') {
                    Tiles[i][j] = new Empty(j, i);
                } else if (toadd == '#') {
                    Tiles[i][j] = new Wall(j, i);
                } else if (toadd == '@') {
                    Tiles[i][j] = player;
                    player.setX(j);
                    player.setY(i);
                } else if (toadd == 'B' || toadd == 'Q' || toadd == 'D') {
                    if (toadd == 'B') {
                        Unit trap = new Trap("Bonus Trap", 1, 1, 1, 1, j, i, toadd, 250, 1, 5, player);
                        Tiles[i][j] = trap;
                        enemiesInboard.add(trap);
                    } else if (toadd == 'Q') {
                        Unit trap = new Trap("Queen's Trap", 250, 250, 50, 10, j, i, toadd, 100, 3, 7, player);
                        Tiles[i][j] = trap;
                        enemiesInboard.add(trap);

                    } else {
                        Unit trap = new Trap("Death Trap", 500, 500, 100, 20, j, i, toadd, 250, 1, 10, player);
                        Tiles[i][j] = trap;
                        enemiesInboard.add(trap);
                    }
                } else {
                    //if( toadd!=' ') {
                        addmonster(j, i, toadd);
                    //}
                }
            }
        }
    }

    private void addmonster(int cordx,int cordy,char input){
        Unit monster = null;
        switch (input){
            case 's':
                monster = new Monster("Lannister Solider",80,80,8,3,cordx,cordy,input,25,3,player);break;
            case 'k':
                monster = new Monster("Lannister Knight" ,200,200,14,8,cordx,cordy,input,50,4,player);break;
            case 'q':
                monster = new Monster("Queen's Guard",400,400,20,15,cordx,cordy,input,100,5,player);break;
            case 'z':
                monster = new Monster("Wright",600,600,30,15,cordx,cordy,input,100,3,player);break;
            case 'b':
                monster = new Monster("Bear-Wright",1000,1000,75,30,cordx,cordy,input,250,4,player);break;
            case 'g':
                monster = new Monster("Giant-Wright" , 1500,1500,100,40,cordx,cordy,input,500,5,player);break;
            case 'w':
                monster = new Monster("White Walker",2000,2000,150,50,cordx,cordy,input,1000,6,player);break;
            case 'M':
                monster = new Boss("The Mountain" , 1000,1000,60,25,cordx,cordy,input,500,6,player,5);break;
            case 'C':
                monster = new Boss("Queen Cersei",100,100,10,10,cordx,cordy,input,1000,1,player,8);break;
            case 'K':
                monster = new Boss("Night's King",5000,5000,300,150,cordx,cordy,input,5000,8,player,3);break;
        }
        enemiesInboard.add(monster);
        Tiles[cordy][cordx] = monster;
    }

    public void Begin(){
        while (player.getHealthamount() > 0 && !enemiesInboard.isEmpty()){
            IO.Write(toString() + player.description());
            play(player);
            List<Unit> ofdead = new LinkedList<>();
            for (Unit e: enemiesInboard) {
                    if (e.getHealthamount() <= 0) {
                        Tiles[e.getYcord()][e.getXcord()] = new Empty(e.getXcord(), e.getYcord());
                        ofdead.add(e);
                        player.getEnemies().remove(e);
                }
            }
            for (Unit dead: ofdead) {
                enemiesInboard.remove(dead);

            }
            for (Unit toplay: enemiesInboard)
                play(toplay);
            }
            if (player.getHealthamount()<=0){IO.Write("Player has died !" + "\n" + toString() + player.description());}
        }


    //to print board every round
    public String toString(){
        String board = new String("");
        for (Tile [] tile: Tiles
             ) {
            board = board+"\n";
            for (int i = 0 ; i<tile.length;i++){
                if(tile[i] !=null) {
                    board = board + tile[i].toString();
                }
            }
        }
        board = board +"\n";
        return board;
    }


}