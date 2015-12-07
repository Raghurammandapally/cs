#include <stdio.h>

int main(int argc, char *argv[])
{
	unsigned int len;

	scanf("%u", &len);

	unsigned int A[len];

	int i;
	for(i = 0; i < len; i++) {
		scanf("%u", &A[i]);
	}

	for(i = 0; i < len; i++) {
		printf("%u\n", A[i]);
	}

	return 0;
}
