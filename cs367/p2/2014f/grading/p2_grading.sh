#!/bin/sh

handinDir="/p/course/cs367-skrentny/handin"
assignmtDir="/p/course/cs367-skrentny/public/html/assignments/p2"
gradingDir="$HOME/private/cs367_p2"
outputFile="ScriptOutput.txt"
summaryFile="ScriptSummary.txt"

noSubmit=0
noCompile=0
partners=0

################################################################################
copy_students_files () {
    echo "-----------> copying files"
    if [ -d $gradingDir ]; then
       rm -rf  $gradingDir
    fi

    mkdir $gradingDir

    cd $handinDir

    for file in *; do
        if [[ "$file" == "$1" || "$file" == "$2" || "$file" > "$1" && "$file" < "$2" ]] ; then
            if [ -d $file ]; then
                mkdir $gradingDir/$file
                if [ $(ls -1A $file/p2 | wc -l) -eq 0 ] ; then
                    studentsNoSubmit[$noSubmit]=$file
                    noSubmit=$noSubmit+1
                else
                    cp $file/p2/* $gradingDir/$file
                fi
            fi
        fi
    done
}

################################################################################
check_student_files () {
    local found_all_files=true

    echo "======================           Checking Files            =====================" >> $outputFile
    echo " " >> $outputFile
    if [ ! -f SimpleLinkedList.java ]; then
        echo "Could not find SimpleLinkedList.java." >> $outputFile
        found_all_files=false
    else
        echo "$1 submitted SimpleLinkedList.java at: "$(stat -c %y $handinDir/$1/p2/SimpleLinkedList.java) >> $outputFile
    fi

    if [ ! -f Tweet.java ]; then
        echo "Could not find Tweet.java." >> $outputFile
        found_all_files=false
    else
        echo "$1 submitted Tweet.java at: "$(stat -c %y $handinDir/$1/p2/Tweet.java) >> $outputFile
    fi

    if [ ! -f Timeline.java ]; then
        echo "Could not find Timeline.java." >> $outputFile
        found_all_files=false
    else
        echo "$1 submitted Timeline.java at: "$(stat -c %y $handinDir/$1/p2/Timeline.java) >> $outputFile
    fi

    if [ ! -f TweetTooLongException.java ]; then
        echo "Could not find TweetTooLongException.java." >> $outputFile
        found_all_files=false
    else
        echo "$1 submitted TweetTooLongException.java at: "$(stat -c %y $handinDir/$1/p2/TweetTooLongException.java) >> $outputFile
    fi

    if [ ! -f Timeline.java ]; then
        echo "Could not find Timeline.java." >> $outputFile
        found_all_files=false
    else
        echo "$1 submitted Timeline.java at: "$(stat -c %y $handinDir/$1/p2/Timeline.java) >> $outputFile
    fi

    local found_readme=false

    if [[ -f README.txt || -f ReadMe.txt || -f Readme.txt || -f readme.txt || -f README || -f ReadMe || -f Readme || -f readme ]] ; then
        found_readme=true
    fi

    echo " " >> $outputFile
    if $found_all_files ; then
        echo "FOUND all required java files." >> $outputFile
        return 0
    fi

    if $found_readme ; then
        echo "FOUND only a README. This student had a partner. View their README file." >> $outputFile
        studentsWithPartner[$partners]=$1
        partners=$partners+1
        return 1
    fi

    echo "PROBLEM: Can't find the required files for this student. Check their folder for incorrect names." >> $outputFile
    return 1
}

################################################################################
compile_student_files () {
    local compiled=true

# -n is no clobber option to prevent overwriting submitted files
#    cp -n $assignmtDir/grading/files/* .
    cp $assignmtDir/grading/files/* .

    echo " " >> $outputFile
    echo "======================           Compiling Files           =====================" >> $outputFile
    echo " " >> $outputFile
    echo "-> compiling DblListnode.java" >> $outputFile
    javac DblListnode.java >> $outputFile
    if [ ! -f DblListnode.class ] ; then
        echo "DblListnode.java failed to compile. The student should not have modified this file, check their code." >> $outputFile
        compiled=false
    else
        echo "DblListnode.java compiled successfully." >> $outputFile
    fi

    echo " " >> $outputFile
    echo "-> compiling ListADT.java" >> $outputFile
    javac ListADT.java >> $outputFile
    if [ ! -f ListADT.class ] ; then
        echo "ListADT.java failed to compile. The student should not have modified this file, check their code." >> $outputFile
        compiled=false
    else
        echo "ListADT.java compiled successfully." >> $outputFile
    fi


    echo " " >> $outputFile
    echo "-> compiling SimpleLinkedList.java" >> $outputFile
    javac SimpleLinkedList.java >> $outputFile
    if [ ! -f SimpleLinkedList.class ] ; then
        echo "SimpleLinkedList.java failed to compile. Please view student's code." >> $outputFile
        compiled=false
    else
        echo "SimpleLinkedList.java compiled successfully." >> $outputFile
    fi

    echo " " >> $outputFile
    echo "-> compiling Tweet.java" >> $outputFile
    javac Tweet.java >> $outputFile
    if [ ! -f Tweet.class ] ; then
        echo "Tweet.java failed to compile. Please view student's code." >> $outputFile
        compiled=false
    else
        echo "Tweet.java compiled successfully." >> $outputFile
    fi

    echo " " >> $outputFile
    echo "-> TweetTooLongExceptiont.java" >> $outputFile
    javac TweetTooLongException.java >> $outputFile
    if [ ! -f TweetTooLongException.class ] ; then
        echo "TweetTooLongExceptioin.java failed to compile. Please view student's code." >> $outputFile
        compiled=false
    else
        echo "TweetTooLongException.java compiled successfully." >> $outputFile
    fi

    echo " " >> $outputFile
    echo "-> compiling Timeline.java" >> $outputFile
    javac Timeline.java >> $outputFile
    if [ ! -f Timeline.class ] ; then
        echo "Timeline.java failed to compile. Please view student's code." >> $outputFile
        compiled=false
    else
        echo "Timeline.java compiled successfully." >> $outputFile
    fi

    echo " " >> $outputFile
    echo "-> compiling Twitter.java" >> $outputFile
    javac Twitter.java >> $outputFile
    if [ ! -f Twitter.class ] ; then
        echo "Twitter.java failed to compile. Please view student's code." >> $outputFile
        compiled=false
    else
        echo "Twitter.java compiled successfully." >> $outputFile
    fi

    echo " " >> $outputFile
    echo "-> compiling ListTester.java" >> $outputFile
    javac ListTester.java >> $outputFile
    if [ ! -f ListTester.class ]  ; then
        echo "ListTester.java failed to compile." >> $outputFile
        echo "This may be because the student is missing methods in their SimpleLinkedList class. Please view student's code." >> $outputFile
        compiled=false
    else
        echo "ListTester.java compiled successfully." >> $outputFile
    fi


    echo " " >> $outputFile
    echo "-> compiling TwitterTester.java" >> $outputFile
    javac TwitterTester.java >> $outputFile
    if [ ! -f TwitterTester.class ]  ; then
        echo "TwitterTester.java failed to compile." >> $outputFile
        echo "Please view student's Twitter.java code." >> $outputFile
        compiled=false
    else
        echo "TwitterTester.java compiled successfully." >> $outputFile
    fi

    if $compiled ; then
        echo " " >> $outputFile
        echo "COMPILED all required java files." >> $outputFile
        return 0
    fi

    studentsNoCompile[$noCompile]=$1 #$file
    noCompile=$noCompile+1
    echo " " >> $outputFile
    echo "PROBLEM: Can't compile all files. Check the appropriate source file." >> $outputFile
    return 1
}


################################################################################
grade_student () {

    echo " " >> $outputFile
    echo "======================        Testing SimpleLinkedList       =====================" >> $outputFile
    echo " " >> $outputFile
    java ListTester >> $outputFile
    echo " " >> $outputFile
    echo "TESTED SimpleLinkedList." >> $outputFile


    echo " " >> $outputFile
    echo "======================     Testing Twitter Execution     =====================" >> $outputFile
    t1="Zach.txt Roney.txt Chris.txt"
    stOut="StudentOutput.txt"
    java TwitterTester $t1 > $stOut
    echo " " >> $outputFile
    echo "------------------------------------------------------------------" >> $outputFile
    echo "-> diff of student's output and correct output for t1:" >> $outputFile
    echo " " >> $outputFile
    diff $stOut "Output.txt" >> $outputFile

    cp MainInput2.txt MainInput.txt
    stOut="StudentOutput2.txt"
    java TwitterTester $t1 > $stOut
    echo " " >> $outputFile
    echo "------------------------------------------------------------------" >> $outputFile
    echo "-> diff of student's output and correct output for t2:" >> $outputFile
    echo " " >> $outputFile
    diff $stOut "Output2.txt" >> $outputFile
    echo " " >> $outputFile
    echo "TESTED Twitter Execution." >> $outputFile
}

################################################################################
#################
# invalid args? #
#################
if [[ $# -eq 0 || $# -gt 2 ]] ; then
    echo ""
    echo "usage: $0 start_login end_login (grade allocation)" >&2
    echo "usage: $0 login (regrade one student)" >&2
    echo ""
    exit 1

####################
# grade allocation #
####################
elif [ $# -eq 2 ] ; then

    echo ""
    echo "********************************************************************************"
    echo "* PROCESSING allocation from $1 to $2"
    echo "********************************************************************************"
    echo ""

    badArgs=false
    if [ ! -d "$handinDir/$1" ] ; then
        echo "$1 is not a valid login." >&2
        badArgs=true
    fi
    if [ ! -d "$handinDir/$2" ] ; then
        echo "$2 is not a valid login." >&2
        badArgs=true
    fi
    if [[ "$1" > "$2" ]] ; then
        echo "$1 doesn't come before $2." >&2
        echo "usage: $0 start_login end_login (grade allocation)" >&2
        badArgs=true
    fi

    if $badArgs ; then
        echo " " >&2
        exit 1
    fi

    copy_students_files $1 $2

    cd $gradingDir

    for student in *; do
        if [ -d $student ]; then
            echo " "
            echo "-----------> $student started"
            cd $student
            if check_student_files "$student" && compile_student_files "$student" ; then
                grade_student $student
            fi
            echo "-----------> $student completed"
            cd .. 
        fi
    done

    echo "Students who didn't submit anything:" >> $summaryFile
    echo ${studentsNoSubmit[@]} >> $summaryFile
    echo " " >> $summaryFile

    echo "Students whose code couldn't be found or didn't compile:" >> $summaryFile
    echo ${studentsNoCompile[@]} >> $summaryFile
    echo " " >> $summaryFile

    echo "Students who submitted only a README file (check README for partner info):" >> $summaryFile
    echo ${studentsWithPartner[@]} >> $summaryFile

###################
# regrade student #
###################
elif [ $# -eq 1 ] ; then

    echo ""
    echo "********************************************************************************"
    echo "* REGRADING $1"
    echo "********************************************************************************"
    echo ""

    if [ ! -d "$gradingDir" ] ; then
        echo "You must first run $0 script on your allocation." >&2
        echo "usage: $0 start_login end_login (grade allocation)" >&2
        echo " " >&2
        exit
    fi
    if [ ! -d "$gradingDir/$1" ] ; then
        echo $1 "is not a valid login for your allocation." >&2
        echo " " >&2
        exit
    fi

    echo "$1 start"
    cd $gradingDir/$1
    if check_student_files "$1" && compile_student_files "$1" ; then
       grade_student $1
    fi
    echo "$1 complete"
fi

exit 0
