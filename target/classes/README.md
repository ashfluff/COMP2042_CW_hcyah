Background image credits:
https://commons.wikimedia.org/wiki/File:Tiled_brick.jpg
https://www.reddit.com/r/PixelArt/comments/2cdh4i/dungeon_background_ocwip/


Info page named instructions menu because it best describes what the page 
is about.
The high score board shows the top ten scores, ordered from greatest (from the left)
to the smallest.
Implemented return buttons on the instructions and high score page to return to 
the home menu.
Removed drawContainer method in HomeMenu class because it is redundant.
Added background images for the home menu and game levels that suits the game more.
Initially, the windows are resizable, so the player is able to resize the windows,
but I made them non-resizable otherwise they will see the unnecessary white screen.
In HomeMenu class, drawGreetings, drawGameTitle and drawCredits method are extracted from
drawText method to make drawText easier to read and understand. drawGreetings, 
drawGameTitle and drawCredits methods have relatively the same principle and functions, anyway.
drawButtonClicked method extracted from drawButton method to draw the buttons when they are
clicked. Before it was used twice in the drawButton method, but as I added more buttons it
had to be extracted into a method to be simplified otherwise the code will be repeated four times.
Override methods (mouseClicked, mousePressed, mouseReleased, mouseMoved) in HomeMenu, HighScoreBoard and 
InstructionsMenu classes had additions to implement the additional buttons added.
Made constant parameters for highScoreButtonDistance, instructionButtonDistance and exitButtonDDistance to improve
refactoring. Also deleted some Color constants as they have become redundant after adding background image.
GameFrame classes were added enableHomeMenu, enableInstructionsMenu and enableHighScoreboard so that when the player
clicks on the buttons, the methods will be called respective to the buttons clicked and the current window will close
and the Home Menu, Instructions Menu or High Score Board window will open up.
In Brick class, UP_IMPACT, DOWN_IMPACT, LEFT_IMPACT AND RIGHT_IMPACT were used to be assigned to integers so when
a method calls them, their parameters would be an integer. Therefore, I changed them to an enum ImpactDirection so that 
they can be called instantly. Because of that, the parameters of method findImpact have to be changed to ImpactDirection
and an extra constant called NO_IMPACT has to be created to initialise the variable 'out' and has to be called
when in 'if(broken)', NO_IMPACT means that the ball has not made contact with the wall.
In Wall class, methods have been extracted from makeLevels method and put into their own class, LevelFactory. Initially,
it was too messy to read and understand, so with that it will be easier to comprehend. 
In LevelFactory class, makeSingleType level is called to make walls for only one type of brick. makeChessBoard level is
called to make walls with more than one type of brick. An enum is made called WallType and initially it was the
variables were assigned to integers. With the enum, it is easier to understand what is being called and what bricks
are being used to make the walls in each level. This enum is used in Wall and LevelFactory class.