#include <stdio.h>
#include <unistd.h>
#include <assert.h>
#include "mythreads.h"

volatile int max; // size of buffer
int *buffer; 

volatile int loops; 

volatile int useptr  = 0;
volatile int fillptr = 0;
volatile int numfull = 0;

pthread_cond_t empty  = PTHREAD_COND_INITIALIZER;
pthread_cond_t fill   = PTHREAD_COND_INITIALIZER;
pthread_mutex_t m     = PTHREAD_MUTEX_INITIALIZER;

#define CMAX (100)
volatile int consumers = 1;

int verbose = 0;

void do_fill(int value) {
    buffer[fillptr] = value;
    fillptr = (fillptr + 1) % max;
    numfull++;
}

int do_get() {
    int tmp = buffer[useptr];
    useptr = (useptr + 1) % max;
    numfull--;
    return tmp;
}

void *producer(void *arg) {
    int i;
    for (i = 0; i < loops; i++) {
	Mutex_lock(&m);            // p1
	while (numfull == max)     // p2
	    Cond_wait(&empty, &m); // p3
	do_fill(i);                // p4
	Cond_signal(&fill);        // p5
	Mutex_unlock(&m);          // p6
    }

    // end case: put a -1 for each consumer
    // when the consumer sees that, it will exit
    for (i = 0; i < consumers; i++) {
	Mutex_lock(&m);
	while (numfull == max) 
	    Cond_wait(&empty, &m);
	do_fill(-1);
	Cond_signal(&fill);
	Mutex_unlock(&m);
    }
    return NULL;
}
                                                                               
void *consumer(void *arg) {
    int tmp = 0;
    while (tmp != -1) { 
	Mutex_lock(&m);            // c1
	while (numfull == 0)       // c2 
	    Cond_wait(&fill, &m);  // c3
	tmp = do_get();            // c4
	Cond_signal(&empty);       // c5
	Mutex_unlock(&m);          // c6
	if (verbose) printf("%d\n", tmp);
    }
    return NULL;
}

int
main(int argc, char *argv[])
{
    if (argc != 5) {
	fprintf(stderr, "usage: %s <buffersize> <loops> <consumers> <verbose>\n", argv[0]);
	exit(1);
    }
    max = atoi(argv[1]);
    loops = atoi(argv[2]);
    consumers = atoi(argv[3]);
    verbose = atoi(argv[4]);

    assert(consumers <= CMAX && consumers > 0);
    assert(loops > 0);
    assert(max > 0);

    buffer = (int *) Malloc(max * sizeof(int));
    int i;
    for (i = 0; i < max; i++) {
	buffer[i] = 0; 
    }

    pthread_t pid, cid[CMAX];
    // create one producer and some consumers
    Pthread_create(&pid, NULL, producer, NULL); 
    for (i = 0; i < consumers; i++) {
	Pthread_create(&cid[i], NULL, consumer, NULL); 
    }
    // wait for all threads to finish
    Pthread_join(pid, NULL); 
    for (i = 0; i < consumers; i++) {
	Pthread_join(cid[i], NULL); 
    }
    return 0;
}

