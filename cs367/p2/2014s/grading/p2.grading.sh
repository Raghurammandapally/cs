#!/bin/sh

cd ~

if [ ! -z $1 ] ; then
echo "Running tests for "$1" . . ."
      cd ~/cs367_grading_p2/$1
	  rm -rf Output.txt
	  found_all_files=true
		  if [ -f LoopADT.java ]; then
			echo "LoopADT.java submitted unneccessarily. Overwriting it now." | tee -a Output.txt
		  fi
		  cp /p/course/cs367-skrentny/public/html/assignments/p2/grading/files/LoopADT.java .
		  if [ ! -f EmptyLoopException.java ]; then
			echo "Could not find EmptyLoopException.java." | tee -a Output.txt
			found_all_files=false
		  fi
		  if [ ! -f DblListnode.java ]; then
			echo "Could not find DblListnode.java." | tee -a Output.txt
			found_all_files=false
		  fi
		  if [ -f DotMatrix.java ]; then
			echo "DotMatrix.java submitted unneccessarily. Overwriting it now." | tee -a Output.txt
		  fi
		  cp /p/course/cs367-skrentny/public/html/assignments/p2/grading/files/DotMatrix.java .
		  if [ ! -f UnrecognizedCharacterException.java ]; then
			echo "Could not find UnrecognizedCharacterException.java." | tee -a Output.txt
			found_all_files=false
		  fi
		  if [ ! -f MessageLoop.java ]; then
			echo "Could not find MessageLoop.java." | tee -a Output.txt
			found_all_files=false
		  fi
		  if [ ! -f MessageLoopIterator.java ]; then
			echo "Could not find MessageLoopIterator.java." | tee -a Output.txt
			found_all_files=false
		  fi
		  if [ ! -f DisplayEditor.java ]; then
			echo "Could not find DisplayEditor.java." | tee -a Output.txt
			found_all_files=false
		  fi
		  found_readme=false
		  if [ -f README.txt ]; then
			found_readme=true
		  fi
		  if [ ! $found_all_files ] &&  [ ! $found_readme ]; then
			echo "Could not find a README either. This student either named some files incorrectly or did not submit everything they needed to." | tee -a Output.txt
			echo "Can't find the required files for this student. Check their folder."
		  fi
		  
		  if $found_readme ; then
			echo "Found a README. This student worked with a partner, view README." | tee -a Output.txt
			#echo "This student worked with a partner. View their README file."
		  fi
	  
	  
	  if $found_all_files ; then
		  compiled=true
	      	  echo "Running tests... " | tee -a Output.txt
		  echo " " | tee -a Output.txt
		  cp /p/course/cs367-skrentny/public/html/assignments/p2/grading/files/MessageLoopTester.java .	
		  cp /p/course/cs367-skrentny/public/html/assignments/p2/grading/files/*.txt .

		  echo "============== Compiling LoopADT.java ===============" | tee -a Output.txt
		  javac LoopADT.java | tee -a Output.txt
		  if [ ! -f LoopADT.class ] ; then
			echo "LoopADT.java failed to compile. View student's code." | tee -a Output.txt
			compiled=false
		  else
			echo "LoopADT.java compiled successfully." | tee -a Output.txt
		  fi
		  echo " " | tee -a Output.txt
		  echo "============= Compiling EmptyLoopException.java ===============" | tee -a Output.txt
		  javac EmptyLoopException.java | tee -a Output.txt
		  if [ ! -f EmptyLoopException.class ]; then
			echo "EmptyLoopException.java failed to compile. View student's code." | tee -a Output.txt
			compiled=false
		  else
			echo "EmptyLoopException.java compiled successfully." | tee -a Output.txt
		  fi
		  echo " " | tee -a Output.txt
		  echo "============= Compiling DotMatrix.java ===============" | tee -a Output.txt
		  javac DotMatrix.java | tee -a Output.txt
		  if [ ! -f DotMatrix.class ]; then
			echo "DotMatrix.java failed to compile. View student's code." | tee -a Output.txt
			compiled=false
		  else
			echo "DotMatrix.java compiled successfully." | tee -a Output.txt
		  fi
		  echo " " | tee -a Output.txt
		  echo "============= Compiling DblListnode.java ===============" | tee -a Output.txt
		  javac DblListnode.java | tee -a Output.txt
		  if [ ! -f DblListnode.class ]; then
			echo "DblListnode.java failed to compile. View student's code." | tee -a Output.txt
			compiled=false
		  else
			echo "DblListnode.java compiled successfully." | tee -a Output.txt
		  fi
		  echo " " | tee -a Output.txt
		  echo "============= Compiling UnrecognizedCharacterException.java ===============" | tee -a Output.txt
		  javac UnrecognizedCharacterException.java | tee -a Output.txt
		  if [ ! -f UnrecognizedCharacterException.class ]; then
			echo "UnrecognizedCharacterException.java failed to compile. View student's code." | tee -a Output.txt
			compiled=false
		  else
			echo "UnrecognizedCharacterException.java compiled successfully." | tee -a Output.txt
		  fi
		  echo " " | tee -a Output.txt
		  echo "============= Compiling MessageLoop.java ===============" | tee -a Output.txt
		  javac MessageLoop.java | tee -a Output.txt
		  if [ ! -f MessageLoop.class ]; then
			echo "MessageLoop.java failed to compile. View student's code." | tee -a Output.txt
			compiled=false
		  else
			echo "MessageLoop.java compiled successfully." | tee -a Output.txt
		  fi
		  echo " " | tee -a Output.txt
		  echo "============= Compiling MessageLoopIterator.java ===============" | tee -a Output.txt
		  javac MessageLoopIterator.java | tee -a Output.txt
		  if [ ! -f MessageLoopIterator.class ]; then
			echo "MessageLoopIterator.java failed to compile. View student's code." | tee -a Output.txt
			compiled=false
		  else
			echo "MessageLoopIterator.java compiled successfully." | tee -a Output.txt
		  fi
		  echo " " | tee -a Output.txt
		  echo "============= Compiling DisplayEditor.java ===============" | tee -a Output.txt
		  javac DisplayEditor.java | tee -a Output.txt
		  if [ ! -f DisplayEditor.class ]; then
			echo "DisplayEditor.java failed to compile. View student's code." | tee -a Output.txt
			compiled=false
		  else
			echo "DisplayEditor.java compiled successfully." | tee -a Output.txt
		  fi
		  echo " " | tee -a Output.txt
		  echo "============= Compiling MessageLoopTester.java ===============" | tee -a Output.txt
		  javac MessageLoopTester.java | tee -a Output.txt
		  if [ ! -f MessageLoopTester.class ]; then
			echo "MessageLoopTester.java failed to compile. This may be because the student's DisplayEditor class is missing methods; view their code." | tee -a Output.txt
			compiled=false
		  else
			echo "MessageLoopTester.java compiled successfully." | tee -a Output.txt
		  fi
		  echo " " | tee -a Output.txt
		  if $compiled ; then
		    echo "This student's files compiled successfully, running tests . . ."
		    echo "================== Running Tests ========================" | tee -a Output.txt
		    if [[ -f studentOutput.txt ]]; then
			rm -rf studentOutput.txt
		    fi
		    /p/course/cs367-skrentny/public/html/assignments/p2/grading/timeout.sh -t 40 java MessageLoopTester test.txt alphabets.txt > studentOutput.txt
		    # testOutput.txt is the test script output located in /p/course/cs367-skrentny/public/html/assignments/p2/grading/files 
		    if  ! diff -aq "./studentOutput.txt" "./testOutput.txt" ; then
			echo $1 | tee -a Output.txt
			echo "There is some difference in student's output. Need to check!" | tee -a Output.txt
		    fi
		  fi
	  fi
	  echo $1" complete."
	  cd ..	
else
	if [[ -d cs367_grading_p2 ]]; then
		rm -rf cs367_grading_p2
	fi
	mkdir cs367_grading_p2
	cd /p/course/cs367-skrentny/handin
	#cd /p/course/cs367-skrentny/public/html/assignments/p2/solution
	for file in *; do
	   if [ -d $file ]; then
		  mkdir ~/cs367_grading_p2/$file
		  cp $file/p2/* ~/cs367_grading_p2/$file
	   fi
	done

	cd ~/cs367_grading_p2
	cp /p/course/cs367-skrentny/public/html/assignments/p2/grading/files/* .

	nocompile=0
	
	for file in *; do
	   if [ -d $file ]; then		  
		  echo "Running tests for "$file" . . ."
		  cd $file		  
		  found_all_files=true
		  if [ -f LoopADT.java ]; then
			echo "LoopADT.java submitted unneccessarily. Overwriting it now." | tee -a Output.txt
		  fi
		  cp /p/course/cs367-skrentny/public/html/assignments/p2/grading/files/LoopADT.java .

		  if [ ! -f EmptyLoopException.java ]; then
			echo "Could not find EmptyLoopException.java." | tee -a Output.txt
			found_all_files=false
		  fi
		  if [ ! -f DblListnode.java ]; then
			echo "Could not find DblListnode.java." | tee -a Output.txt
			found_all_files=false
		  fi
		  if [ -f DotMatrix.java ]; then
			echo "DotMatrix.java submitted unneccessarily. Overwriting it now." | tee -a Output.txt
		  fi
		  cp /p/course/cs367-skrentny/public/html/assignments/p2/grading/files/DotMatrix.java .

		  if [ ! -f UnrecognizedCharacterException.java ]; then
			echo "Could not find UnrecognizedCharacterException.java." | tee -a Output.txt
			found_all_files=false
		  fi
		  if [ ! -f MessageLoop.java ]; then
			echo "Could not find MessageLoop.java." | tee -a Output.txt
			found_all_files=false
		  fi
		  if [ ! -f MessageLoopIterator.java ]; then
			echo "Could not find MessageLoopIterator.java." | tee -a Output.txt
			found_all_files=false
		  fi
		  if [ ! -f DisplayEditor.java ]; then
			echo "Could not find DisplayEditor.java." | tee -a Output.txt
			found_all_files=false
		  fi
		  found_readme=false
		  if [ -f README.txt ]; then
			found_readme=true
		  fi
		  if [ ! $found_all_files ] &&  [ ! $found_readme ]; then
			echo "Could not find a README either. This student either named some files incorrectly or did not submit everything they needed to." | tee -a Output.txt
			echo "Can't find the required files for this student. Check their folder."
		  fi
		  
		  if $found_readme ; then
			echo "Found a README. This student worked with a partner, view README." | tee -a Output.txt
			#echo "This student worked with a partner. View their README file."
		  fi
		  
		  
		  if $found_all_files ; then
			  compiled=true
			  echo "Running tests... " | tee -a Output.txt
			  echo " " | tee -a Output.txt
			  # All the solution file names should be different from the names of the student's files. This directory also has MessageLoopTester.java
			  cp /p/course/cs367-skrentny/public/html/assignments/p2/grading/files/MessageLoopTester.java .	
			  cp /p/course/cs367-skrentny/public/html/assignments/p2/grading/files/*.txt .	
		  
			  echo "============== Compiling LoopADT.java ===============" | tee -a Output.txt
			  javac LoopADT.java | tee -a Output.txt
			  if [ ! -f LoopADT.class ] ; then
				echo "LoopADT.java failed to compile. View student's code." | tee -a Output.txt
				compiled=false
			  else
				echo "LoopADT.java compiled successfully." | tee -a Output.txt
			  fi
			  echo " " | tee -a Output.txt
			  echo "============= Compiling EmptyLoopException.java ===============" | tee -a Output.txt
			  javac EmptyLoopException.java | tee -a Output.txt
			  if [ ! -f EmptyLoopException.class ]; then
				echo "EmptyLoopException.java failed to compile. View student's code." | tee -a Output.txt
				compiled=false
			  else
				echo "EmptyLoopException.java compiled successfully." | tee -a Output.txt
			  fi
			  echo " " | tee -a Output.txt
			  echo "============= Compiling DotMatrix.java ===============" | tee -a Output.txt
			  javac DotMatrix.java | tee -a Output.txt
			  if [ ! -f DotMatrix.class ]; then
				echo "DotMatrix.java failed to compile. View student's code." | tee -a Output.txt
				compiled=false
			  else
				echo "DotMatrix.java compiled successfully." | tee -a Output.txt
			  fi
			  echo " " | tee -a Output.txt
			  echo "============= Compiling DblListnode.java ===============" | tee -a Output.txt
			  javac DblListnode.java | tee -a Output.txt
			  if [ ! -f DblListnode.class ]; then
				echo "DblListnode.java failed to compile. View student's code." | tee -a Output.txt
				compiled=false
			  else
				echo "DblListnode.java compiled successfully." | tee -a Output.txt
			  fi
			  echo " " | tee -a Output.txt
			  echo "============= Compiling UnrecognizedCharacterException.java ===============" | tee -a Output.txt
			  javac UnrecognizedCharacterException.java | tee -a Output.txt
			  if [ ! -f UnrecognizedCharacterException.class ]; then
				echo "UnrecognizedCharacterException.java failed to compile. View student's code." | tee -a Output.txt
				compiled=false
			  else
				echo "UnrecognizedCharacterException.java compiled successfully." | tee -a Output.txt
			  fi
			  echo " " | tee -a Output.txt
			  echo "============= Compiling MessageLoop.java ===============" | tee -a Output.txt
			  javac MessageLoop.java | tee -a Output.txt
			  if [ ! -f MessageLoop.class ]; then
				echo "MessageLoop.java failed to compile. View student's code." | tee -a Output.txt
				compiled=false
			  else
				echo "MessageLoop.java compiled successfully." | tee -a Output.txt
			  fi
			  echo " " | tee -a Output.txt
			  echo "============= Compiling MessageLoopIterator.java ===============" | tee -a Output.txt
			  javac MessageLoopIterator.java | tee -a Output.txt
			  if [ ! -f MessageLoopIterator.class ]; then
				echo "MessageLoopIterator.java failed to compile. View student's code." | tee -a Output.txt
				compiled=false
			  else
				echo "MessageLoopIterator.java compiled successfully." | tee -a Output.txt
			  fi
			  echo " " | tee -a Output.txt
			  echo "============= Compiling DisplayEditor.java ===============" | tee -a Output.txt
			  javac DisplayEditor.java | tee -a Output.txt
			  if [ ! -f DisplayEditor.class ]; then
				echo "DisplayEditor.java failed to compile. View student's code." | tee -a Output.txt
				compiled=false
			  else
				echo "DisplayEditor.java compiled successfully." | tee -a Output.txt
			  fi
			  echo " " | tee -a Output.txt
			  echo "============= Compiling MessageLoopTester.java ===============" | tee -a Output.txt
			  javac MessageLoopTester.java | tee -a Output.txt
			  if [ ! -f MessageLoopTester.class ]; then
				echo "MessageLoopTester.java failed to compile. This may be because the student's DisplayEditor class is missing methods; view their code." | tee -a Output.txt
				compiled=false
			  else
				echo "MessageLoopTester.java compiled successfully." | tee -a Output.txt
			  fi
			  echo " " | tee -a Output.txt
			  if $compiled ; then
				echo "This student's files compiled successfully, running tests . . ."
				echo "================== Running Tests ========================" | tee -a Output.txt
				if [[ -f studentOutput.txt ]]; then
					rm -rf studentOutput.txt
				fi
				/p/course/cs367-skrentny/public/html/assignments/p2/grading/timeout.sh -t 40 java MessageLoopTester test.txt alphabets.txt > studentOutput.txt
				# testOutput.txt is the test script output located in /p/course/cs367-skrentny/public/html/assignments/p2/grading/files 				
				if  ! diff -aq "./studentOutput.txt" "./testOutput.txt" ; then
				    echo $file | tee -a Output.txt
				    echo "There is some difference in student's output. Need to check!" | tee -a Output.txt
				fi
			  else
			    notCompiled[$nocompile]=$file
				nocompile=$nocompile+1
			  fi
		  fi
		  echo $file" complete."
		  cd ..
		fi
	done
	
	echo "Students whose code did not compile:" >> DidNotCompile.txt
	echo ${notCompiled[@]} >> DidNotCompile.txt
	echo " " >> DidNotCompile.txt
fi

cd ~
exit
