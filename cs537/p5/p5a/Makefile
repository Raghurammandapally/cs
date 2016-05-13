default:
	gcc -o fscheck -g fscheck.c
check-syntax:
	gcc -o nul -S ${CHK_SOURCES}
clean:
	rm -f fscheck
	rm -f nul
test: default
	#./fscheck corrupt/fs.img.bak
	#./fscheck corrupt/badroot2
	#./fscheck corrupt/badfmt
	#./fscheck corrupt/mismatch
	./fscheck corrupt/good
	# [x] ./fscheck corrupt/fs_badinode.img
	# [x] ./fscheck corrupt/fs_badaddr.img
	# [x] ./fscheck corrupt/fs_badroot.img
	# [x] ./fscheck corrupt/fs_badfmt.img
	# [x] ./fscheck corrupt/fs_mismatch.img
	# [x] ./fscheck corrupt/fs_mrkfree.img
	./fscheck corrupt/fs_mrkused.img
	#./fscheck corrupt/fs_imrkfree.img
	#./fscheck corrupt/fs_imrkused.img
	#./fscheck corrupt/fs_badrefcnt.img



