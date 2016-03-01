JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

HWNUM = 3

FILE = DecisionTreeImpl

CLASSES = \
        $(FILE).java #\

FLAG = 2

default: classes

classes: $(CLASSES:.java=.class)

test:	$(FILE).class HW3.class
	java HW3 $(FLAG) balance_train.txt balance_tune.txt balance_test.txt > balance$(FLAG).out
	diff balance$(FLAG).out outb$(FLAG).txt

clean:
	$(RM) *.class

package:
	rm -f ../wfunkhouser-HW$(HWNUM).zip || exit 0
	zip -j ../../../wfunkhouser-HW$(HWNUM).zip ../../../P1.pdf ../../../P2.pdf $(FILE).java

show:
	unzip -l ../../../wfunkhouser-HW$(HWNUM).zip
