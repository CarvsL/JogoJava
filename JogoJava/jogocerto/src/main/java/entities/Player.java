package entities;

import com.mycompany.jogocerto.Carregamento;
import com.mycompany.jogocerto.Game;
import com.mycompany.jogocerto.GameWindow;
import com.mycompany.jogocerto.MainFoda;
import com.mycompany.jogocerto.PaginaStart;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.awt.Rectangle; 
import java.awt.Color; 
import javax.swing.JOptionPane;
import static utilz.Constants.PlayerConstants.ATTACK_1;
import utilz.LoadSave;
import static utilz.Constants.PlayerConstants.IDLE;
import static utilz.Constants.PlayerConstants.RUNNING;
import static utilz.Constants.PlayerConstants.GetSpriteAmount;

public class Player extends Entity {
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 25;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;
    private float playerSpeed = 2.0f;
    private boolean lastDirectionLeft = false;
    private float gravity = 0.02f;
    private float verticalSpeed = 0.0f;
    private boolean isJumping = false;
    private float jumpStrength = -2.0f;
    private Rectangle hitbox; 
    private Rectangle hitbox2; 
    private GameWindow gameWindow;
    
    private int attackCooldown = 100; 
    private int attackCooldownCounter = 0;
    

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
        hitbox = new Rectangle((int) x + 110, (int) y, 50, 130); 
        hitbox2 = new Rectangle((int) x + 110, (int) y, 50, 130); 
    }
   

   public void update() {
    applyGravity();
    updatePos();
    updateAnimationTick();
    setAnimation();

    
    if (left) {
        lastDirectionLeft = true;
    } else if (right) {
        lastDirectionLeft = false;
    }

    
    if (lastDirectionLeft) {
        hitbox.setLocation((int) x - 40, (int) y);
        hitbox2.setLocation((int) x, (int) y);
    } else {
        hitbox.setLocation((int) x + 20, (int) y);
        hitbox2.setLocation((int) x - 20, (int) y);
    }

    
    if (attackCooldownCounter > 0) {
        attackCooldownCounter--;
    }

   
    if (attacking && attackCooldownCounter == 0) {
        attack();
    }
}


private void attack() {
    

    
    attackCooldownCounter = attackCooldown;
}

public void setAttacking(boolean attacking) {
    
    if (!this.attacking && attackCooldownCounter == 0) {
        this.attacking = attacking;
    }
}



    public void render(Graphics g) {
        
        if (lastDirectionLeft) {
            
            BufferedImage flippedImage = flipImageHorizontally(animations[playerAction][aniIndex]);
            g.drawImage(flippedImage, (int) x, (int) y, 256, 160, null);
        } else {
            g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, 256, 160, null);
        }

        
        
    }

    private void applyGravity() {
        if (y < 760) {
            verticalSpeed += gravity;
            y += verticalSpeed;
        } else {
            verticalSpeed = 0.0f;
            y = 760;
        }
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
            }
        }
    }

    private void setAnimation() {
        int startAni = playerAction;

        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (attacking) {
            
            playerAction = ATTACK_1;
        }

        if (startAni != playerAction) {
            resetAniTick();
        }
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos() {
        moving = false;

        if (left && !right) {
            x -= playerSpeed;
            moving = true;
        } else if (right && !left) {
            x += playerSpeed;
            moving = true;
        }

        
        if (y >= 760 && !isJumping) {
            if (up && !down) {
                
                isJumping = true;
                verticalSpeed = jumpStrength;
            }
        }

        
        if (isJumping) {
            y += verticalSpeed;
            verticalSpeed += gravity;

            if (y >= 760) {
                isJumping = false;
                y = 760; 
                verticalSpeed = 0.0f;
            }
        }

        
        if (down && !up) {
            
            moving = true;
        }
    }

    private void loadAnimations() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");

        try {
            BufferedImage img = ImageIO.read(is);

            animations = new BufferedImage[9][6];
            for (int j = 0; j < animations.length; j++) {
                for (int i = 0; i < animations[j].length; i++) {
                    animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    
    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
    public float getX() {
    return x;
}
    

    private BufferedImage flipImageHorizontally(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage flippedImage = new BufferedImage(width, height, image.getType());
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                flippedImage.setRGB(width - 1 - x, y, image.getRGB(x, y));
            }
        }
        return flippedImage;
    }
    public Rectangle getHitbox() {
    return hitbox;
}
    
    public Rectangle getHitbox2() {
    return hitbox2;
}
    public boolean isAttacking() {
    return attacking;
}

}
