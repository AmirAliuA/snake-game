import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManagement implements KeyListener {
    private int velocityX = 0;
    private int velocityY = 0;

    public int getVelocityX() {
        return velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (velocityY != 1) {
                    velocityX = 0;
                    velocityY = -1;
                    System.out.println("Player pressed the UP ARROW KEY.");
                }
                break;
            case KeyEvent.VK_DOWN:
                if (velocityY != -1) {
                    velocityX = 0;
                    velocityY = 1;
                    System.out.println("Player pressed the DOWN ARROW KEY.");
                }
                break;
            case KeyEvent.VK_LEFT:
                if (velocityX != 1) {
                    velocityX = -1;
                    velocityY = 0;
                    System.out.println("Player pressed the LEFT ARROW KEY.");
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (velocityX != -1) {
                    velocityX = 1;
                    velocityY = 0;
                    System.out.println("Player pressed the RIGHT ARROW KEY.");
                }
                break;
            case KeyEvent.VK_SPACE:
                System.out.println("Player pressed the SPACE BUTTON & restarted the game.");
                //resetGame();
                break;
        }
    }

    // unused
    @Override
    public void keyTyped(KeyEvent e) {}

    // unused
    @Override
    public void keyReleased(KeyEvent e) {}
}