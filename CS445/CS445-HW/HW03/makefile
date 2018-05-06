JFLAGS = -g -cp .:junit.jar:hamcrest.jar
JC = javac
JVM = java
JUNIT = -cp .:junit.jar:hamcrest.jar org.junit.runner.JUnitCore
MAIN = Client

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Lightbulbs/Lightbulbs.java \
	Lightbulbs/Lightbulb.java \
	Buttons/Buttons.java \
	Buttons/Button.java \
	Buttons/ChainButton.java \
	Client.java \
	ChainButtonTest.java \
	ButtonTest.java \
	ClientTest.java \
	LightbulbTest.java 

TEST = \
	ChainButtonTest.java \
	ButtonTest.java \
	ClientTest.java \
	LightbulbTest.java 

default: classes

classes: $(CLASSES:.java=.class)

target: classes
	$(JVM) $(JUNIT) $(basename $(TEST))

run: classes
	$(JVM) $(MAIN)

clean:
	$(RM) *.class