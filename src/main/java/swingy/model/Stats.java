package swingy.model;

public class Stats {
    private long    HP;
    private long    AtkDmg;
    private int     Armor;
    private int     xp;
    private int     xpBar;
    private int     Level;

    Stats(long HP, long AtkDmg, int Armor, int xp, int Level) {
        this.HP = HP;
        this.AtkDmg = AtkDmg;
        this.Armor = Armor;
        this.Level = Level;
        this.xpBar = Level * 1000 + (int)Math.pow(Level - 1, 2) * 450;
        this.xp = xp;
    }

    public int getXpBar() {
        return this.xpBar;
    }

    public long getHP() {
        return HP;
    }

    public long getAtkDmg() {
        return AtkDmg;
    }

    public int getArmor() {
        return Armor;
    }

    public int getXp() {
        return xp;
    }

    public int getLevel() {
        return Level;
    }
}
