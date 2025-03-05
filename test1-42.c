#include <stdio.h>
#include <pthread.h>

int fact = 1; 

void *mul(void *param);

int main() {
    pthread_t tid;
    pthread_attr_t attr;
    int n = 10;

    pthread_attr_init(&attr);
    pthread_create(&tid, &attr, mul, &n);
    pthread_join(tid, NULL);

    printf("%d factorial = %d\n", n, fact);
}

void *mul(void *param) {
    int num = *((int *)param); 
    for (int i = num; i > 0; i--) { 
        fact *= i;
    }
    pthread_exit(0);
}
