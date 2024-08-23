import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Game extends JPanel implements ActionListener {
    int boardWidth;
    int boardHeight;
    int tileSize = 25; // 25 pixels
    private final InputManagement inputManagement;

    Tile snakeHead;
    Tile food;
    Random random;
    ArrayList<Tile> snakeBody;

    // game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    private static class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    Game(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);

        inputManagement = new InputManagement();
        addKeyListener(inputManagement);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<>();
        food = new Tile(10, 10);
        random = new Random();
        generateFood();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this); // 100 milliseconds = 1/10 of a second
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //drawGrid(g);
        drawSnake(g);
        drawFood(g);
        drawScore(g);
    }

    private boolean collisionCheck(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void generateFood() {
        food.x = random.nextInt(boardWidth / tileSize); // 600 / 25 = 24
        food.y = random.nextInt(boardHeight / tileSize); //
    }

    public void move() {
        // eat food
        if(collisionCheck(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            generateFood();
        }

        // snake body
        for(int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);

            if(i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else
            {
                // move the first part of the body to the previous head position
                Tile previousSnakePart = snakeBody.get(i - 1);
                snakePart.x = previousSnakePart.x;
                snakePart.y = previousSnakePart.y;
            }
        }

        // snake
        snakeHead.x += inputManagement.getVelocityX();
        snakeHead.y += inputManagement.getVelocityY();

        // game over condition
        for (Tile snakePart : snakeBody) {
            // collide with the snake head
            if (collisionCheck(snakeHead, snakePart)) {
                gameOver = true;
                System.out.println("The player has died and the game is over.");
                break;
            }
        }

        if(snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth || snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > boardHeight) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if(gameOver) {
            gameLoop.stop();
        }
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.GRAY);

        for(int i = 0; i < boardWidth / tileSize; i++) {
            // (x1, y1, x2, y2)
            g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
            g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
        }
    }

    private void drawSnake(Graphics g) {
        // snake head
        g.setColor(Color.green);
        //g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        // snake body
        for (Tile snakePart : snakeBody) {
            //g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }
    }

    private void drawFood(Graphics g) {
        g.setColor(Color.red);
        //g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);
    }

    private void drawScore(Graphics g) {
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.setColor(gameOver ? Color.RED : Color.WHITE);
        String message = gameOver ? "Game Over: " : "Score: ";
        g.drawString(message + snakeBody.size(), tileSize - 16, tileSize);
    }
}