/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package main.brickdestroy.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * This class draws the contents of the first page (Home Menu) of the game.
 */


public class HomeMenu extends JComponent implements MouseListener, MouseMotionListener {

    private static final String GREETINGS = "Welcome to:";
    private static final String GAME_TITLE = "Brick Destroy";
    private static final String CREDITS = "Version 0.1";
    private static final String START_TEXT = "Start";
    private static final String INSTRUCTIONS_TEXT = "Instructions";
    private static final String HIGHSCORE_TEXT = "High Scores";
    private static final String EXIT_TEXT = "Exit";

    private static final Color BG_COLOR = Color.GREEN.darker();
    private static final Color TEXT_COLOR = new Color(255, 204, 0);//dark yellow
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    private static final Color CLICKED_TEXT = Color.WHITE;
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12,6};

    private Rectangle menuFace;
    private Rectangle startButton;
    private Rectangle instructionsButton;
    private Rectangle highScoreButton;
    private Rectangle exitButton;


    private BasicStroke borderStoke;
    private BasicStroke borderStoke_noDashes;

    private Font greetingsFont;
    private Font gameTitleFont;
    private Font creditsFont;
    private Font buttonFont;

    private GameFrame owner;

    private boolean startClicked;
    private boolean instructionsClicked;
    private boolean highScoreClicked;
    private boolean exitClicked;

    private double instructionButtonDistance = 1.2;
    private double highScoreButtonDistance = 1.17;
    private double exitButtonDistance = 1.15;

    Image img;

    public HomeMenu(GameFrame owner,Dimension area){
        img = Toolkit.getDefaultToolkit().getImage("Brick_Destroy-master/Tiled_brick.png");

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;
        owner.setResizable(false);



        menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 2, area.height / 12);
        startButton = new Rectangle(btnDim);
        instructionsButton = new Rectangle(btnDim);
        highScoreButton = new Rectangle(btnDim);
        exitButton = new Rectangle(btnDim);

        borderStoke = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,DASHES,0);
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);

        greetingsFont = new Font("Noto Mono",Font.PLAIN,25);
        gameTitleFont = new Font("Noto Mono",Font.BOLD,40);
        creditsFont = new Font("Monospaced",Font.PLAIN,10);
        buttonFont = new Font("Monospaced",Font.PLAIN,startButton.height-2);
    }

    /**
     * This method initialises the drawing of the home menu.
     * @param g
     */
    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }

    /**
     * This method draws the background, text, and buttons of the home menu.
     * @param g2d
     */
    public void drawMenu(Graphics2D g2d){

        g2d.drawImage(img, 0, 0, this);

        /*
        all the following method calls need a relative
        painting directly into the HomeMenu rectangle,
        so the translation is made here so the other methods do not do that.
         */
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuFace.getX();
        double y = menuFace.getY();

        g2d.translate(x,y);

        //methods calls
        drawText(g2d);
        drawButton(g2d);
        //end of methods calls

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    /**
     * This method draws the texts of the home menu, i.e. the greetings, game title and credits.
     * @param g2d
     */
    private void drawText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();


        int yCoordinate = drawGreetings(g2d, frc);

        int yGameTitle = drawGameTitle(g2d, frc, yCoordinate);

        drawCredits(g2d, frc, yGameTitle);
    }

    /**
     * drawGreetings, drawGameTitle and drawCredits methods are extracted from drawText method for comprehension.
     * The y-coordinate needs to be taken to calculate the distance between each lines.
     */
    private int drawGreetings(Graphics2D g2d, FontRenderContext frc) {
        Rectangle2D greetingsRect = greetingsFont.getStringBounds(GREETINGS,frc);
        int xGreetings = (int)(menuFace.getWidth() - greetingsRect.getWidth()) / 2;
        int yGreetings = (int)(menuFace.getHeight() / 4);

        g2d.setFont(greetingsFont);
        g2d.drawString(GREETINGS,xGreetings,yGreetings);
        return yGreetings;
    }

    private int drawGameTitle(Graphics2D g2d, FontRenderContext frc, int yCoordinate) {
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE,frc);
        int xGameTitle = (int)(menuFace.getWidth() - gameTitleRect.getWidth()) / 2;
        int yGameTitle = (int) (gameTitleRect.getHeight() * 1.1 + yCoordinate);//add 10% of String height between the two strings

        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE,xGameTitle,yGameTitle);
        return yGameTitle;
    }

    private void drawCredits(Graphics2D g2d, FontRenderContext frc, int yGameTitle) {
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS,frc);
        int xCredits = (int)(menuFace.getWidth() - creditsRect.getWidth()) / 2;
        int yCredits = (int) (creditsRect.getHeight() * 1.1 + yGameTitle);

        g2d.setFont(creditsFont);
        g2d.drawString(CREDITS,xCredits,yCredits);
    }

    /**
     * Draws the buttons for the home menu, i.e. start button, instructions button, high score button and exit button.
     * @param g2d
     */
    private void drawButton(Graphics2D g2d){

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D startTextRect = buttonFont.getStringBounds(START_TEXT,frc);
        Rectangle2D instructionsTextRect = buttonFont.getStringBounds(INSTRUCTIONS_TEXT, frc);
        Rectangle2D exitTextRect = buttonFont.getStringBounds(EXIT_TEXT,frc);

        g2d.setFont(buttonFont);

        int x = (menuFace.width - startButton.width) / 2;
        int y =(int) ((menuFace.height - startButton.height) * 0.6);

        //draw start button
        startButton.setLocation(x,y);

        x = (int)(startButton.getWidth() - startTextRect.getWidth()) / 2;
        y = (int)(startButton.getHeight() - startTextRect.getHeight()) / 2;

        x += startButton.x;
        y += startButton.y + (startButton.height * 0.9);


        drawButtonClicked(g2d, x, y, startClicked,startButton, START_TEXT);

        x = startButton.x;
        y = startButton.y;

        y *= instructionButtonDistance;

        //draw instructions button
        instructionsButton.setLocation(x,y);

        x = (int)(instructionsButton.getWidth() - instructionsTextRect.getWidth()) / 2;
        y = (int)(instructionsButton.getHeight() - instructionsTextRect.getHeight()) / 2;

        x += instructionsButton.x;
        y += instructionsButton.y + (instructionsButton.height * 0.9);

        drawButtonClicked(g2d, x, y, instructionsClicked,instructionsButton, INSTRUCTIONS_TEXT);

        x = instructionsButton.x;
        y = instructionsButton.y;

        y *= highScoreButtonDistance;

        //draw high score button
        highScoreButton.setLocation(x,y);

        x = (int)(highScoreButton.getWidth() - instructionsTextRect.getWidth()) / 2;
        y = (int)(highScoreButton.getHeight() - instructionsTextRect.getHeight()) / 2;

        x += highScoreButton.x;
        y += highScoreButton.y + (highScoreButton.height * 0.9);

        drawButtonClicked(g2d, x, y, highScoreClicked, highScoreButton, HIGHSCORE_TEXT);

        x = highScoreButton.x;
        y = highScoreButton.y;

        y *= exitButtonDistance;

        //draw exit button
        exitButton.setLocation(x,y);

        x = (int)(exitButton.getWidth() - exitTextRect.getWidth()) / 2;
        y = (int)(exitButton.getHeight() - exitTextRect.getHeight()) / 2;

        x += exitButton.x;
        y += exitButton.y + (startButton.height * 0.9);

        drawButtonClicked(g2d, x, y, exitClicked,exitButton, EXIT_TEXT);

    }

    /**
     * This method draws how the button should look after it is clicked.
     * Method is extracted from drawButton because it is repetitive.
     * @param g2d
     * @param x The x-coordinate of the button location.
     * @param y The y-coordinate of the button location.
     * @param buttonClickedType The name of button clicked.
     * @param buttonType The name of the button.
     * @param TEXT_TYPE The text that should be shown on the button.
     */

    private void drawButtonClicked(Graphics2D g2d, int x, int y, boolean buttonClickedType, Rectangle buttonType, String TEXT_TYPE) {
        if(buttonClickedType){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(buttonType);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(TEXT_TYPE,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(buttonType);
            g2d.drawString(TEXT_TYPE,x,y);
        }
    }


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
           owner.enableGameBoard();
        }
        else if(instructionsButton.contains(p)) {
            owner.enableInstructionsMenu();
        }
        else if(highScoreButton.contains(p)) {
            owner.enableHighScoreBoard();
        }
        else if(exitButton.contains(p)){
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
            startClicked = true;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
        else if(instructionsButton.contains(p)) {
            instructionsClicked = true;
            repaint(instructionsButton.x,instructionsButton.y,instructionsButton.width+1,instructionsButton.height+1);
        }
        else if(highScoreButton.contains(p)) {
            highScoreClicked = true;
            repaint(highScoreButton.x, highScoreButton.y, highScoreButton.width+1, highScoreButton.height+1);
        }
        else if(exitButton.contains(p)){
            exitClicked = true;
            repaint(exitButton.x, exitButton.y, exitButton.width+1, exitButton.height+1);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(startClicked ){
            startClicked = false;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
        else if(instructionsClicked) {
            instructionsClicked = false;
            repaint(instructionsButton.x,instructionsButton.y,instructionsButton.width+1,instructionsButton.height+1);
        }
        else if(highScoreClicked) {
            highScoreClicked = false;
            repaint(highScoreButton.x, highScoreButton.y, highScoreButton.width+1, highScoreButton.height+1);
        }
        else if(exitClicked){
            exitClicked = false;
            repaint(exitButton.x, exitButton.y, exitButton.width+1, exitButton.height+1);
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }


    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p) || instructionsButton.contains(p) || highScoreButton.contains(p) || exitButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}
