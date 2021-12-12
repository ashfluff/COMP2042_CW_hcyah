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
