#ifndef BUFFER_MANAGER_H
#define BUFFER_MANAGER_H

// Include return codes and methods for logging errors
#include "dberror.h"

// Include bool DT
#include "dt.h" 

// Replacement Strategies
typedef enum ReplacementStrategy {
	RS_FIFO = 0,
	RS_LRU = 1,
	RS_CLOCK = 2,
	RS_LFU = 3,
	RS_LRU_K = 4
} ReplacementStrategy;

// Data Types and Structures
typedef int PageNumber;
#define NO_PAGE -1

typedef struct BM_BufferPool {
	char *pageFile;
	int numPages;
	ReplacementStrategy strategy;
	void *mgmtData; // use this one to store the bookkeeping info your buffer 
					// manager needs for a buffer pool
	void *stratData;// strategy data, used for thread safe.
} BM_BufferPool;

//original not enough for use

//typedef struct BM_PageHandle {
//	PageNumber pageNum;
//	char *data;
//} BM_PageHandle;

#include <time.h>

typedef struct BM_PageHandle {
	PageNumber pageNum;
	char *data;

	int fixcount;	//reference count by clients
	int NumReadIO;  //the number of pages that have been read from disk since a buffer pool has been initialized
	int NumWriteIO; //the number of pages written to the page file since the buffer pool has been initialized.

	int HitCount;   //when hit, increase by 1
	int HitTime; //hit time

	bool isDirty;  //is the page dirty?

} BM_PageHandle;

#define UNREFERENCED_PARAMETER(P) (P)
typedef BM_PageHandle* PBM_PageHandle;

// convenience macros
#define MAKE_POOL()					\
	((BM_BufferPool *) malloc (sizeof(BM_BufferPool)))

#define MAKE_PAGE_HANDLE()				\
	((BM_PageHandle *) malloc (sizeof(BM_PageHandle)))

// Buffer Manager Interface Pool Handling
RC initBufferPool
(
	BM_BufferPool *const bm,
	const char *const pageFileName, 
	const int numPages, 
	ReplacementStrategy strategy, 
	void *stratData
);

RC shutdownBufferPool(BM_BufferPool *const bm);
RC forceFlushPool(BM_BufferPool *const bm);

// Buffer Manager Interface Access Pages
RC markDirty (BM_BufferPool *const bm, BM_PageHandle *const page);
RC unpinPage (BM_BufferPool *const bm, BM_PageHandle *const page);
RC forcePage (BM_BufferPool *const bm, BM_PageHandle *const page);
RC pinPage (BM_BufferPool *const bm, BM_PageHandle *const page, 
	    const PageNumber pageNum);

// Statistics Interface
PageNumber *getFrameContents (BM_BufferPool *const bm);
bool *getDirtyFlags (BM_BufferPool *const bm);
int *getFixCounts (BM_BufferPool *const bm);
int getNumReadIO (BM_BufferPool *const bm);
int getNumWriteIO (BM_BufferPool *const bm);

//helper functions

RC ReplacePage(BM_BufferPool * const bm, int const replaceIndex, BM_PageHandle * const page, int const pageNum, char const * pageContent);
int FindPage_FIFO(PBM_PageHandle PageFrameArray, int maxIndex, int curIndex, int nextIndex);


#endif
