package swingy.controller;

import swingy.model.Enemy;
import swingy.model.Hero;

import java.util.ArrayList;
import java.util.List;

public class WorldGeneration {
    private int boundsX = 0;
    private int boundsY = 0;
    private int heroX = 0;
    private int heroY = 0;
    private Hero hero = null;
    private List<Enemy> enemies = new ArrayList<>();

    public void generateWorld(Hero tmpHero){
        hero = tmpHero;
        int level = hero.getStats().getLevel();
        boundsX = (level-1) * 5+10-(level % 2);
        boundsY = (level-1) * 5+10-(level % 2);
        heroX = boundsX / 2;
        heroY = boundsY / 2;
        generateEnemies(level);
    }

    public void generateEnemies(int level){
        enemies = new ArrayList<>();
        int enemyAmm = ((int) Math.ceil(Math.random() * 5)) * level;
        while (enemyAmm >= 0){
            enemies.add(generateEnemy(level));
            enemyAmm--;
        }
    }

    private Enemy generateEnemy(int level){
        int[] coords = coordsGenerator();
        while (coordsTaken(coords))
            coords = coordsGenerator();
        return new Enemy(level, coords[0], coords[1]);
    }

    private int[] coordsGenerator(){
        return new int[]{(int) Math.ceil(Math.random() * boundsX),(int) Math.ceil(Math.random() * boundsY)};
    }

    private boolean coordsTaken(int[] newCoords){
        if (!enemies.isEmpty()){
            for (Enemy element : enemies) {
                if (element.getX() == newCoords[0] && element.getY() == newCoords[1] && (newCoords[0] != boundsX/2 && newCoords[1] != boundsY/2))
                    return true;
            }
        }
        return false;
    }

    public Enemy ghostMove(String direction){
        int ghostPosX = heroX;
        int ghostPosY = heroY;
        if (direction.equals("n")){
            ghostPosY++;
        } else if (direction.equals("s")){
            ghostPosY--;
        } else if (direction.equals("e")){
            ghostPosX++;
        } else if (direction.equals("w")){
            ghostPosX--;
        }
        return checkPos(ghostPosX, ghostPosY);
    }

    public boolean move(String direction){
        System.out.println("moving " + direction);
        if (direction.equals("n")){
            heroY++;
        } else if (direction.equals("s")){
            heroY--;
        } else if (direction.equals("e")){
            heroX++;
        } else if (direction.equals("w")){
            heroX--;
        }
        if ((heroX > boundsX || heroX < 0) || (heroY > boundsY || heroY < 0)){
            return true;
        }
        return false;
    }

    private Enemy checkPos(int x, int y){
        for (Enemy element : enemies) {
            if (element.getX() == x && element.getY() == y)
                return element;
        }
        return null;
    }

    public void defeatEnemy(Enemy enemy){
        enemies.remove(enemy);
    }


    public void printWorld(){
//        System.out.println("coords : " + boundsX + " " + boundsY);
        int posX = 0;
        int posY = boundsY;
        while (posY >= 0){
            posX = 0;
            while (posX <= boundsX){
                if (coordsTaken(new int[]{posX,posY}))
                    System.out.print("X  ");
                else if (posX == heroX && posY == heroY)
                    System.out.print("C  ");
                else
                    System.out.print("   ");
                posX++;
            }
            posY--;
            System.out.print(" | \n");
        }
//        System.out.println("coords : " + boundsX + " " + boundsY);
//        for (Enemy element : enemies) {
//            System.out.println("Enemy coords >> x :" + element.getX() + " y :" + element.getY());
//        }
    }
}
