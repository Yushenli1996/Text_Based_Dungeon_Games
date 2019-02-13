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

1) The first thing as a group we did was to discuss who would give what classes. After deciding what classes each group
member will give, we went ahead and combined all of the classes into a one file. (Done by Matt Jankowski and Yushen Li)

2)The next task was to compile the code and watch it work just like magic :) just kidding. Obviously the code did not
compile and our task was to fix the code first. (Done by Matt Jankowski and Yushen Li)
________________________________________________________________________________________________________________________
3) Once the code was fixed, we decided to split the work load in the following way:

Biren's Part:
In my part, I went ahead and created a Food Class, Weapon Class, and Medicine Class. Here are the following Details for
each class. All of the classes Inherited from the Artifact class.

Food Class:  Food class inherits from Artifact.
Basically in this class, there will be food throughout couple of places. Players have the ability to GET Food and USE
Food. If a player gets the food, it'll added to inventory and then when a player eats the food, it'll be dropped from
the inventory. By eating the food, players can increase their health.iArtifactUsageVal variable will act as food
strength for this class.


Medicine Class: Medicine class inherits from the artifact class. Players will have the ability to use medicine. Players
will have the ability to use and pick up medicine. If a player eats the medicine, it'll increase their health and be
dropped from inventory once its used.

Weapon Class: Places will now have weapons. In this, Player's will have the ability to pick up weapons and drop weapons.
They can also use weapons to hurt other players IF they're in the same room. If the weapon is used, it'll decrease their
health by certain point amount and the way that player can increase his or her health will be through either eating
medicine or eating food.

Also Created the UML documentation for all the new features that were added to this game.
________________________________________________________________________________________________________________________


Yushen's Part:

Merged all the code today, fix all the bugs in all source code; added backwards compatibility to earlier game versions.
Expand the Place class and added two new inheritance classes with description below. The first class is HealingRoom and
the second class is PoisonRoom. Both of the classes Inherit from place class. Added a lot of new features to different
classes such as weapon has different attack damage. Also help both teammate to debug all their code.

Poison Room class:
It's an inheritance class from Place, which its special ability is to poison player once player walk in; however, it
doesn't effect other character

Healing Room class:
It's an inheritance class from Place, which its special ability is to heal all characters once they walk in.

Weapon class:
Add weapon damage feature, every weapon now has damage range +-15% of its based attack value. So there is a change to
get a good roll. Also fixed problem of character never die in game

Food and Medicine class:
Fixed problem of infinite amount of artifact once picked up, also fixed health issue that use food or medicine can exceed
its character's max hp.

Added in a battle system:
Player can fight against the monster in see hit point or attack information during fight.

________________________________________________________________________________________________________________________

Matt's Part:

Matt added four new classes. The classes that were added were: Monster class, Pet Class, Monster_MoveGenerator, and
Pet_MoveGenerator.The Monster and Pet class both Inherit from Character Class.

Monster Class (Aggresive Character):
    Extends the Character class; makeMove() is overriden with these differences:
	* Allowed moves: 'go', 'use', and 'pass'.
	* Since the monster has only weapons, 'use' basically means attack.

Monster_MoveGenerator Class:
    Implements DecisionMaker. Decides which of the 3 moves the monster should make, with a priority given to 'use'.

Pet Class (Friendly Character):
    Extends the Character class; makeMove() is overriden with these differences:
	* Allowed moves: 'go', 'get', and 'pass'
	* A pet starts out by itself but a player can tame it with the 'tame' command.
	* When tamed, the pet gives player all its artifacts and from that point on will get artifacts for the player.

Pet_MoveGenerator Class:
    Implements DecisionMaker. Decides which of the 3 moves the monster should make, with a priority given to 'use'.

Matt also worked on
    * debugging together with Yushen and fixing many problems with various methods.
    * Added comments.
    * Worked on supporting methods (adding, removing, printing, iterating, disperseArtifacts)
    * Added several simple moves (dance, sleep)
    * Added support for shortcuts: eat vs. use, inve vs. inventory, p vs. pass
    * create a tame move so that players can tame pets when they're in the same room
    * Modified the GDF and other files with Yushen