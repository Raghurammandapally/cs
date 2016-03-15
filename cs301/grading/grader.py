import sys
import os
import datetime
import shutil
import subprocess
import time
import pexpect
import time
from zipfile import ZipFile
import shutil

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
        with ZipFile(zip_file, 'r') as myzip:
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
        # expand zip file into grading dir if we just created it
        else:
            unzip_to_grading_dir(sys.argv[1])
    make_output_dir()
    out_files = copy_files_to_output(find_unique_files())
    grade_students(out_files)

"""

def new_child(filename):
    return pexpect.spawn("python2.7", ["-u", filename], timeout=0.1)

tmp_name = "%s/%s" % (res, "tmp")
print "\n"
print "Checking student work..."
with open("%s/results.txt" % res, 'w') as outfile:

    for f in files:
        total = 10
        denom = total
        print "...checking %s" % f[1]
        t0 = time.time()
        filename = filefmt % (res, f[1])


        # Welcome message not displayed 
        welcome_message = "Welcome to the container inventory program!"

        child = new_child(filename)
        try:
            ret = child.expect_exact(welcome_message)
        except:
            outfile.write(" [-1] Welcome message not displayed (or not identical to the welcome message given in the problem description)\n")
            total -= 1
        child.terminate()


        #  Program commands do not match specified commands (add/printV/printSA/printNum/quit) 
        commands = "What would you like to do? (add, printV, printSA, printNum, quit):"
        child = new_child(filename)
        try:
            ret = child.expect_exact(commands)
        except:
            outfile.write(" [-1] Program commands (\"What would you like to do?\") not displayed (or not identical to options given in the problem description)\n")
            total -= 1
        child.terminate()


        # Program shapes do not match specified shapes (sphere/rect/cone/cylinder) 
        shapes = "What shape is the container? (sphere, rect, cone, cylinder)"
        child = new_child(filename)
        try:
            child.sendline("add")
            ret = child.expect_exact(shapes)
        except:
            outfile.write(" [-1] Shape prompt (\"What shape is the container?\") not displayed (or not identical to options given in the problem description)\n)")
            total -= 1
        child.terminate()

        # Shape dimension prompts do not match those specified 
        shape_prompts = [["cone", ["Radius (m):", "Height (m):"]],
                         ["rect", ["Length (m):", "Width (m):", "Height (m):"]],
                         ["sphere", ["Radius (m):"]],
                         ["cylinder", ["Radius (m):", "Height (m):"]]
        ]
        
        for s in shape_prompts:
            s_name = s[0]
            s_prompts = s[1]
            child = new_child(filename)
            try:
                child.sendline("add")
                child.sendline(s_name)
                for p in s_prompts:
                    ret = child.expect_exact(p)
                    child.sendline("1.0")
            except:
                outfile.write(" [-1] Shape dimension prompts do not match those specified.\n")
                total -= 1
                break
        child.terminate()

        # Program does not correctly calculate volumes as given in first Example Usage run 
        shape_dims = [["rect", [0.5, 0.5, 1.0]],
                     ["sphere", [2]],
                     ["rect", [3.5, 9.2222, 11]],
                     ["cylinder", [9,9.2]]
                 ]

        # account for rounding/imprecision
        exp = [["sphere","33.51"],
               ["rectangular prism", "355.30"],
               ["cone", "0"],
               ["cylinder", "2341.11"]
           ]
        
        child = new_child(filename)

        try:
            for s in shape_dims:
                s_name = s[0]
                s_inputs= s[1]

                child.sendline("add")
                child.sendline(s_name)
                for i in s_inputs:
                    child.sendline(str(i))
            child.sendline("printV")

            for e in exp:
                e_name = e[0]
                e_exp = str(e[1])
                child.expect_exact(e_exp)
        except:
            outfile.write(" [-1] printV does not match expected output (including the order) given in first Example Usage run.\n")
            total -= 1
        child.terminate()

        # Program does not correctly calculate surface areas as given in first Example Usage run 
        exp = [["sphere","50.26"],
               ["rectangular prism", "346.94"],
               ["cone", "0"],
               ["cylinder", "1029.18"]
           ]
        
        child = new_child(filename)

        try:
            for s in shape_dims:
                s_name = s[0]
                s_inputs= s[1]
                
                child.sendline("add")
                child.sendline(s_name)
                for i in s_inputs:
                    child.sendline(str(i))
            child.sendline("printSA")

            for e in exp:
                e_name = e[0]
                e_exp = str(e[1])
                child.expect_exact(e_exp)
        except:
            outfile.write(" [-1] printSA does not match expected output (including the order) given in first Example Usage run.\n")
            total -= 1
        child.terminate()

        # Program does not correctly track object counts as given in first Example Usage run 
        exp = [["sphere","1"],
               ["rectangular prism", "2"],
               ["cone", "0"],
               ["cylinder", "1"]
           ]

        nums = "Object counts:"

        child = new_child(filename)

        try:
            for s in shape_dims:
                s_name = s[0]
                s_inputs = s[1]

                child.sendline("add")
                child.sendline(s_name)
                for i in s_inputs:
                    child.sendline(str(i))
            child.sendline("printNum")

            child.expect_exact(nums)
            for e in exp:
                e_name = e[0]
                e_exp = str(e[1])
                child.expect_exact(e_exp)
        except:
            outfile.write(" [-1] printNum does not match expected output (including the order) given in first Example Usage run.\n")
            total -= 1
        child.terminate()

        # Program does not correctly allow additional containers to be added after printing cumulative values 
        child = new_child(filename)
        s = shape_dims[0]
        try:
            child.sendline("add")
            child.expect_exact("shape")
            child.sendline(s[0])
            for i in s[1]:
                child.sendline(str(i))
            child.sendline("printV")
            child.sendline("add")
            child.expect_exact("shape")
        except:
            outfile.write(" [-1] Does not correctly allow additional containers to be added after printV\n")
            total -= 1
        child.terminate()

        # Quit command does not end program with parting message 
        child = new_child(filename)
        try:
            child.sendline("quit")
            child.expect_exact("Goodbye!")
        except:
            outfile.write(" [-1] Does not accept \"quit\" command correctly, or does not display exit message \"Goodbye!\"\n")
            total -= 1
        child.terminate()

        # No comments
        try:
            # probably should be using this: "^.*#.*$"
            n_hash_comments = subprocess.check_output(["grep", "-e", "^\s*#", "-c", filename])
        except:
            n_hash_comments = 0
            
        try:
            n_quote_comments = subprocess.check_output(["grep", "-e", "^\s*\"\"\"", "-c", filename])
        except:
            n_quote_comments = 0

        if n_hash_comments == 0 and n_quote_comments == 0:
            outfile.write(" [-1] No comments\n")
            total -= 1
        
            
        if(total == denom):
            outfile.write("Nice job!\n")
        outfile.write("\nScore: %s/%s\n\n" % (total, denom))

"""
if __name__ == "__main__":
    do_grading()

