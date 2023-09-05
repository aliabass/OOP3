package BusinessLayer;

import BusinessLayer.Heros.*;
import BusinessLayer.Tiles.Unit;
import BusinessLayer.Tiles.Wall;
import View.IOController;
import View.IOoperation;

import java.util.List;

public class LevelManage {

    private List<List<String>> lvls;
    private IOoperation IO = new IOController();
    private Hero selectedHero;

    public LevelManage(List<List<String>> lvls) {
        this.lvls = lvls;
        IO.Write(Selection());
        String player = IO.Read();
        switch (player) {
            case "1":
                this.selectedHero = new Warrior(0, 0, "Jon Snow", 300, 300, 30, 4, 3);
                IO.Write("You have chosen Jon Snow (Warrior) !");
                break;
            case "2":
                this.selectedHero = new Warrior(0, 0, "The Hound", 400, 400, 20, 6, 5);
                IO.Write("You have chosen The Hound (Warrior) !");
                break;
            case "3":
                this.selectedHero = new Mage(0, 0, "Melisandre", 100, 100, 5, 1, 300, 30, 15, 5, 6);
                IO.Write("You have chosen Melisandre (Mage) !");
                break;
            case "4":
                this.selectedHero = new Mage(0, 0, "Thoros of Myr", 250, 250, 25, 4, 150, 20, 20, 3, 4);
                IO.Write("You have chosen Thoros of Myr (Mage) !");
                break;
            case "5":
                this.selectedHero = new Rogue(0, 0, "Arya Stark", 150, 150, 40, 2, 20);
                IO.Write("You have chosen Arya (Rogue) !");
                break;
            case "6":
                this.selectedHero = new Rogue(0, 0, "Bronn", 250, 250, 35, 3, 50);
                IO.Write("You have chosen Bronn (Rogue) !");
                break;
            case "7":
                this.selectedHero = new Hunter(0, 0, "Ygritte", 220, 220, 30, 2, 6);
                IO.Write("You have chosen Ygritte (Hunter) !");
                break;
        }
    }


    private String Selection() {
        String starter = "Select player:  \n" +
                "1. Jon Snow \t (Warrior)\t\tHealth: 300/300\t\tAttack: 30\t\tDefense: 4\t\tLevel: 1\t\tExperience: 0/50\t\tCooldown: 0/3\n" +
                "2. The Hound\t (Warrior) \t\tHealth: 400/400\t\tAttack: 20\t\tDefense: 6\t\tLevel: 1\t\tExperience: 0/50\t\tCooldown: 0/5\n" +
                "3. Melisandre \t(Mage)\t\tHealth: 100/100\t\tAttack: 5 \t\tDefense: 1\t\tLevel: 1\t\tExperience: 0/50\t\tMana: 75/300\t\tSpell Power: 15\n" +
                "4. Thoros of Myr\t(Mage)\tHealth: 250/250\t\tAttack: 25\t\tDefense: 4\t\tLevel: 1\t\tExperience: 0/50\t\tMana: 37/150\t\tSpell Power: 20\n" +
                "5. Arya Stark\t (Rogue)\t\tHealth: 150/150\t\tAttack: 40\t\tDefense: 2\t\tLevel: 1\t\tExperience: 0/50\t\tEnergy: 100/100\n" +
                "6. Bronn\t\t(Rogue)\t\tHealth: 250/250\t\tAttack: 35\t\tDefense: 3\t\tLevel: 1\t\tExperience: 0/50\t\tEnergy: 100/100\n" +
                "7. Ygritte\t (Hunter) \t\tHealth: 220/220\t\tAttack: 30\t\tDefense: 2\t\tLevel: 1\t\tExperience: 0/50\t\tArrows: 10\t\tRange: 6\n";
        return starter;
    }


    public void Startgame() {
        boolean won = false;
        for (int lvl = 0; lvl < this.lvls.size(); lvl++) {
            GameBoard currLvlBoard = new GameBoard();
            currLvlBoard.PrepareBoard(lvls.get(lvl), selectedHero);
            List<Unit> heroENMS = currLvlBoard.getenemies();
            for (Unit enemy : heroENMS) {
                selectedHero.addEnemy(enemy);
            }
            currLvlBoard.Begin();
            if (selectedHero.getHealthamount() <= 0) {
                break;
            }
            if(lvl == this.lvls.size()-1){ won = true;}
            if(!won) {
                IO.Write("Level completed, get ready to the next level ... ");
            }
        }
        if (won){IO.Write("Winner winner chicken dinner!!");}


    }
}
