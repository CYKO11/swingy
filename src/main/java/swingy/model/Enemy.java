package swingy.model;

public class Enemy {

    private long    enemyHP;
    private long    enemyAtkDmg;
    private int     enemyArmor;
    private int     enemyLevel;
    private int     x;
    private int     y;

    public Enemy(int Herolvl, int x, int y){
        this.enemyHP = 10 * Herolvl;
        this.enemyAtkDmg = 5 * Herolvl;
        this.enemyArmor = 2 * Herolvl;
        this.enemyLevel = Herolvl;
        this.x = x;
        this.y = y;
    }

    public long getEnemyHP() {
        return enemyHP;
    }

    public long getEnemyAtkDmg() {
        return enemyAtkDmg;
    }

    public int getEnemyArmor() {
        return enemyArmor;
    }

    public int getEnemyLevel() {
        return enemyLevel;
    }

    public int getX(){ return x; };

    public int getY(){ return y; }
}