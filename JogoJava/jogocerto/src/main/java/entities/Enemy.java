package entities;
import com.mycompany.jogocerto.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.imageio.ImageIO;
import utilz.LoadSave;
import static utilz.Constants.PlayerConstants.IDLE;
import static utilz.Constants.PlayerConstants.RUNNING;
import static utilz.Constants.PlayerConstants.GetSpriteAmount;


public class Enemy extends Entity {
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 25;
    private int playerAction = IDLE;
    private boolean movingLeft = true;
    private float playerSpeed = 1.5f;
    private int steps = 0;
    private int maxSteps = 50;
    private Rectangle hitbox; 
    private int health = 50;
    private boolean defeated = false;
     private Game game;
    

    public Enemy(float x, float y, Game game) {
        super(x, y);
        this.game = game;
        loadAnimations();
        hitbox = new Rectangle((int) x + 110, (int) y, 50, 130); // Inicialização da hitbox
    }
    
    public float getX() {
        return x;
    }
    
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            handleDefeat();
        }
    }
    
    private void handleDefeat() {
        
        game.removeEnemy(this);  
    }
    
    
    
     
    
    

    public void update() {
        if (movingLeft) {
            x -= playerSpeed;
        } else {
            x += playerSpeed;
        }

        hitbox.setLocation((int) x, (int) y);

        if (x <= -150) { 
            movingLeft = false;
        } else if (x >= 1800) { 
            movingLeft = true;
        }
        

        if (movingLeft) {
            playerAction = RUNNING;
        } else {
            playerAction = RUNNING; 
        }

        updateAnimationTick();
    }

   public void render(Graphics g) {
    BufferedImage currentFrame = animations[playerAction][aniIndex];

    if (movingLeft) {
        
        currentFrame = flipImageHorizontally(currentFrame);
    }

    g.drawImage(currentFrame, (int) x, (int) y, 256, 160, null);

   
}

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
            }
        }
    }

   private void loadAnimations() {
    InputStream is = getClass().getResourceAsStream("/enemy_sprites.png");
    BufferedImage img = null;

    try {
        img = ImageIO.read(is);
    } catch (IOException e) {
        e.printStackTrace();
    }

    animations = new BufferedImage[9][6];
    for (int j = 0; j < animations.length; j++) {
        for (int i = 0; i < animations[j].length; i++) {
            animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
        }
    }
}


    private BufferedImage flipImageHorizontally(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage flippedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = flippedImage.createGraphics();
        g.drawImage(originalImage, width, 0, -width, height, null);
        g.dispose();
        return flippedImage;
    }
    
     

    
    public Rectangle getHitbox() {
    return hitbox;
}
    
    public void setX(float x) {
        this.x = x;
    }
}

