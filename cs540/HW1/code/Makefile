JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        FindPath.java #\

default: classes

classes: $(CLASSES:.java=.class)

testDFSSmall:
	@echo 'testing DFS with small maze...'
	@echo '******************************'
	java FindPath tests/smallMaze.txt dfs > tests/smallMazeDFSOut.txt
	diff tests/smallMazeDFSOut.txt tests/smallMazeSolutionDFS.txt || exit 0
	rm -f tests/smallMazeDFSOut.txt
	@echo ' '
testAStarSmall:
	@echo 'testing AStar with small maze...'
	@echo '******************************'
	java FindPath tests/smallMaze.txt astar > tests/smallMazeAStarOut.txt
	diff tests/smallMazeAStarOut.txt tests/smallMazeSolutionAStar.txt || exit 0
	rm -f tests/smallMazeAStarOut.txt
	@echo ' '
testAStarMedium:
	@echo 'testing AStar with medium maze...'
	@echo '******************************'
	java FindPath tests/mediumMaze.txt astar > tests/mediumMazeAStarOut.txt
	diff tests/mediumMazeAStarOut.txt tests/mediumMazeSolutionAStar.txt || exit 0
	rm -f tests/mediumMazeAStarOut.txt
	@echo ' '
testAStarBig:
	@echo 'testing AStar with big maze...'
	@echo '******************************'
	java FindPath tests/bigMaze.txt astar > tests/bigMazeAStarOut.txt
	diff tests/bigMazeAStarOut.txt tests/bigMazeSolutionAStar.txt || exit 0
	rm -f tests/bigMazeAStarOut.txt
	@echo ' '
testDFSBig:
	@echo 'testing DFS with big maze...'
	@echo '******************************'
	java FindPath tests/bigMaze.txt dfs > tests/bigMazeDFSOut.txt
	diff tests/bigMazeDFSOut.txt tests/bigMazeSolutionDFS.txt || exit 0
	rm -f tests/bigMazeDFSOut.txt
	@echo ' '

test: testAStarSmall testDFSSmall testAStarMedium testAStarBig testDFSBig

clean:
	$(RM) *.class

package:
	rm -f wfunkhouser-HW1.zip || exit 0
	zip -j ../wfunkhouser-HW1.zip ../P1.pdf ../P2.pdf *.java

show:
	unzip -l ../wfunkhouser-HW1.zip
