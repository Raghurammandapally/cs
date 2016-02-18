JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

HWNUM = 2

CLASSES = \
        FindPath.java #\

default: classes

classes: $(CLASSES:.java=.class)

testDFSBig:
	@echo 'testing DFS with big maze...'
	@echo '******************************'
	java FindPath tests/bigMaze.txt dfs > tests/bigMazeDFSOut.txt
	diff tests/bigMazeDFSOut.txt tests/bigMazeSolutionDFS.txt || exit 0
	rm -f tests/bigMazeDFSOut.txt
	@echo ' '

test: testDFSBig

clean:
	$(RM) *.class

package:
	rm -f ../wfunkhouser-HW$(HWNUM).zip || exit 0
	zip -j ../wfunkhouser-HW$(HWNUM).zip ../P1.pdf ../P2.pdf *.java

show:
	unzip -l ../wfunkhouser-HW$(HWNUM).zip
