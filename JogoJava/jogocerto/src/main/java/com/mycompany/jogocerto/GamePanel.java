package com.mycompany.jogocerto;

import Input.MouseInputs;
import Input.KeyboardInputs;
import static com.mycompany.jogocerto.Game.GAME_HEIGHT;
import static com.mycompany.jogocerto.Game.GAME_WIDTH;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private Game game;
    private BufferedImage backgroundImage;
    public Clip backgroundMusic;

    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;

        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

        requestFocus();

        try {
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("fundo.png");
            backgroundImage = ImageIO.read(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
        InputStream musicStream = getClass().getClassLoader().getResourceAsStream("credit.wav");
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicStream);
        backgroundMusic = AudioSystem.getClip();
        backgroundMusic.open(audioInput);
        
        backgroundMusic.start();
        
        
        
        FloatControl volumeControl = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(-20.0f); 
        
        backgroundMusic.stop();
        
        backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (Exception e) {
        e.printStackTrace();
    }

        setPanelSize();
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("size : " + GAME_WIDTH + " : " + GAME_HEIGHT);
    }
    
    public void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
    }

    public void updateGame() {
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this);
        }

        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}
