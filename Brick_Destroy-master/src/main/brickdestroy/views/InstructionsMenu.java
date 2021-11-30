package main.brickdestroy.views;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class InstructionsMenu extends JComponent implements KeyListener, MouseListener, MouseMotionListener {

    private static final String INSTRUCTIONS_TITLE = "Instructions";
    private static final Color TITLE_COLOR = new Color(255, 204, 0);
    private Font instructionsTitleFont;

    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private Rectangle menuFace;
    private Rectangle exitButton;
    private boolean exitClicked;

    Image img;

    public InstructionsMenu(Dimension area) {
        img = Toolkit.getDefaultToolkit().getImage("Brick_Destroy-master/Tiled_brick.png");
        this.setPreferredSize(new Dimension(DEF_WIDTH,DEF_HEIGHT));
        menuFace = new Rectangle(new Point(0,0),area);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        Dimension btnDim = new Dimension(area.width / 2, area.height / 12);
        exitButton = new Rectangle(btnDim);

        instructionsTitleFont = new Font("Noto Mono",Font.PLAIN,30);
    }

    public void paint(Graphics g){
        g.drawImage(img, 0, 0, this);
        drawMenu((Graphics2D)g);
    }

    public void drawMenu(Graphics2D g2d){

        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuFace.getX();
        double y = menuFace.getY();

        g2d.translate(x,y);

        //methods calls
        drawTitle(g2d);
        //drawButton(g2d);
        //end of methods calls

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    private void drawTitle(Graphics2D g2d){

        g2d.setColor(TITLE_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D instructionsRect = instructionsTitleFont.getStringBounds(INSTRUCTIONS_TITLE,frc);
        int xTitle = (int)(menuFace.getWidth() - instructionsRect.getWidth()) / 4;
        int yTitle = (int)(menuFace.getHeight() / 5);

        g2d.setFont(instructionsTitleFont);
        g2d.drawString(INSTRUCTIONS_TITLE,xTitle,yTitle);
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
