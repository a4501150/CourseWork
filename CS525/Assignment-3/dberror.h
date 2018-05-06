#ifndef DBERROR_H
#define DBERROR_H

#include "stdio.h"

/* module wide constants */
#define PAGE_SIZE 4096

/* return code definitions */
typedef int RC;

#define RC_OK 0
#define RC_FILE_NOT_FOUND 1
#define RC_FILE_HANDLE_NOT_INIT 2
#define RC_WRITE_FAILED 3
#define RC_READ_NON_EXISTING_PAGE 4
#define RC_ERROR 400 // Added a new definiton for ERROR
#define RC_PINNED_PAGES_IN_BUFFER 500 // Added a new definition for Buffer Manager


// Added new definitions for Record Manager
#define RC_RM_NO_TUPLE_WITH_GIVEN_RID 600
#define RC_SCAN_CONDITION_NOT_FOUND 601


//add rc error
#define RC_STORAGE_MANAGER_NOT_INITIALIZED 5
#define RC_FILE_CREATION_FAILED 6
#define RC_WRITE_TO_OUTPUTSTREAM_FAILED 7
#define RC_INCOMPATIBLE_BLOCKSIZE 8
#define RC_FILE_NOT_INITIALIZED 9
#define RC_FILE_OFFSET_FAILED 10
#define RC_FILE_READ_FAILED 11
#define RC_FILE_NOT_CLOSED 12
#define RC_CURRENT_PAGE_NOT_FOUND 13
#define RC_NO_FILENAME 14
#define RC_MISMATCH 15
#define RC_PINNED_NOT_OUT 16
#define RC_MATCH 17
#define RC_NOT_REMOVED 18
#define RC_WRITE_NON_EXISTING_PAGE 19
//end adding

#define RC_RM_COMPARE_VALUE_OF_DIFFERENT_DATATYPE 200
#define RC_RM_EXPR_RESULT_IS_NOT_BOOLEAN 201
#define RC_RM_BOOLEAN_EXPR_ARG_IS_NOT_BOOLEAN 202
#define RC_RM_NO_MORE_TUPLES 203
#define RC_RM_NO_PRINT_FOR_DATATYPE 204
#define RC_RM_UNKOWN_DATATYPE 205

//add rc error
#define RC_PAGE_NOT_FOUND 206
#define RC_LIST_EMPTY 207
#define RC_PAGE_FOUND 208
#define RC_PAGE_NOT_EXIST 209
#define RC_NO_REPLACEMENT_STRATEGY 210

#define RC_IM_KEY_NOT_FOUND 300
#define RC_IM_KEY_ALREADY_EXISTS 301
#define RC_IM_N_TO_LAGE 302
#define RC_IM_NO_MORE_ENTRIES 303

/* holder for error messages */
extern char *RC_message;

/* print a message to standard out describing the error */
extern void printError (RC error);
extern char *errorMessage (RC error);

#define THROW(rc,message) \
  do {			  \
    RC_message=message;	  \
    return rc;		  \
  } while (0)		  \

// check the return code and exit if it is an error
#define CHECK(code)							\
  do {									\
    int rc_internal = (code);						\
    if (rc_internal != RC_OK)						\
      {									\
	char *message = errorMessage(rc_internal);			\
	printf("[%s-L%i-%s] ERROR: Operation returned error: %s\n",__FILE__, __LINE__, __TIME__, message); \
	free(message);							\
	exit(1);							\
      }									\
  } while(0);


#endif