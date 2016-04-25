JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

HWNUM = 5

CLASSES = \
        NaiveBayesClassifierImpl.java

CLASSES_EXTRA = \
								HW$(HWNUM).java

default: classes classes_extra

classes_extra: $(CLASSES_EXTRA:.java=.class)

classes: $(CLASSES:.java=.class)

test:	default
	./test.sh

clean:
	$(RM) *.class
	$(RM) *.out

package:
	rm -f ../wfunkhouser-HW$(HWNUM).zip || exit 0
	zip -j ../wfunkhouser-HW$(HWNUM).zip ../P1.pdf ../P2.pdf $(CLASSES)

show:
	unzip -l ../wfunkhouser-HW$(HWNUM).zip
