package BusinessLayer.Heros;

import BusinessLayer.HeroicUnit;
import BusinessLayer.Tiles.Unit;
import View.IOController;
import View.IOoperation;

import java.util.LinkedList;
import java.util.List;

public class Mage extends Hero implements HeroicUnit {
    private int Manapool;
    private int currentMana;
    private int manaCost;
    private int spellPower;
    private int hitsCount;
    private int abilityRange;
    private IOoperation IO = new IOController();

    public Mage(int x, int y, String name, int healthamount, int healthpool, int attackpoints, int defensepoints, int manapool, int manacost, int spellpower, int hitcount, int abilityrng)
    {
        super(x, y, name, healthamount, healthpool, attackpoints, defensepoints,1);
        this.Manapool = manapool;
        this.currentMana = Manapool / 4;
        this.manaCost = manacost;
        this.spellPower = spellpower;
        this.hitsCount = hitcount;
        this.abilityRange = abilityrng;
    }


    public boolean gainEXP(int Exp) {
        if (super.gainEXP(Exp)) {
            return this.LevelUps();
        }
        return false;
    }

    public boolean LevelUps() {
        this.Manapool = this.Manapool + (25 * getLevel());
        this.currentMana = Math.min(this.currentMana + (Manapool / 4), Manapool);
        this.spellPower = this.spellPower + (10 * getLevel());
        IO.Write(" Mage Bonus: " + 25 * getLevel() + "  Manapool  " + (Manapool / 4) + " Mana  " + 10 * getLevel() + "  SpellPower ");
        return true;
    }

    @Override
    public void castAbility() {
        int curr = currentMana;
        curr = curr - manaCost;
        if (curr < 0) {
            IO.Write(getName() + " tried to cast Blizzard but there is not enough mana!");
            return;
        }
        currentMana = curr;
        int hits = 0;
        List<Unit> inrange = EnmsINrange(abilityRange);
        if (!inrange.isEmpty()) {
            while (hits < hitsCount && !inrange.isEmpty()) {
                Unit selected = inrange.get((int) (Math.random() * (inrange.size())));
                int def = selected.Roll(0);
                int damage=spellPower - def;
                if (damage> 0) {
                    IO.Write(getName() + " attacked " + selected.getName() + " with Blizzard and dealt " + damage + " damage");
                    if(selected.impair(damage)){
                        this.gainEXP(selected.getExp());
                        inrange.remove(selected);
                        getEnemies().remove(selected);
                    }
                    IO.Write(selected.description());
                } else {
                    IO.Write(getName() + " tried to attack" + selected.getName() + " with Blizzard but " + selected.getName() + " Blocked the damage");
                }
                hits += 1;
            }
        }else{IO.Write(getName() +" cast Blizzard but there is no enemies in Range!");}
    }

    @Override
    public List<Unit> EnmsINrange(int range) {
        List<Unit> enemies = getEnemies();
        List<Unit> inrange = new LinkedList<>();
        for (Unit e : enemies) {
            double currRange = Math.sqrt((Math.pow(this.getXcord() - e.getXcord(), 2)) + (Math.pow(this.getYcord() - e.getYcord(), 2)));
            if (currRange < range) {
                inrange.add(e);
            }
        }
        return inrange;
    }

    public String description() {
        return super.description() + " Mana " + currentMana + "/" + Manapool + "SpellPower: " + spellPower + " Ability Range: " + abilityRange + " hitsCount:" + hitsCount;
    }

    public int[] Action() {
        currentMana = Math.min(Manapool, currentMana + getLevel());
        return super.Action();
    }

}

