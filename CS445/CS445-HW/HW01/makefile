JFLAGS = -g
JC = javac
JVM = java
MAIN = TestCreature

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Thing.java \
	Creature.java \
	Tiger.java \
	Ant.java \
	Flyer.java \
	Fly.java \
	Bat.java \
	TestCreature.java 

default: classes

classes: $(CLASSES:.java=.class)

run: classes
	$(JVM) $(MAIN)

clean:
	$(RM) *.class