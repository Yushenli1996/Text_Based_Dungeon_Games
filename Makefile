JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Game.java \
	Place.java \
	Direction.java \
	Artifact.java \
	Character.java \
	NPC.java \
	Player.java \
	DecisionMaker.java \
	UI.java \
	NPC_MoveGenerator.java \
	Move.java \
	KeyboardScanner.java \
	GameTester.java \
	Monster.java \
	Key.java \
	Food.java \
	Weapon.java \
	Medicine.java \
	Pet.java \
	Monster_MoveGenerator.java \
	Pet_MoveGenerator.java \
	IO.java \
	UserInterface.java \
	NonPlayerGUI.java \
	Matts_GUI.java \
	Yushens_GUI.java \
	Birens_GUI.java \
	TextInterface.java \
	Console.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
