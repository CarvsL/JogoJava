package com.mycompany.jogocerto;

import entities.Enemy;
import entities.Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.util.Random;
import mongo.Entrada;
import utilz.Constants.PlayerConstants;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private boolean congratulated = false;
    private boolean isRunning = true;
    private long contactTime = 0;
    private int  score = 0;
    private int ENEMYS = 5;
    private int level;
    private long levelStartTime;
    
    
    private Entrada entrada;

    public final static int GAME_WIDTH = 1920;
    public final static int GAME_HEIGHT = 1080;

     public Game(Entrada entrada) {
          this.entrada = entrada;
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);

        gamePanel.requestFocus();
        startGameLoop();
        levelStartTime = System.currentTimeMillis();
    }
    
    
    

    private void initClasses() {
        player = new Player(100, 760);
        Enemy enemy = new Enemy(1200, 760, this);
        enemies.add(enemy);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    

    public void update() {
        player.update();

        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - levelStartTime;
        int gameTimeInSeconds = (int) (elapsedTime / 1000);

        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemy.update();

            if (player.getHitbox().intersects(enemy.getHitbox())) {
                if (player.isAttacking()) {
                    int newA = generateRandomXInRange(2000, 3500);
                    int newB = generateRandomXInRange(-1500, -200);
                    int newC = generateRandomXInRange(-1500, -200);
                    int newD = generateRandomXInRange(2000, 3500);

                    enemy.takeDamage(50);

                    score += 1;
                    if (gameTimeInSeconds < 20) {
                        level = 1;
                        if (enemies.size() < 3) {
                            if (Math.random() < 0.5) {
                                enemies.add(new Enemy(newA, 760, this));
                                if (Math.random() < 0.5) {
                                    enemies.add(new Enemy(newC, 760, this));

                                } else {
                                    enemies.add(new Enemy(newD, 760, this));
                                }

                            } else {
                                enemies.add(new Enemy(newC, 760, this));
                                if (Math.random() < 0.5) {
                                    enemies.add(new Enemy(newD, 760, this));

                                } else {
                                    enemies.add(new Enemy(newB, 760, this));
                                }
                            }
                        }
                    } else if (gameTimeInSeconds > 20 && gameTimeInSeconds < 40) {
                        level = 2;
                        if (enemies.size() < 6) {
                            if (Math.random() < 0.5) {
                                enemies.add(new Enemy(newA, 760, this));
                                if (Math.random() < 0.5) {
                                    enemies.add(new Enemy(newC, 760, this));

                                } else {
                                    enemies.add(new Enemy(newD, 760, this));
                                }

                            } else {
                                enemies.add(new Enemy(newC, 760, this));
                                if (Math.random() < 0.5) {
                                    enemies.add(new Enemy(newD, 760, this));

                                } else {
                                    enemies.add(new Enemy(newB, 760, this));
                                }
                            }
                        }
                    } else if (gameTimeInSeconds > 40 && gameTimeInSeconds < 60) {
                        level = 3;
                        if (enemies.size() < 10) {
                            if (Math.random() < 0.5) {
                                enemies.add(new Enemy(newA, 760, this));
                                if (Math.random() < 0.5) {
                                    enemies.add(new Enemy(newC, 760, this));

                                } else {
                                    enemies.add(new Enemy(newD, 760, this));
                                }

                            } else {
                                enemies.add(new Enemy(newC, 760, this));
                                if (Math.random() < 0.5) {
                                    enemies.add(new Enemy(newD, 760, this));

                                } else {
                                    enemies.add(new Enemy(newB, 760, this));
                                }
                            }
                        }
                    }else if (gameTimeInSeconds > 60 && gameTimeInSeconds < 80  ) {
                        level = 4;
                        if (enemies.size() < 14) {
                            if (Math.random() < 0.5) {
                                enemies.add(new Enemy(newA, 760, this));
                                if (Math.random() < 0.5) {
                                    enemies.add(new Enemy(newC, 760, this));

                                } else {
                                    enemies.add(new Enemy(newD, 760, this));
                                }

                            } else {
                                enemies.add(new Enemy(newC, 760, this));
                                if (Math.random() < 0.5) {
                                    enemies.add(new Enemy(newD, 760, this));

                                } else {
                                    enemies.add(new Enemy(newB, 760, this));
                                }
                            }
                        }
                    }else if (gameTimeInSeconds > 80  ) {
                        level = 5;
                        if (enemies.size() < 20) {
                            if (Math.random() < 0.5) {
                                enemies.add(new Enemy(newA, 760, this));
                                if (Math.random() < 0.5) {
                                    enemies.add(new Enemy(newC, 760, this));

                                } else {
                                    enemies.add(new Enemy(newD, 760, this));
                                }

                            } else {
                                enemies.add(new Enemy(newC, 760, this));
                                if (Math.random() < 0.5) {
                                    enemies.add(new Enemy(newD, 760, this));

                                } else {
                                    enemies.add(new Enemy(newB, 760, this));
                                }
                            }
                        }
                    }
                    
                } else if (player.getHitbox2().intersects(enemy.getHitbox())) {
                    playerDie();
                }
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        g.drawString("Score: " + score, 870, 80);
        g.drawString("Level: " + level, 870, 120);
        g.drawString("Time: " + calculateGameTime() + "s", 870, 160);
        player.render(g);

        List<Enemy> enemiesCopy = new ArrayList<>(enemies);
        for (Enemy enemy : enemiesCopy) {
            enemy.render(g);
        }
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;
        long previousTime = System.nanoTime();
        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();
        double deltaU = 0;
        double deltaF = 0;

        while (isRunning) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " |  UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }

    public void closeGame() {
        isRunning = false;
         
        if (gameWindow != null) {
            gameWindow.closeWindow();
        }
    }

    public void playerDie() {
    
    entrada.setScore(score);
    JOptionPane.showMessageDialog(null, "Você morreu!");
    closeGame();
    if (gamePanel != null) {
        gamePanel.stopBackgroundMusic();
    }
    new Save().setVisible(true);
}

    private int generateRandomXInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    private int calculateGameTime() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - levelStartTime;
        return (int) (elapsedTime / 1000);
    }

    public static void main(String[] args) {
    Entrada entrada = new Entrada();
    new Game(entrada);
}

}
