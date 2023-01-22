import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyLength = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.darkGray);
        this.setFocusable(true);
        this.addKeyListener(new kontrol());
        startGame();
    }
    public void startGame(){
        newApples();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){

        if (running) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);

            }

            g.setColor(Color.red);
            g.fillRect(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyLength; i++) {
                if (i == 0) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Garuda",Font.BOLD,25));
            FontMetrics score = getFontMetrics(g.getFont());
            g.drawString("Score ="+applesEaten,(SCREEN_WIDTH- score.stringWidth("Score ="+applesEaten))/2, g.getFont().getSize());
        }else{
            gameOver(g);
        }
    }
    public void newApples(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
        for(int i = bodyLength;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    public void checkApple(){
        if((x[0]==appleX)&&(y[0]==appleY)){
            bodyLength++;
            applesEaten++;
            newApples();
        }

    }
    public void checkCollisions(){
        //check ketika kepala terkena body
        for(int i = bodyLength; i>0;i--){
            if ((x[0] == x[i]) && (y[0] == y[i]) ){
                running = false;
            }
        }
        //check ketika kepala terkena dinding kiri
        if (x[0]<0){
            running = false;
        }
        //check ketika kepala terkena dinding kana
        if (x[0]>SCREEN_WIDTH){
            running = false;
        }
        //check ketika kepala terkena dinding kiri
        if (y[0]<0){
            running = false;
        }
        //check ketika kepala terkena dinding kiri
        if (y[0]>SCREEN_HEIGHT){
            running = false;
        }

        if (running==false){
            timer.stop();
        }
    }
    public void gameOver(Graphics g) {
        this.setBackground(Color.BLACK);
        g.setColor(Color.red);
        g.setFont(new Font("Ubuntu", Font.BOLD, 50));
        FontMetrics gameOver = getFontMetrics(g.getFont());
        g.drawString("GAME OVERR", (SCREEN_WIDTH - gameOver.stringWidth("GAME OVERR")) / 2, SCREEN_HEIGHT / 2);

        g.setColor(Color.red);
        g.setFont(new Font("Garuda", Font.BOLD, 25));
        FontMetrics score = getFontMetrics(g.getFont());
        g.drawString("Score =" + applesEaten, (SCREEN_WIDTH - score.stringWidth("Score =" + applesEaten)) / 2, g.getFont().getSize());

        g.setColor(Color.cyan);
        g.setFont(new Font("Garuda", Font.PLAIN, 15));
        FontMetrics MyName = getFontMetrics(g.getFont());
        g.drawString("-bintangshada-", (SCREEN_WIDTH - score.stringWidth("-bintangshada-")) / 2, SCREEN_HEIGHT - 5);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    public class kontrol extends KeyAdapter{
        @Override
                public void keyPressed(KeyEvent e){
                switch (e.getKeyCode()){
                    case KeyEvent.VK_LEFT:
                        if(direction !='R'){
                            direction = 'L';
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if(direction !='L'){
                            direction = 'R';
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if(direction !='D'){
                            direction = 'U';
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if(direction !='U'){
                            direction = 'D';
                        }
                        break;
                }
        }
    }
}
