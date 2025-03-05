#include <stdio.h>
#include <omp.h>

int fact = 1;

int main() {
    int threads = 5;
    int n = 10;

    #pragma omp parallel for num_threads(threads) 
    for (int i = 1; i <= n; i++) {
        fact *= i; 
    }

    printf("%d factorial = %d\n", n, fact);
}
