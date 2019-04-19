/****************************************************************************************
* Guangyang Chen
* 1518931
* PA2
* Lex.c
* Client program
*****************************************************************************************/
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include "List.h"
#define MAX_LEN 1000

int main(int argc, char * argv[]){
   
   FILE *in, *in2, *out;
   char line[MAX_LEN];
   int count = 0;
   int i = 0;
   
   //check 3 arugments 
   if( argc != 3 ){
      printf("Usage: %s <input file> <output file>\n", argv[0]);
      exit(1);
   }

   //open files to read and write 
   in = fopen(argv[1], "r");
   out = fopen(argv[2], "w");
   if( in==NULL ){
      printf( "Unable to open file %s for reading\n", argv[1]);
      exit(1);
   }
   if( out==NULL ){
      printf("Unable to open file %s for writing\n", argv[2]);
      exit(1);
   }
   
   //count the lines
   while( fgets(line, MAX_LEN, in) != NULL) {
      count++;
   }
   fclose(in);
   
   in2 = fopen(argv[1], "r");
   char *str[count];
   while( fgets(line, MAX_LEN, in2) != NULL) {
      str[i] = malloc(strlen(line)+1);
      strcpy(str[i], line);
      i++;
   }
   
   // Create new List ADT
   List list = newList();

	append(list,0);
   moveFront(list);

   // Insertion Sort 
   for(int j = 1; j < count; ++j) {
      char *temp = str[j];
      moveBack(list);
      while(index(list) >= 0 && strcmp(temp, str[get(list)]) < 0){
         movePrev(list); 
      }
      if(index(list) >= 0){
         insertAfter(list,j);
      } else {
         prepend(list,j);
      }
   }
   moveFront(list);

   while(index(list)>=0) {
      fprintf(out, str[get(list)]); 
      moveNext(list);
   }
  
   // Free Array and list ADT
   for(i = 0; i < count; i++) {
      free(str[i]);
   }
   clear(list);
   freeList(&list);
     
   // Close files
   fclose(in2);
   fclose(out);
}