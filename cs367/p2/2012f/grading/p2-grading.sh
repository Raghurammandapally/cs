#!/bin/sh

cd ~

if [ ! -z $1 ] ; then
echo "Running tests for "$1" . . ."
      cd ~/cs367_p2/$1
	  found_all_files=true
	  if [ ! -f LinkedList.java ]; then
		echo "Could not find LinkedList.java." >> Output.txt
		found_all_files=false
	  else
	    echo $1" turned in LinkedList.java at: "$(stat -c %y /p/course/cs367-skrentny/handin/$1/p2/LinkedList.java) >> Output.txt
	  fi
	  if [ ! -f LinkedListIterator.java ]; then
		echo "Could not find LinkedListIterator.java." >> Output.txt
		found_all_files=false
	  else
	    echo $1" turned in LinkedListIterator.java at: "$(stat -c %y /p/course/cs367-skrentny/handin/$1/p2/LinkedListIterator.java) >> Output.txt
	  fi
	  if [ ! -f InvalidListPositionException.java ]; then
		echo "Could not find InvalidListPositionException.java." >> Output.txt
		found_all_files=false
	  else
	    echo $1" turned in InvalidListPositionException.java at: "$(stat -c %y /p/course/cs367-skrentny/handin/$1/p2/InvalidListPositionException.java) >> Output.txt
	  fi
	  if [ ! -f RobotLogMain.java ]; then
		echo "Could not find RobotLogMain.java." >> Output.txt
		found_all_files=false
	  else
	    echo $1" turned in RobotLogMain.java at: "$(stat -c %y /p/course/cs367-skrentny/handin/$1/p2/RobotLogMain.java) >> Output.txt
	  fi
	  found_readme=false
	  if [ -f README.txt ]; then
		found_readme=true
	  fi
	  if [ ! $found_all_files ] &&  [ ! $found_readme ]; then
	    echo "Could not find a README either. This student either named all files incorrectly or did not submit anything." >> Output.txt
		echo "Can't find the required files for this student. Check their folder."
		studentsNocompile[$nocompile]=$1
		nocompile=$nocompile+1
	  fi
	  
	  if $found_readme ; then
	    echo "Found a README. This student worked with a partner, view README." >> Output.txt
	    echo "This student worked with a partner. View their README file."
		studentsPartner[$partner]=$1
		partner=$partner+1
	  fi
	  
	  
	  if $found_all_files ; then
		  compiled=true
	      echo "Running tests... " >> Output.txt
		  echo " " >> Output.txt
		  cp /p/course/cs367-skrentny/public/html/assignments/p2/grading/solution/* .
		  echo "================= Compiling InvalidListPositionException.java =================" >> Output.txt
		  javac InvalidListPositionException.java >> Output.txt
		  if [ ! -f InvalidListPositionException.class ] ; then
			echo "InvalidListPositionException.java failed to compile. Please view student's code." >> Output.txt
			compiled=false
		  else
		    echo "InvalidListPositionException.java compiled successfully." >> Output.txt
		  fi
		  echo " " >> Output.txt
		  echo "================= Compiling ListNode.java =================" >> Output.txt
		  javac ListNode.java >> Output.txt
		  if [ ! -f ListNode.class ] ; then
			echo "ListNode.java failed to compile. The student should not have modified this file, check their code." >> Output.txt
			compiled=false
		  else
		    echo "ListNode.java compiled successfully." >> Output.txt
		  fi
		  echo " " >> Output.txt
		  echo "================= Compiling LinkedListADT.java =================" >> Output.txt
		  javac LinkedListADT.java >> Output.txt
		  if [ ! -f LinkedListADT.class ] ; then
			echo "LinkedListADT.java failed to compile. The student should not have modified this file, check their code. This sometimes happens because the student did not create a InvalidListPositionException class, or their InvalidListPositionException class does not extend Exception." >> Output.txt
			compiled=false
		  else
		    echo "LinkedListADT.java compiled successfully." >> Output.txt
		  fi
		  echo " " >> Output.txt
		  echo "================= Compiling LinkedList.java =================" >> Output.txt
		  javac LinkedList.java >> Output.txt
		  if [ ! -f LinkedList.class ] ; then
			echo "LinkedList.java failed to compile. Please view student's code." >> Output.txt
			compiled=false
		  else
		    echo "LinkedList.java compiled successfully." >> Output.txt
		  fi
		  echo " " >> Output.txt
		  echo "============== Compiling RobotLogMain.java ===============" >> Output.txt
		  javac RobotLogMain.java >> Output.txt
		  if [ ! -f RobotLogMain.class ] ; then
			echo "RobotLogMain.java failed to compile. Please view student's code." >> Output.txt
			compiled=false
		  else
		    echo "RobotLogMain.java compiled successfully." >> Output.txt
		  fi
		  echo " " >> Output.txt
		  echo "============= Compiling RobotLogTester.java ===============" >> Output.txt
		  javac RobotLogTester.java >> Output.txt
		  if [ ! -f RobotLogTester.class ] || [ ! -f Test.class ] ; then
		    echo "RobotLogTester.java failed to compile. This may be because the student is missing methods in their LinkedList class." >> Output.txt
			compiled=false
		  else
		    echo "RobotLogTester.java compiled successfully." >> Output.txt
		  fi
		  echo " " >> Output.txt
		  if $compiled ; then
		    echo "This student's files compiled successfully, running tests . . ."
		    echo "================== Running Tests ========================" >> Output.txt
		    /p/course/cs367-skrentny/public/html/assignments/p2/grading/timeout.sh -t 40 java RobotLogTester >> Output.txt
		  else
			studentsNocompile[$nocompile]=$1
		    nocompile=$nocompile+1
		  fi
	  fi
	  echo $1" complete."
	  cd ..	
else
	mkdir cs367_p2
	cd /p/course/cs367-skrentny/handin

	nothing=0

	for file in *; do
	   if [ -d $file ]; then
		  if [  $(ls -1A $file/p2 | wc -l) -eq 0 ] ; then
			studentsNothing[$nothing]=$file
			nothing=$nothing+1
		  else
			mkdir ~/cs367_p2/$file
			cp $file/p2/* ~/cs367_p2/$file
		  fi
	   fi
	done

	cd ~/cs367_p2

	nocompile=0
	parter=0

	for file in *; do
	   if [ -d $file ]; then
		  echo "Running tests for "$file" . . ."
		  cd ~/cs367_p2/$file
		  found_all_files=true
		  if [ ! -f LinkedList.java ]; then
			echo "Could not find LinkedList.java." >> Output.txt
			found_all_files=false
		  else
			echo $file" turned in LinkedList.java at: "$(stat -c %y /p/course/cs367-skrentny/handin/$file/p2/LinkedList.java) >> Output.txt
		  fi
		  if [ ! -f LinkedListIterator.java ]; then
			echo "Could not find LinkedListIterator.java." >> Output.txt
			found_all_files=false
		  else
			echo $file" turned in LinkedListIterator.java at: "$(stat -c %y /p/course/cs367-skrentny/handin/$file/p2/LinkedListIterator.java) >> Output.txt
		  fi
		  if [ ! -f InvalidListPositionException.java ]; then
			echo "Could not find InvalidListPositionException.java." >> Output.txt
			found_all_files=false
		  else
			echo $file" turned in InvalidListPositionException.java at: "$(stat -c %y /p/course/cs367-skrentny/handin/$file/p2/InvalidListPositionException.java) >> Output.txt
		  fi
		  if [ ! -f RobotLogMain.java ]; then
			echo "Could not find RobotLogMain.java." >> Output.txt
			found_all_files=false
		  else
			echo $file" turned in RobotLogMain.java at: "$(stat -c %y /p/course/cs367-skrentny/handin/$file/p2/RobotLogMain.java) >> Output.txt
		  fi
		  found_readme=false
		  if [ -f README.txt ]; then
			found_readme=true
		  fi
		  if [ ! $found_all_files ] &&  [ ! $found_readme ]; then
			echo "Could not find a README either. This student either named all files incorrectly or did not submit anything." >> Output.txt
			echo "Can't find the required files for this student. Check their folder."
			studentsNocompile[$nocompile]=$file
			nocompile=$nocompile+1
		  fi
		  
		  if $found_readme ; then
			echo "Found a README. This student worked with a partner, view README." >> Output.txt
			echo "This student worked with a partner. View their README file."
			studentsPartner[$partner]=$file
			partner=$partner+1
		  fi
		  
		  
		  if $found_all_files ; then
			  compiled=true
			  echo "Running tests... " >> Output.txt
			  echo " " >> Output.txt
			  cp /p/course/cs367-skrentny/public/html/assignments/p2/grading/solution/* .
			  echo "================= Compiling InvalidListPositionException.java =================" >> Output.txt
			  javac InvalidListPositionException.java >> Output.txt
			  if [ ! -f InvalidListPositionException.class ] ; then
				echo "InvalidListPositionException.java failed to compile. Please view student's code." >> Output.txt
				compiled=false
			  else
				echo "InvalidListPositionException.java compiled successfully." >> Output.txt
			  fi
			  echo " " >> Output.txt
			  echo "================= Compiling ListNode.java =================" >> Output.txt
			  javac ListNode.java >> Output.txt
			  if [ ! -f ListNode.class ] ; then
				echo "ListNode.java failed to compile. The student should not have modified this file, check their code." >> Output.txt
				compiled=false
			  else
				echo "ListNode.java compiled successfully." >> Output.txt
			  fi
			  echo " " >> Output.txt
			  echo "================= Compiling LinkedListADT.java =================" >> Output.txt
			  javac LinkedListADT.java >> Output.txt
			  if [ ! -f LinkedListADT.class ] ; then
				echo "LinkedListADT.java failed to compile. The student should not have modified this file, check their code. This sometimes happens because the student did not create a InvalidListPositionException class, or their InvalidListPositionException class does not extend Exception." >> Output.txt
				compiled=false
			  else
				echo "LinkedListADT.java compiled successfully." >> Output.txt
			  fi
			  echo " " >> Output.txt
			  echo "================= Compiling LinkedList.java =================" >> Output.txt
			  javac LinkedList.java >> Output.txt
			  if [ ! -f LinkedList.class ] ; then
				echo "LinkedList.java failed to compile. Please view student's code." >> Output.txt
				compiled=false
			  else
				echo "LinkedList.java compiled successfully." >> Output.txt
			  fi
			  echo " " >> Output.txt
			  echo "============== Compiling RobotLogMain.java ===============" >> Output.txt
			  javac RobotLogMain.java >> Output.txt
			  if [ ! -f RobotLogMain.class ] ; then
				echo "RobotLogMain.java failed to compile. Please view student's code." >> Output.txt
				compiled=false
			  else
				echo "RobotLogMain.java compiled successfully." >> Output.txt
			  fi
			  echo " " >> Output.txt
			  echo "============= Compiling RobotLogTester.java ===============" >> Output.txt
			  javac RobotLogTester.java >> Output.txt
			  if [ ! -f RobotLogTester.class ] || [ ! -f Test.class ] ; then
				echo "RobotLogTester.java failed to compile. This may be because the student is missing methods in their LinkedList class." >> Output.txt
				compiled=false
			  else
				echo "RobotLogTester.java compiled successfully." >> Output.txt
			  fi
			  echo " " >> Output.txt
			  if $compiled ; then
				echo "This student's files compiled successfully, running tests . . ."
				echo "================== Running Tests ========================" >> Output.txt
				/p/course/cs367-skrentny/public/html/assignments/p2/grading/timeout.sh -t 40 java RobotLogTester >> Output.txt
			  else
				studentsNocompile[$nocompile]=$file
				nocompile=$nocompile+1
			  fi
		  fi
		  echo $file" complete."
		  cd ..	
		fi
	done

	echo "Students who did not turn in anything (including a README):" >> Results.txt
	echo ${studentsNothing[@]} >> Results.txt
	echo "These students may have worked with a partner but forgotten the README. Check other students' READMEs." >> Results.txt
	echo " " >> Results.txt

	echo "Students whose code could not be found or did not compile:" >> Results.txt
	echo ${studentsNocompile[@]} >> Results.txt
	echo " " >> Results.txt

	echo "Students who submitted a README file (may have worked with a partner, check README):" >> Results.txt
	echo ${studentsPartner[@]} >> Results.txt
fi

cd ~
exit
