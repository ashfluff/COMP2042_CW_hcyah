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


public class HomeMenu extends JComponent implements MouseListener, MouseMotionListener {

    private static final String GREETINGS = "Welcome to:";
    private static final String GAME_TITLE = "Brick Destroy";
    private static final String CREDITS = "Version 0.1";
    private static final String START_TEXT = "Start";
    private static final String INSTRUCTIONS_TEXT = "Instructions";
    private static final String HIGHSCORE_TEXT = "Highscores";
    private static final String EXIT_TEXT = "Exit";

    private static final Color BG_COLOR = Color.GREEN.darker();
    //private static final Color BORDER_COLOR = new Color(200,8,21); //Venetian Red
    //private static final Color DASH_BORDER_COLOR = new  Color(255, 216, 0);//school bus yellow
    private static final Color TEXT_COLOR = new Color(255, 204, 0);//dark yellow
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    private static final Color CLICKED_TEXT = Color.WHITE;
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12,6};

    private Rectangle menuFace;
    private Rectangle startButton;
    private Rectangle instructionsButton;
    private Rectangle highscoreButton;
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
    private boolean highscoreClicked;
    private boolean exitClicked;

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
        highscoreButton = new Rectangle(btnDim);
        exitButton = new Rectangle(btnDim);

        borderStoke = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,DASHES,0);
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);

        greetingsFont = new Font("Noto Mono",Font.PLAIN,25);
        gameTitleFont = new Font("Noto Mono",Font.BOLD,40);
        creditsFont = new Font("Monospaced",Font.PLAIN,10);
        buttonFont = new Font("Monospaced",Font.PLAIN,startButton.height-2);
    }


    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }


    public void drawMenu(Graphics2D g2d){

        drawContainer(g2d);

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

    private void drawContainer(Graphics2D g2d){

        //Color prev = g2d.getColor();

        //g2d.setColor(BG_COLOR);
        //g2d.fill(menuFace);

        //Stroke tmp = g2d.getStroke();

        //g2d.setStroke(borderStoke_noDashes);
        //g2d.setColor(DASH_BORDER_COLOR);
        //g2d.draw(menuFace);

        //g2d.setStroke(borderStoke);
        //g2d.setColor(BORDER_COLOR);
        //g2d.draw(menuFace);

        //g2d.setStroke(tmp);

       //g2d.setColor(prev);

        g2d.drawImage(img, 0, 0, this);
    }

    private void drawText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();


        int yCoordinate = drawGreetings(g2d, frc);

        int yGameTitle = drawGameTitle(g2d, frc, yCoordinate);

        drawCredits(g2d, frc, yGameTitle);
    }


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

        y *= 1.2;

        //draw instructions button
        instructionsButton.setLocation(x,y);

        x = (int)(instructionsButton.getWidth() - instructionsTextRect.getWidth()) / 2;
        y = (int)(instructionsButton.getHeight() - instructionsTextRect.getHeight()) / 2;

        x += instructionsButton.x;
        y += instructionsButton.y + (instructionsButton.height * 0.9);

        drawButtonClicked(g2d, x, y, instructionsClicked,instructionsButton, INSTRUCTIONS_TEXT);

        x = instructionsButton.x;
        y = instructionsButton.y;

        y *= 1.17;

        //draw highscore button
        highscoreButton.setLocation(x,y);

        x = (int)(highscoreButton.getWidth() - instructionsTextRect.getWidth()) / 2;
        y = (int)(highscoreButton.getHeight() - instructionsTextRect.getHeight()) / 2;

        x += highscoreButton.x;
        y += highscoreButton.y + (highscoreButton.height * 0.9);

        drawButtonClicked(g2d, x, y, highscoreClicked,highscoreButton, HIGHSCORE_TEXT);

        x = highscoreButton.x;
        y = highscoreButton.y;

        y *= 1.15;

        //draw exit button
        exitButton.setLocation(x,y);

        x = (int)(exitButton.getWidth() - exitTextRect.getWidth()) / 2;
        y = (int)(exitButton.getHeight() - exitTextRect.getHeight()) / 2;

        x += exitButton.x;
        y += exitButton.y + (startButton.height * 0.9);

        drawButtonClicked(g2d, x, y, exitClicked,exitButton, EXIT_TEXT);

    }

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
        else if(highscoreButton.contains(p)) {
            owner.enableHighscoreMenu();
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
        else if(highscoreButton.contains(p)) {
            highscoreClicked = true;
            repaint(highscoreButton.x,highscoreButton.y,highscoreButton.width+1,highscoreButton.height+1);
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
        else if(highscoreClicked) {
            highscoreClicked = false;
            repaint(highscoreButton.x,highscoreButton.y,highscoreButton.width+1,highscoreButton.height+1);
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
        if(startButton.contains(p) || instructionsButton.contains(p) || highscoreButton.contains(p) || exitButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}
