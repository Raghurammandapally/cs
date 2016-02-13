default:
	gcc -o whoosh -g whoosh.c
	./whoosh < input.txt
check-syntax:
	gcc -o nul -S ${CHK_SOURCES}
clean:
	rm -f whoosh
	rm -f nul
