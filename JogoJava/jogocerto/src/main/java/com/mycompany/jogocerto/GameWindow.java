package com.mycompany.jogocerto;

import static com.mycompany.jogocerto.Game.GAME_HEIGHT;
import static com.mycompany.jogocerto.Game.GAME_WIDTH;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;
import mongo.Entrada;


public class GameWindow {

    private JFrame jframe;
    

    public GameWindow(GamePanel gamePanel) {
        
        

        jframe = new JFrame();
       
       
        
        
        jframe.setTitle("");
        jframe.setSize(GAME_WIDTH, GAME_HEIGHT);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
        jframe.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });
        
        
        
    }
    public void closeWindow() {
    jframe.dispose();
}
}
