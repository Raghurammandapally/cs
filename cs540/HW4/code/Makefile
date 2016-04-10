JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

HWNUM = 4

FILE = NNImpl

CLASSES = \
        $(FILE).java #\

default: classes

classes: $(CLASSES:.java=.class)

test:	$(FILE).class HW$(HWNUM).class
	./test.sh

clean:
	$(RM) *.class
	$(RM) *.out

package:
	rm -f ../wfunkhouser-HW$(HWNUM).zip || exit 0
	zip -j ../wfunkhouser-HW$(HWNUM).zip ../P1.pdf ../P2.pdf $(FILE).java

show:
	unzip -l ../wfunkhouser-HW$(HWNUM).zip
