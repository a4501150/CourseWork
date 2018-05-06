#include<stdio.h>
#include<stdlib.h>
#include "buffer_mgr.h"
#include "storage_mgr.h"
#include <math.h>
#include <string.h>

//for threading
#include <pthread.h>

#ifdef _WIN32
//for windows support
#pragma comment( lib, "pthreads\\pthreadVC2.lib" )  
#endif


/*
Global Virable is not thread safe nor reentrant. So change it to dynamic allocate buffer;

static int clock_tick = 0;

read function is not required lock to be thread safe, so just lock in REAL Critical part. For example, write related. force page. ping page etc.

Also, since thread efficiency is not required for extra credit, just lock where we need, and no need to worry about redesign interface.

*/

typedef unsigned long long BigInt; //ulonglong

//setup our global mutex
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

RC initBufferPool(BM_BufferPool * const bm, const char * const pageFileName, const int numPages, ReplacementStrategy strategy, void * stratData)
{
	RC ret;

	UNREFERENCED_PARAMETER(stratData);

	//Check parameter
	if (bm == NULL || pageFileName == NULL || numPages <= 0)
		return RC_BAD_PARAMETER;

	//initialize our buffer pool
	bm->pageFile = pageFileName;
	bm->numPages = numPages;
	bm->strategy = strategy;

	//initialize Our Pages, with numPages pages. 
	PBM_PageHandle PageFrameArray = calloc(numPages,sizeof(BM_PageHandle));

	//make it as an empty page
	for (int i = 0; i < numPages; ++i)
	{
		PageFrameArray[i].pageNum = NO_PAGE;
		PageFrameArray[i].data = calloc(PAGE_SIZE,sizeof(char));
		PageFrameArray[i].fixcount = 0;
		PageFrameArray[i].NumReadIO = 0;
		PageFrameArray[i].NumWriteIO = 0;
		PageFrameArray[i].isDirty = false;
		PageFrameArray[i].HitCount = 0;
		PageFrameArray[i].HitTime = -1;
	}

	//store our array in mgmtData for futher uses
	bm->mgmtData = PageFrameArray;

	//clock tick
	bm->stratData = calloc(1,sizeof(BigInt));


	return RC_OK;
}

RC shutdownBufferPool(BM_BufferPool * const bm)
{
	RC ret;

	//for readable code
	PBM_PageHandle PageFrameArray = (PBM_PageHandle)bm->mgmtData;

	//check if there's any pinned page
	for (int i = 0; i < bm->numPages; ++i)
	{
		if (PageFrameArray[i].fixcount != 0)
		{
			return RC_BM_POOL_LOCKED;
		}
	}

	//lock here
	pthread_mutex_lock(&mutex);

	//do our job here.
	ret = forceFlushPool(bm);

	//unlock 
	pthread_mutex_unlock(&mutex);

	//return any errors.
	if (ret != RC_OK)
	{
		printf("forceFlushPool failed in shutdownBufferPool\n");
		return ret;
	}
		
	//clean up !!!!!!!!!@!@@!@!@!@

	//free data
	for (int i = 0; i < bm->numPages; ++i)
	{
		free(PageFrameArray[i].data);
	}
	free(PageFrameArray);
	free(bm->stratData);

	return RC_OK;
}

RC forceFlushPool(BM_BufferPool * const bm)
{
	//for readable code
	PBM_PageHandle PageFrameArray = (PBM_PageHandle)bm->mgmtData;

	RC ret;

	for (int i = 0; i < bm->numPages; ++i)
	{
		if (PageFrameArray[i].isDirty == true && PageFrameArray[i].fixcount == 0)
		{
			SM_FileHandle fHandle;

			ret = openPageFile(bm->pageFile, &fHandle);

			if (ret != RC_OK)
				return RC_FILE_SYS_ERROR;

			//WRITE back to file
			ret = writeBlock(PageFrameArray[i].pageNum, &fHandle, PageFrameArray[i].data);

			if (ret != RC_OK)
				return RC_FILE_SYS_ERROR;

			//increase stat
			PageFrameArray[i].NumWriteIO++;

			//since written to disk and fix count = 0, mark it as non dirty
			PageFrameArray[i].isDirty = false;

			//cleap up
			ret = closePageFile(&fHandle);

			if (ret != RC_OK)
				return RC_FILE_SYS_ERROR;

		}
	}

	return RC_OK;
}

RC markDirty(BM_BufferPool * const bm, BM_PageHandle * const page)
{
	//check parameter first
	if (bm == NULL || page == NULL)
		return RC_BAD_PARAMETER;

	//Handle Out Our Page Array
	PBM_PageHandle PageArray = bm->mgmtData;

	for (int i = 0; i < bm->numPages; ++i)
	{
		if (PageArray[i].pageNum == page->pageNum)
		{
			PageArray[i].isDirty = true;
			page->isDirty = true;
			return RC_OK;
		}
	}

	return RC_BM_NON_EXISTING_PAGE;
}

RC unpinPage(BM_BufferPool * const bm, BM_PageHandle * const page)
{
	RC ret;

	//check parameter first
	if (bm == NULL || page == NULL)
		return RC_BAD_PARAMETER;

	//Handle Out Our Page Array
	PBM_PageHandle PageArray = bm->mgmtData;

	for (int i = 0; i < bm->numPages; ++i)
	{
		if (PageArray[i].pageNum == page->pageNum)
		{
			PageArray[i].fixcount--;

			return RC_OK;
		}
	}

	return RC_BM_NON_EXISTING_PAGE;
}

RC forcePage(BM_BufferPool * const bm, BM_PageHandle * const page)
{
	RC ret;

	//check parameter first
	if (bm == NULL || page == NULL)
		return RC_BAD_PARAMETER;

	//Handle Out Our Page Array
	PBM_PageHandle PageArray = bm->mgmtData;

	for (int i = 0; i < bm->numPages; ++i) 
	{
		if (PageArray[i].pageNum == page->pageNum)
		{
			SM_FileHandle fHandle;

			ret = openPageFile(bm->pageFile, &fHandle);
			if (ret != RC_OK)
			{
				printf("forcePage openPageFile failed\n");
				return ret;
			}
				
			ret = writeBlock(PageArray[i].pageNum, &fHandle, page->data);
			if (ret != RC_OK)
			{
				printf("forcePage writeBlock failed\n");
				return ret;
			}
				
			ret = closePageFile(&fHandle);
			if (ret != RC_OK)
			{
				printf("forcePage closePageFile\n");
				return ret;
			}

			return RC_OK;

		}
	}


	return RC_BM_NON_EXISTING_PAGE;
}

RC ReplacePage(BM_BufferPool * const bm, int const replaceIndex, BM_PageHandle * const page, int const pageNum, char const * pageContent) {

	RC ret;

	//Handle Out Our Page Array
	PBM_PageHandle PageArray = bm->mgmtData;

	//replace our page, if dirty then it needs to write to disk
	if (PageArray[replaceIndex].isDirty == true)
	{

		//memmove(PageArray[i].data, page->data, sizeof(char)*PAGE_SIZE);

		ret = forcePage(bm, &PageArray[replaceIndex]);

		if (ret != RC_OK)
			return ret;

		//successfully write to disk, increase io count.
		PageArray[replaceIndex].NumWriteIO++;

		PageArray[replaceIndex].isDirty = false;

	}

	memmove(PageArray[replaceIndex].data, pageContent, PAGE_SIZE * sizeof(char));

	//modify our pool first
	PageArray[replaceIndex].fixcount = 1;
	PageArray[replaceIndex].NumReadIO += 1;
	PageArray[replaceIndex].pageNum = pageNum;
	PageArray[replaceIndex].HitCount++;
	PageArray[replaceIndex].HitTime = (*((BigInt*)bm->stratData))++;//clock_tick++;

	//direct mem address assign to user supply buffer
	page->data = PageArray[replaceIndex].data;
	page->pageNum = pageNum;

	return RC_OK;

}

//second chance 
int FindPage_Clock(PBM_PageHandle PageFrameArray, int maxIndex)
{
	int i = 0;

	//using temporary array so actually array won't get effected
	PBM_PageHandle tmpArray = malloc(sizeof(BM_PageHandle) * (maxIndex + 1));
	memmove(tmpArray, PageFrameArray, sizeof(BM_PageHandle) * (maxIndex + 1));

	while (true) {

		//skip pinned page.
		if (tmpArray[i].fixcount != 0)
		{

			if (i != maxIndex)
				i++;
			else
				i = 0;

			continue;
		}

		//found correct shit return it and clean temp array
		if (tmpArray[i].HitCount == 0)
		{
			free(tmpArray);
			return i;
		}

		//do core part of second chance algrorithm
		if (tmpArray[i].HitCount != 0)
		{
			tmpArray[i].HitCount--;
			if (i != maxIndex)
				i++;
			else
				i = 0;
			continue;
		}


	}
}

int FindPage_LFU(PBM_PageHandle PageFrameArray, int maxIndex)
{

	//start from beginning
	int leastHitCount = PageFrameArray[0].HitCount;

	int leastIndex = -1;

	//find least count;
	for (int i = 0; i <= maxIndex; ++i)
	{
		if (PageFrameArray[i].fixcount != 0)
			continue;

		if (PageFrameArray[i].HitCount <= leastHitCount) {
			leastHitCount = PageFrameArray[i].HitCount;
		}
	}

	//return first least count;
	for (int i = 0; i <= maxIndex; ++i)
	{
		if (PageFrameArray[i].fixcount != 0)
			continue;

		if (PageFrameArray[i].HitCount == leastHitCount) {
			leastIndex = i;
			break;
		}
	}

	return leastIndex;
}


int FindPage_LRU(PBM_PageHandle PageFrameArray, int maxIndex)
{
	 
	//start from beginning
	int leastHitTime = PageFrameArray[0].HitTime;

	int leastIndex = -1;

	for (int i = 0; i <= maxIndex; ++i) 
	{
		if (PageFrameArray[i].fixcount != 0)
			continue;

		if (PageFrameArray[i].HitTime <= leastHitTime) {
			leastIndex = i;
			leastHitTime = PageFrameArray[i].HitTime;
		}
	}

	return leastIndex;
}

int FindPage_FIFO(PBM_PageHandle PageFrameArray,int maxIndex,int curIndex, int nextIndex)
{
	//simple fifo algorithm

	if (PageFrameArray[curIndex].fixcount != 0)
	{
		return FindPage_FIFO(PageFrameArray, maxIndex, curIndex+1, nextIndex);
	}

	if (PageFrameArray[nextIndex].fixcount != 0)
	{
		return FindPage_FIFO(PageFrameArray, maxIndex, curIndex, nextIndex + 1);
	}
 
	if (nextIndex > maxIndex)
	{
		return FindPage_FIFO(PageFrameArray, maxIndex, curIndex, 0);
	}

	if (PageFrameArray[curIndex].NumReadIO > PageFrameArray[nextIndex].NumReadIO && PageFrameArray[nextIndex].fixcount == 0)
	{
		return nextIndex;
	}

	return curIndex;
}


RC pinPage(BM_BufferPool * const bm, BM_PageHandle * const page, const PageNumber pageNum)
{

	RC ret;

	//check parameter first
	if (bm == NULL || page == NULL || pageNum < 0)
		return RC_BAD_PARAMETER;

	//ensure our file is big enough
	SM_FileHandle fHandle;

	ret = openPageFile(bm->pageFile, &fHandle);
	ret = ensureCapacity(pageNum+1, &fHandle);
	ret = closePageFile(&fHandle);
	if (ret != RC_OK)
		return ret;

	//initialize user supply buffer
	page->pageNum = NO_PAGE;
	page->data = NULL;
	page->fixcount = 0;
	page->NumReadIO = 0;
	page->NumWriteIO = 0;
	page->isDirty = false;
	page->HitCount = 0;
	page->HitTime = -1;

	//Handle Out Our Page Array
	PBM_PageHandle PageArray = bm->mgmtData;

	//Check if we already have a cached page
	for (int i = 0; i < bm->numPages; ++i) 
	{
		//if so return it.
		if (PageArray[i].pageNum == pageNum)
		{
			PageArray[i].fixcount++;
			PageArray[i].HitCount++;
			PageArray[i].HitTime = (*((BigInt*)bm->stratData))++;//clock_tick++;

			page->data = PageArray[i].data;
			page->pageNum = pageNum;

			//page->fixcount = PageArray[i].fixcount;
			return RC_OK;
		}	
	}
	//IF not, lets cahced it in.

	//open the file
	ret = openPageFile(bm->pageFile, &fHandle);

	//error handling
	if (ret != RC_OK)
	{
		printf("pinPage openPageFile failed\n");
		return ret;
	}
		
	//alloc a temporary buffer in stack
	char pageContent[PAGE_SIZE] = { 0 };

	//read specific block
	ret = readBlock(pageNum, &fHandle, pageContent);

	//error handling
	if (ret != RC_OK)
	{
		printf("pinPage readBlock failed %s\n");
		return ret;
	}

	//close our file, No resource leak 
	ret = closePageFile(&fHandle);

	//error handling
	if (ret != RC_OK)
	{
		printf("pinPage closePageFile failed\n");
		return ret;
	}

	//find if there're any unused page
	for (int i = 0; i < bm->numPages; ++i)
	{
		//found page num == -1! 
		if (PageArray[i].pageNum == NO_PAGE)
		{
			memmove(PageArray[i].data, pageContent, PAGE_SIZE * sizeof(char));

			//modify our pool first
			PageArray[i].fixcount++;
			PageArray[i].NumReadIO++;
			PageArray[i].pageNum = pageNum;
			PageArray[i].HitCount++;
			PageArray[i].HitTime = (*((BigInt*)bm->stratData))++;//clock_tick++;

			//direct mem address assign to user supply buffer
			page->data = PageArray[i].data;
			page->pageNum = pageNum;

			return RC_OK;
		}
	}

	//check if there're no more removable page
	for (int i = 0; i < bm->numPages; ++i)
	{
		//found page then break
		if (PageArray[i].fixcount == 0)
		{
			break;
		}
		//retur error
		return RC_BM_NO_USABLE_PAGE;
	}
		
	/*

	How we replace our shit
	
	Since instruction said:

	A buffer pool consists of a fixed amount of page frames (pages in memory)
	
	Thus, we dont need to expand our pool. 

	So if we didn't found any removable page (with fix count == 0), just return an error.

	*/

	//If there's no any white page, then we need replace a shit
	switch (bm->strategy)
	{
		//first in first out
		case RS_FIFO:

			{
				//get correct replace index using FIFO
				int i = FindPage_FIFO(PageArray, bm->numPages - 1, 0, 1);

				//replace our page
				ret = ReplacePage(bm, i, page, pageNum, pageContent);

				//return RC_OK
				return ret;

			}	

			break;

		case RS_LRU:

		{
			
			//get correct replace index using LRU
			int i = FindPage_LRU(PageArray, bm->numPages - 1);

			//replace our page
			ret = ReplacePage(bm, i, page, pageNum, pageContent);

			//return RC_OK
			return ret;

		}


			break;
		case RS_CLOCK:

		{

			//get correct replace index using Clock
			int i = FindPage_Clock(PageArray, bm->numPages - 1);

			//replace our page
			ret = ReplacePage(bm, i, page, pageNum, pageContent);

			//return RC_OK
			return ret;

		}

			break;
		case RS_LFU:
		{

			//get correct replace index using Clock
			int i = FindPage_LFU(PageArray, bm->numPages - 1);

			//replace our page
			ret = ReplacePage(bm, i, page, pageNum, pageContent);

			//return RC_OK
			return ret;

		}

			break;
		case RS_LRU_K:
			break;
		default:
			ret = RC_BAD_PARAMETER;
	}

	return ret;
}

PageNumber * getFrameContents(BM_BufferPool * const bm)
{
	//possible memory leaks here. depends on test
	int* PageNumberArray = malloc(sizeof(int) * bm->numPages);

	for (int i = 0; i < bm->numPages; ++i)
	{
		PageNumberArray[i] = ((PBM_PageHandle)bm->mgmtData)[i].pageNum;
	}

	return PageNumberArray;
}

bool * getDirtyFlags(BM_BufferPool * const bm)
{
	//possible memory leaks here. depends on test
	bool* DirtyFlagArray = malloc(sizeof(bool) * bm->numPages);

	for (int i = 0; i < bm->numPages; ++i)
	{
		DirtyFlagArray[i] = ((PBM_PageHandle)bm->mgmtData)[i].isDirty;
	}

	return DirtyFlagArray;
}

int * getFixCounts(BM_BufferPool * const bm)
{
	//possible memory leaks here. depends on test
	int* FixCountArray = malloc(sizeof(int) * bm->numPages);

	for (int i = 0; i < bm->numPages; ++i)
	{
		FixCountArray[i] = ((PBM_PageHandle)bm->mgmtData)[i].fixcount;
	}

	return FixCountArray;
}

int getNumReadIO(BM_BufferPool * const bm)
{
	int NumReadIO_total = 0;
	for (int i = 0; i < bm->numPages; ++i)
	{
		NumReadIO_total += ((PBM_PageHandle)bm->mgmtData)[i].NumReadIO;
	}
	return NumReadIO_total;
}

int getNumWriteIO(BM_BufferPool * const bm)
{
	int NumWriteIO_total = 0;
	for (int i = 0; i < bm->numPages; ++i)
	{
		NumWriteIO_total += ((PBM_PageHandle)bm->mgmtData)[i].NumWriteIO;
	}
	return NumWriteIO_total;
}
