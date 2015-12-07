#include <stdio.h>

int main(int argc, char *argv[])
{
	unsigned short int x;
	while(1) {
		scanf("%hu", &x);
		if(x == 42) {
			break;
		}
		printf("%hu\n", x);
	}

	return 0;
}
