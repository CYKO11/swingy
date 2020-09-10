package swingy.controller;

import swingy.model.Enemy;
import swingy.model.Hero;
import swingy.model.MapData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorldGeneration {
    public int boundsX = 0;
    public int boundsY = 0;
    int heroX = 0;
    int heroY = 0;
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

    public boolean ghostMove(String direction){
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
        if ((ghostPosX > boundsX || ghostPosX < 0) || (ghostPosY > boundsY || ghostPosY < 0)){
            return true;
        }
        return false;
    }

    public boolean move(String direction){
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

    public Enemy getEnemy(String direction){
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
        for (Enemy enemy : enemies){
            if (enemy.getX() == ghostPosX && enemy.getY() == ghostPosY)
                return enemy;
        }
        return null;
    }

    public void defeatEnemy(Enemy enemy){
        enemies.remove(enemy);
//        for (Enemy element : this.enemies){
//            if (element.getY() == enemy.getY() && element.getX() == enemy.getX()){
//                enemies.remove(element);
//                return;
//            }
//        }
    }

    public List<MapData> exportWorld(){
        int pos = 0;
        List<MapData> mapData= new ArrayList<>();
        while (pos < enemies.size()){
            mapData.add(
                    new MapData(
                            enemies.get(pos).getX(),
                            enemies.get(pos).getY(),
                            1,
                            enemies.get(pos),
                            null
                    )
            );
            pos++;
        }
        mapData.add(
                new MapData(
                        heroX,
                        heroY,
                        2,
                        null,
                        hero
                )
        );
        return mapData;
    }

}
