import sys
import os
import datetime
import shutil
import subprocess
import time
import time
from zipfile import ZipFile
import shutil
import contextlib

# relative path to student files from here
grading_dir = "input_files"
res = "results"
output = "results.out"
fname = "gameBoard.py"
grader_name = "Keith Funkhouser"
grader_login = "wfunkhouser"

# returns True if it created a new dir, false otherwise
def make_grading_dir():
    if grading_dir not in os.listdir(os.getcwd()):
        print "* Creating new directory for files..."
        os.mkdir(grading_dir)
        return True
    else:
        return False

# returns a list of filenames to be extracted from the .zip file
def unzip_to_grading_dir(zip_file):
    try:
        with contextlib.closing(ZipFile(zip_file , "r")) as myzip:
            myzip.extractall(grading_dir)
    except Exception as e:
        print "CAUGHT EXCEPTION EXTRACTING ZIP FILE. EXITING..."
        print "************************************************"
        print e
        exit(1)

def find_unique_files():
    files = os.listdir(grading_dir)
    
    sep = " - "

    # selects only files which are correctly named
    #filenames = [f for f in files if f.lower().endswith(fname.lower())]

    # selects all .py files
    filenames = [f for f in files if f.lower().endswith(".py")]
    files = [f.split(sep) for f in filenames]
    for i in range(0, len(files)):
        files[i].append(filenames[i])

    # Feb 11, 2016 205 AM
    fmt = "%b %d, %Y %I%M %p"
    for f in files:
        # check filenames
        if len(f) != 5:
            print "Invalid file: %s" % sep.join(f)
            exit(1)
        # parse filename dates
        try:
            d = datetime.datetime.strptime(f[2], fmt)
        except ValueError:
            print "Unexpected date formate in %s" % sep.join(f)
            exit(1)
        f[2] = d

    # want to sort on name first (ascending), then date (descending)
    # need to sort twice in order to achieve this
    # http://stackoverflow.com/questions/4233476/sort-a-list-by-multiple-attributes
    files = sorted(files, key = lambda x: x[2], reverse=True)
    files = sorted(files, key = lambda x: x[1].lower())
    
    # find unique student files
    s = set()
    filedup = files[:]
    for f in filedup:
        if f[1] not in s:
            s.add(f[1])
        else:
            files.remove(f)
            
    return files

def make_output_dir():
    try:
        os.mkdir(res)
    except OSError:
        print "%s/ subdir already exists. Attempting to remove." % res
        try:
            shutil.rmtree(res)
            os.mkdir(res)
        except Exception as e:
            print "CAUGHT EXCEPTION REMOVING OUTPUT DIR. EXITING..."
            print "************************************************"
            print e
            exit(1)

# copies files to "res" dir, exiting if an error is caught. returns list of filenames in "res"
def copy_files_to_output(files):
    # copy file to results/ dir
    print "Copying files..."
    for f in files:
        try:
            shutil.copyfile("%s/%s" % (grading_dir, f[4]), "%s/%s" % (res, f[4]))
            print "...copied %s" % f[1]
        except Exception as e:
            print "CAUGHT EXCEPTION COPYING STUDENT FILE. EXITING..."
            print "************************************************"
            print e
            exit(1)
    return files

def grade_student(f, outfile):
    outfile.write("***************************************\n")
    outfile.write("Tests for %s:\n" % f[1])
    outfile.write("***************************************\n")
    if f[3] != fname:
        outfile.write("---Please name your file according to the specification. For this project, it should have been named %s.---\n\n" % fname)
    outfile.write("Grader:\t\t%s\n" % grader_name)
    outfile.write("Grader login:\t%s\n" % grader_login)
    outfile.write("\n")
    outfile.write("Grading the most recent file:\n")
    outfile.write("\tFilename: %s\n\tSubmitted: %s\n" % (f[3], f[2].strftime("%c")))
    outfile.write("If this is incorrect, please let me know via email for a regrade.\n\n")
        
    outfile.write("------------------------ Results: ---------------------------\n")
    shutil.copyfile( f[4], "%s/%s" % ("tmp", fname))
    os.chdir("tmp")

    try:
        import gameBoard
    except ImportError as e:
        outfile.write( "Failed to import gameBoard.py!\n")
        outfile.write(str(e) + "\n")

    os.chdir("..")
    

def grade_students(files):
    os.chdir(res)
    os.mkdir("tmp")
    with open(output, "w") as outfile:
        for f in files:
            grade_student(f, outfile)
    os.chdir("..")

def do_grading():
    # check if grading dir exists, create if necessary
    if(make_grading_dir()):
        if len(sys.argv) < 2:
            print "Expecting name of zip file to extract in argument 1. Exiting..."
            os.rmdir(grading_dir)
            exit(1)
        # expand zip file into grading dir if we just created it
        else:
            unzip_to_grading_dir(sys.argv[1])
    make_output_dir()
    out_files = copy_files_to_output(find_unique_files())
    grade_students(out_files)

if __name__ == "__main__":
    do_grading()

