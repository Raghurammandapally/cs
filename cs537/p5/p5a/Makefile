default:
	gcc -o fscheck -g fscheck.c
check-syntax:
	gcc -o nul -S ${CHK_SOURCES}
clean:
	rm -f fscheck
	rm -f nul
test: default
	#./fscheck
	#./fscheck tester
	./fscheck fs.h
