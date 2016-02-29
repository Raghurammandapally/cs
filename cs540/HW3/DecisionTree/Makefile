JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

HWNUM = 3

FILE = DecisionTreeImpl

CLASSES = \
        $(FILE).java #\

default: classes

classes: $(CLASSES:.java=.class)

test:	$(FILE).class
	java HW3 1 balance_train.txt balance_tune.txt balance_test.txt
	#java HW3 1 test.txt balance_tune.txt balance_test.txt

clean:
	$(RM) *.class

package:
	rm -f ../wfunkhouser-HW$(HWNUM).zip || exit 0
	zip -j ../../../wfunkhouser-HW$(HWNUM).zip ../../../P1.pdf ../../../P2.pdf $(FILE).java

show:
	unzip -l ../../../wfunkhouser-HW$(HWNUM).zip
