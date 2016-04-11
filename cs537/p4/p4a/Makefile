.PHONY: all
all : libcrawler.so file_tester web_tester linked_list hash_set

file_tester : file_tester.c libcrawler.so
	gcc -g -L. -lcrawler -lpthread file_tester.c -Wall -Werror -o file_tester

test: file_tester
	./file_tester pagea


web_tester : web_tester.c cs537.c libcrawler.so
	gcc -g -L. -lcrawler -lpthread web_tester.c cs537.c -Wall -Werror -o web_tester

libcrawler.so : crawler.c linked_list.c hash_set.c
	gcc -g -fpic -c crawler.c -Wall -Werror -o crawler.o
	gcc -g -fpic -c linked_list.c -Wall -Werror -o linked_list.o
	gcc -g -fpic -c hash_set.c -Wall -Werror -o hash_set.o
	gcc -g -shared -o libcrawler.so crawler.o linked_list.o hash_set.o

.PHONY: clean
clean :
	rm -f file_tester web_tester libcrawler.so *.o *~ hash_set linked_list
