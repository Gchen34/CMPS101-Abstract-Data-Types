#------------------------------------------------------------------------------
#Guangyang Chen gchen34@ucsc.edu
#CMPS101 PA1
#------------------------------------------------------------------------------ 

MAINCLASS  = Lex
JAVAC      = javac 
JAVASRC    = $(wildcard *.java)
SOURCES    = $(JAVASRC) makefile README
CLASSES    = $(patsubst %.java, %.class, $(JAVASRC))
JARCLASSES = $(patsubst %.class, %*.class, $(CLASSES))
JARFILE    = $(MAINCLASS) 

all: $(JARFILE)
	
$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(JARCLASSES)
	chmod +x $(JARFILE)
	rm Manifest

%.class: %.java
	$(JAVAC) $<

clean:
	rm -f *.class $(JARFILE)
submit: README Makefile Lex.java List.java ListClient.java
	submit cmps101-pt.s18 pa1 README Makefile Lex.java List.java ListClient.java