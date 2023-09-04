# Authors
Aloysio Winter, Carlo Mantovani, Felipe Elsner


# Compile

## Linux
	make
## Windows
	java -jar jflex.jar mini_java.flex
	javac AsdrSample.java

# Run
	java AsdrSample Program.java
	
	
# Addendum
The command tokens (if, else, while, among others) were included in the lexical analyser (mini-java.flex) despite the fact that they are not actually used by the Recursive Descent Parser in this case. This was done intentionally since they are part of the original grammar of Mini-Java.