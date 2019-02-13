CS 342 GROUP Project (HW4)
Biren Patel
Yushen Li | yli265 | 670896761 | yli265@uic.edu
Matt Jankowski | mjanko5 | 670657390 | mjanko5@uic.edu
________________________________________________________________________________________________________________________
Instruction of running the game:
To run this program, please type in make,
then java GameTester "FILENAME"
Example:
        make
            java GameTester "MystiCity 31.gdf"
            or java GameTester "MysticCity_40_untested.gdf"
            or java GameTester "MysticCity_41_untested.gdf"        [Modified to accept arguments]
            or java GameTester "MysticCity_50.gdf"
            or java GameTester "MysticCity_51_Tester.gdf"
            or java GameTester "MysticCity_51_Final".gdf"          [Final verison of the game]

            IMPORTANT NOTE: Use the following file test out second argument
            java GameTester "MystiCity 31.gdf" # (must be a positive number)
            or java GameTester "Mysticity_41_untested.gdf" #(must be a positive number)


The commands to use are: GO, LOOK, EXIT, QUIT, INVE, GET, DROP, SLEEP, DANCE, HELP
For the Direction, please type in N,S,E,W,U,D....
Example: GO N, GO D, GO U

Type in gui1 or gui2, gui3 to switch GUI for TextInterface only

For LOOK, please type in LOOK

FOR Exiting or quitinng the game, please type in EXIT or QUIT

For the get, please type in GET + item name
Example: GET Brass key, GET Leather bag

For the drop, please type in drop + item name
Example: DROP Brassy Key, DROP Leather bag

For the inventory, please type in INVE;
this would display all inventory items and info of items

When running Mysticity 31, it's going to ask user for input for
the character's name, please type in anything name you like.

When running Mysticity_41, if argument contains number after filename,
it would also prompt the users to input number of character names.
________________________________________________________________________________________________________________________
Work Details:
(Done by Matt Jankowski and Yushen Li)
1) Created all new classes such as TextInterface, UserInterface, IO, etc
    * TextInterface is a terminal
    * UserInterface is a interface that connects the IO to GUI
    * Console is an UI that display all game history logs
    * NonPlayerGUi is a empty GUI for nonPlayer objects to access
    * IO creates the connecting and memorize the implementor for different character objects

2) Fixed Getline() concurrency issue and threading issue
    * Add Thread sleep to help solving concurrency issue

3) Changed all KeyBoardScanner to getline(), unless it's in constructor or TextInterface
    * Based on assignment requirement

4) Connected IO to all characters.
    * All characters have their individual IO and a GUI to do getline, display displayln

5) Added NonPlayer Attacking AI feature
    *Monster now able to select random character to attack

6) Removed all System.out.println and Scanner
    * Based on assignment requirement
________________________________________________________________________________________________________________________
Individual workflow:
Biren's Part:
- Finished his GUI.
________________________________________________________________________________________________________________________

Yushen's Part:
- Created Yushen_GUI and add a panel to add all buttons and actionListener and functionality
- Add Game Console to keep logs for the game
- Add NonPlayerGUI.java for NonPlayer objects to call display, displayln
- Added AI attacking feature to Monster object
- Figure out concurrency issue and add threading support.
________________________________________________________________________________________________________________________

Matt's Part:
- Created Matts_GUI with components such as JLabels, JTextAreas, JComboBox, JTextField, etc.
- Started out by creating the classes IO.java, UserInterface, and 4 interfaces
- Made a UML diagram for all new features in Homework 5
- Figured out concurrency issue using boolean myTurn