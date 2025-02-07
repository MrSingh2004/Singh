#include <stdio.h>
#include <time.h>
#include <sys/sysinfo.h>

int main(int argc, char *argv[]) {

	time_t current_time = time(NULL);
	struct tm *local_time = localtime(&current_time);
	
	printf("*****************************\n");
	printf("*** ACO350 - Ramandeep Singh ***\n");
	printf("*** System Info Utility ***\n");
	printf("*****************************\n\n");

	
	printf("Current Date: %s", asctime(local_time));
	printf("Current Time: %02d:%02d:%02d\n", local_time->tm_hour, local_time->tm_min, local_time->tm_sec);

	return 0;
	}
