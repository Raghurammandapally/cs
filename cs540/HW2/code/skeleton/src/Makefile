JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

HWNUM = 2

CLASSES = \
        wfunkhouserPlayer.java #\

default: classes

classes: $(CLASSES:.java=.class)

test:	Mancala.class wfunkhouserPlayer.class
	java Mancala wfunkhouserPlayer

clean:
	$(RM) *.class

package:
	rm -f ../wfunkhouser-HW$(HWNUM).zip || exit 0
	zip -j ../../../wfunkhouser-HW$(HWNUM).zip ../../../P1.pdf ../../../P2.pdf wfunkhouserPlayer.java

show:
	unzip -l ../../../wfunkhouser-HW$(HWNUM).zip
