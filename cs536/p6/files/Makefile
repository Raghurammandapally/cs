###
# This Makefile can be used to make a parser for the CFlat language
# (parser.class) and to make a program (P5.class) that tests the parser and
# the unparse methods in ast.java.
#
# make clean removes all generated files.
#
###

JC = javac

P6.class: P6.java parser.class Yylex.class ASTnode.class
	$(JC) -g P6.java

parser.class: parser.java ASTnode.class Yylex.class ErrMsg.class
	$(JC) parser.java

parser.java: CFlat.cup
	java java_cup.Main < CFlat.cup

Yylex.class: CFlat.jlex.java sym.class ErrMsg.class
	$(JC) CFlat.jlex.java

ASTnode.class: ast.java Type.java
	$(JC) -g ast.java

CFlat.jlex.java: CFlat.jlex sym.class
	java JLex.Main CFlat.jlex

sym.class: sym.java
	$(JC) -g sym.java

sym.java: CFlat.cup
	java java_cup.Main < CFlat.cup

ErrMsg.class: ErrMsg.java
	$(JC) ErrMsg.java

Sym.class: Sym.java Type.java ast.java
	$(JC) -g Sym.java
	
SymTable.class: SymTable.java Sym.java DuplicateSymException.java EmptySymTableException.java
	$(JC) -g SymTable.java
	
Type.class: Type.java
	$(JC) -g Type.java

DuplicateSymException.class: DuplicateSymException.java
	$(JC) -g DuplicateSymException.java
	
EmptySymTableException.class: EmptySymTableException.java
	$(JC) -g EmptySymTableException.java

###
# clean
###
clean:
	rm -f *~ *.class parser.java CSX.jlex.java sym.java tests/*.s
##test
test:
	# let's test simple output!
	java P6 tests/hello.cf tests/hello.s
	spim -file tests/hello.s
	# let's test recursion!
	java P6 tests/factorial.cf tests/factorial.s
	spim -file tests/factorial.s
	# let's test a whole bunch of our other language features!
	# here we will compile them but not run them, but they can
	#   be run with 'spim -file tests/xxx.s'
	java P6 tests/test_bool_assignment.cf tests/test_bool_assignment.s
	java P6 tests/test_bool_operations.cf tests/test_bool_operations.s
	java P6 tests/test_int_assignment.cf tests/test_int_assignment.s
	java P6 tests/test_int_operations.cf tests/test_int_operations.s
	java P6 tests/test_globals.cf tests/test_globals.s
	java P6 tests/test_return.cf tests/test_int_return.s
	java P6 tests/test_side_effects.cf tests/test_side_effects.s
	# this is expected to run until spim times the process out
	java P6 tests/test_stack.cf tests/test_stack.s

	################################################################
	# uncomment below to test exiting when there is no main method #
	################################################################
	#java P6 test_main_exit.cf test_main_exit.out
