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

import main.brickdestroy.controllers.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;

/**
 * This class directs the user to different parts of the game, i.e. game, home menu, instructions menu, etc.
 * It also automatically sets all the windows in the middle of the user's screen.
 */
public class GameFrame extends JFrame implements WindowFocusListener {

    private static final String DEF_TITLE = "Brick Destroy";

    private GameBoard gameBoard;
    private HomeMenu homeMenu;
    private InstructionsMenu instructionsMenu;
    private HighScoreBoard highscoreBoard;

    private boolean gaming;

    public GameFrame() throws IOException {
        super();

        gaming = false;

        this.setLayout(new BorderLayout());

        gameBoard = new GameBoard(this);

        instructionsMenu = new InstructionsMenu(this, new Dimension(450, 300));

        highscoreBoard = new HighScoreBoard(this, new Dimension(450, 300));

        homeMenu = new HomeMenu(this,new Dimension(450,300));

        this.add(homeMenu,BorderLayout.CENTER);

        this.setUndecorated(true);


    }

    /**
     * This method initializes the window that opens the game.
     */
    public void initialize(){
        this.setTitle(DEF_TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.autoLocate();
        this.setVisible(true);
    }

    /**
     * This method closes the home menu window and redirects the user to the game board window where they will play the game.
     */
    public void enableGameBoard(){
        this.dispose();
        this.remove(homeMenu);
        this.add(gameBoard,BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        /*to avoid problems with graphics focus controller is added here*/
        this.addWindowFocusListener(this);

    }

    /**
     * This method closes the home menu window and redirects the user to the instructions menu window.
     */
    public void enableInstructionsMenu() {
        this.dispose();
        this.remove(homeMenu);
        this.add(instructionsMenu,BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        this.addWindowFocusListener(this);
    }

    /**
     * This method closes the instructions menu or the high score board windows and redirects the user to the home menu.
     */
    public void enableHomeMenu() {
        this.dispose();
        this.remove(instructionsMenu);
        this.remove(highscoreBoard);
        this.add(homeMenu,BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        this.addWindowFocusListener(this);
    }

    /**
     * This method closes the home menu window and redirects the user to the high score menu window.
     */
    public void enableHighScoreMenu() {
        this.dispose();
        this.remove(homeMenu);
        this.add(highscoreBoard,BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        this.addWindowFocusListener(this);
    }

    /**
     * This methods measures the user's screen and automatically sets the game's windows in the middle of the screen.
     */
    private void autoLocate(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (size.width - this.getWidth()) / 2;
        int y = (size.height - this.getHeight()) / 2;
        this.setLocation(x,y);
    }


    @Override
    public void windowGainedFocus(WindowEvent windowEvent) {
        /*
            the first time the frame loses focus is because
            it has been disposed to install the GameBoard,
            so went it regains the focus it's ready to play.
            of course calling a method such as 'onLostFocus'
            is useful only if the GameBoard as been displayed
            at least once
         */
        gaming = true;
    }

    @Override
    public void windowLostFocus(WindowEvent windowEvent) {
        if(gaming)
            gameBoard.onLostFocus();

    }
}


