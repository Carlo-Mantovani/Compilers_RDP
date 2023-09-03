JFLEX  = jflex
JAVAC  = javac

# targets:

all: AsdrSample.class

clean:
	rm -f *~ *.class Yylex.java

AsdrSample.class: AsdrSample.java Yylex.java
	$(JAVAC) AsdrSample.java

Yylex.java: mini_java.flex
	$(JFLEX) mini_java.flex

