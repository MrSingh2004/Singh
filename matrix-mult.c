#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

typedef struct {
   int row;
   int col;            // TO DO: What arguments do you need to send to each thread?
} instruction;

void *cell_multiply(void *);
void read_matrix(FILE *f, int[10][10], int *, int *);
void print_matrix(char *, int[10][10], int, int);

int matrix_a[10][10];
int matrix_b[10][10];
int matrix_c[10][10];

int row_a, col_a;
int row_b, col_b;
int main(int argc, char *argv[]) {

   printf("ACO350 - Multi-Threaded Matrix Multiplication\n");
   printf("Ramandeep Singh\n\n");
   FILE *f = NULL;
 

   if (argc != 2) {
      fprintf(stderr,"usage: %s <file>\n", argv[0]);
      return 1;
   }

   f = fopen(argv[1], "r");
   if (f == NULL) {
      fprintf(stderr, "Can't open file %s\n", argv[1]);
      return -1;
   }

   read_matrix(f, matrix_a, &row_a, &col_a);
   read_matrix(f, matrix_b, &row_b, &col_b);

   print_matrix("A", matrix_a, row_a, col_a);
   print_matrix("B", matrix_b, row_b, col_b);

   if (col_a != row_b) {
      fprintf(stderr, "Error: Columns in A (%d) must equal rows in B (%d)\n", col_a, row_b);
      return 2;
   }

   int num_threads = row_a; // TO DO: How many threads will there be?

   pthread_t tids[row_a];
   pthread_attr_t attr;

   pthread_attr_init(&attr);

   // TO DO: Create the threads.
   for (int i = 0; i < row_a; i++) {
   int *row = malloc(sizeof(int)); 
   *row = i; 

   pthread_create(&tids[i], NULL, cell_multiply, row); 
}
 

   // TO DO: wait for all the threads to complete.
   for (int i = 0; i < row_a; i++) {
    pthread_join(tids[i], NULL);
}
   print_matrix("A x B = C", matrix_c, row_a, col_b);
   } 
void *cell_multiply(void *param) {
   // TO DO: Perform the assigned part of the multiply.
   int row = *(int *)param;
   free(param); 

   for (int col = 0; col < col_b; col++) { 
      int sum = 0; 
      for (int i = 0; i < col_a; i++) { 
         sum += matrix_a[row][i] * matrix_b[i][col]; 
      }
      matrix_c[row][col] = sum; 
   }

   pthread_exit(NULL);
}

void print_matrix(char *title, int m[10][10], int row_cnt, int col_cnt) {
   printf("%s [%dx%d]:\n", title, row_cnt, col_cnt);
   int max = 0;
   for(int r = 0; r < row_cnt; r++) {
      for (int c = 0; c < col_cnt; c++) {
        max = max < m[r][c] ? m[r][c] : max; 
      };
   }

   int size = (int)(log10((double)max)+1);
   char format[10];
   sprintf(format, "%%%dd ", size);

   for(int r = 0; r < row_cnt; r++) {
      printf("   ");
      for (int c = 0; c < col_cnt; c++) {
         printf(format, m[r][c]);
      };
      printf("\n");
   }
   printf("\n");
}

void read_matrix(FILE *f, int m[10][10], int *row_cnt, int *col_cnt) {
   fscanf(f, "%d %d", row_cnt, col_cnt);
   for (int r = 0; r < *row_cnt; r++) {
      for (int c = 0; c < *col_cnt; c++) {
         fscanf(f, "%d", &(m[r][c]));
      }
   }
}
