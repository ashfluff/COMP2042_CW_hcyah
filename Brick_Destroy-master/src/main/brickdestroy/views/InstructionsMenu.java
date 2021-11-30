package main.brickdestroy.views;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class InstructionsMenu extends JComponent implements MouseListener, MouseMotionListener {

    private static final String INSTRUCTIONS_TITLE = "Instructions";
    private static final String RETURN_TEXT = "Return";
    private static final Color TITLE_COLOR = new Color(255, 204, 0);
    private static final Color CLICKED_TEXT = Color.WHITE;

    private Font instructionsTitleFont;
    private Font buttonFont;

    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private Rectangle menuFace;
    private Rectangle returnButton;
    private boolean returnClicked;

    Image img;

    public InstructionsMenu(Dimension area) {
        img = Toolkit.getDefaultToolkit().getImage("Brick_Destroy-master/Tiled_brick.png");
        this.setPreferredSize(new Dimension(DEF_WIDTH,DEF_HEIGHT));
        menuFace = new Rectangle(new Point(0,0),area);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        Dimension btnDim = new Dimension(area.width / 4, area.height / 12);
        returnButton = new Rectangle(btnDim);

        instructionsTitleFont = new Font("Noto Mono",Font.PLAIN,30);
        buttonFont = new Font("Monospaced",Font.PLAIN, returnButton.height-2);
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
        drawButton(g2d);
        //end of methods calls

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    private void drawTitle(Graphics2D g2d){

        g2d.setColor(TITLE_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D instructionsRect = instructionsTitleFont.getStringBounds(INSTRUCTIONS_TITLE,frc);
        int xTitle = (int)(menuFace.getWidth() - instructionsRect.getWidth()) / 6;
        int yTitle = (int)(menuFace.getHeight() / 8);

        g2d.setFont(instructionsTitleFont);
        g2d.drawString(INSTRUCTIONS_TITLE,xTitle,yTitle);
    }

    private void drawButton(Graphics2D g2d) {
        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D buttonTextRect = buttonFont.getStringBounds(RETURN_TEXT,frc);

        g2d.setFont(buttonFont);

        int x = (menuFace.width - returnButton.width) / 5;
        int y =(int) ((menuFace.height - returnButton.height) * 1.5);

        //draw start button
        returnButton.setLocation(x,y);

        x = (int)(returnButton.getWidth() - buttonTextRect.getWidth()) / 2;
        y = (int)(returnButton.getHeight() - buttonTextRect.getHeight()) / 2;

        x += returnButton.x;
        y += returnButton.y + (returnButton.height * 0.9);

        if(returnClicked){
            Color tmp = g2d.getColor();
            g2d.draw(returnButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(RETURN_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(returnButton);
            g2d.drawString(RETURN_TEXT,x,y);
        }
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        Point p = e.getPoint();
        if(returnButton.contains(p)){
            //go back to menu page
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();
        if(returnButton.contains(p)){
            returnClicked = true;
            repaint(returnButton.x,returnButton.y,returnButton.width+1,returnButton.height+1);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(returnClicked ){
            returnClicked = false;
            repaint(returnButton.x,returnButton.y,returnButton.width+1,returnButton.height+1);
        }
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
