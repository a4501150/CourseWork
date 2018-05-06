JFLAGS = -g -cp .:junit.jar:hamcrest.jar
JC = javac
JVM = java
JUNIT = -cp .:junit.jar:hamcrest.jar org.junit.runner.JUnitCore

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	ImprovedStringTokenizer.java \
	ImprovedRandom.java \
	ImprovedStringTokenizerTest.java \
	ImprovedRandomTest.java 

TEST = \
	ImprovedStringTokenizerTest.java \
	ImprovedRandomTest.java 

default: classes

classes: $(CLASSES:.java=.class)

target: classes
	$(JVM) $(JUNIT) $(basename $(TEST))

clean:
	$(RM) *.class