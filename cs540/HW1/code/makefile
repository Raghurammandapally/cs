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
	echo 'testing DFS with small maze...'
	java FindPath tests/smallMaze.txt dfs > tests/smallMazeDFSOut.txt
	diff tests/smallMazeDFSOut.txt tests/smallMazeSolutionDFS.txt || exit 0
	rm -f tests/smallMazeDFSOut.txt
testAStarSmall:
	echo 'testing AStar with small maze...'
	java FindPath tests/smallMaze.txt astar > tests/smallMazeAStarOut.txt
	diff tests/smallMazeAStarOut.txt tests/smallMazeSolutionAStar.txt || exit 0
	rm -f tests/smallMazeAStarOut.txt
testAStarMedium:
	echo 'testing AStar with medium maze...'
	java FindPath tests/mediumMaze.txt astar > tests/mediumMazeAStarOut.txt
	diff tests/mediumMazeAStarOut.txt tests/mediumMazeSolutionAStar.txt || exit 0
	rm -f tests/mediumMazeAStarOut.txt
testAStarBig:
	echo 'testing AStar with big maze...'
	java FindPath tests/bigMaze.txt astar > tests/bigMazeAStarOut.txt
	diff tests/bigMazeAStarOut.txt tests/bigMazeSolutionAStar.txt || exit 0
	rm -f tests/bigMazeAStarOut.txt

test: testAStarSmall testDFSSmall testAStarMedium

clean:
	$(RM) *.class

