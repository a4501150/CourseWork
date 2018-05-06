#include<stdio.h>
#include<stdlib.h>
#include<sys/stat.h>
#include<sys/types.h>

#include<string.h>
#include<math.h>

#include "storage_mgr.h"


//Leave it as is for futher uses
extern void initStorageManager (void) {
	return;
}

//Create a new page file fileName. The initial file size should be one page. This method should fill this single page with '\0' bytes.
extern RC createPageFile (char *fileName) {

	RC ret;

	//"w" write: Create an empty file for output operations. If a file with the same name already exists, 
	//its contents are discarded and the file is treated as a new empty file.
	FILE* fpPageFile = fopen(fileName, "w");

	//IF FOPEN Failed, RETURN ERROR CODE
	if (fpPageFile == NULL)
		return RC_SM_FOPEN_ERROR;

	//ELSE, WRITE IT WITH \0
	ret = fseek(fpPageFile, 0, SEEK_SET);
	if (ret != RC_OK)
		return RC_SM_FSEEK_ERROR;

	//allocate buffer on stack
	char buffer[PAGE_SIZE] = { 0 };

	//write buffer
	ret = fwrite(buffer, sizeof(char), PAGE_SIZE, fpPageFile);
	if (ret != PAGE_SIZE)
		return RC_SM_FWRITE_ERROR;

	//Clean up
	ret = fclose(fpPageFile);

	if (ret == 0)
		return RC_OK;
	else
		return RC_SM_FCLOSE_ERROR;

}

//Opens an existing page file.
//Should return RC_FILE_NOT_FOUND if the file does not exist.
//The second parameter is an existing file handle.
//If opening the file is successful, then the fields of this file handle should be initialized with the information about the opened file.
//For instance, you would have to read the total number of pages that are stored in the file from disk.
extern RC openPageFile (char *fileName, SM_FileHandle *fHandle) {

	// if file doesn't exist
	if (access((const char*)fileName, 0) == -1)
		return RC_FILE_NOT_FOUND;

	//get handle to file, OPEN IT WITH read/update access rights
	FILE* fpPageFile = fopen((const char*)fileName, "r+");

	//In case of SYSTEM CALL Filed
	if (fpPageFile == NULL)
		return RC_SM_FOPEN_ERROR;

	//Get File stat, using sys call stat.
	struct stat st = {0};
	int ret = stat((const char*)fileName, &st);

	//In case of SYSTEM CALL Filed
	if (ret < 0)
	{
		fclose(fpPageFile);
		return RC_FILE_SYS_ERROR;
	}
		
	//initialize our own file descriptor
	//fHandle = malloc(sizeof(PAGE_SIZE) * sizeof(char));
	
	fHandle->fileName = fileName;
	fHandle->curPagePos = 0;
	fHandle->totalNumPages = st.st_size / PAGE_SIZE;
	//keep file stream handle for futher uses
	fHandle->mgmtInfo = fpPageFile;

	return RC_OK;

}

//Close an open page file.
extern RC closePageFile (SM_FileHandle *fHandle) {
	
	RC ret;

	//handle some race condition errors.
	if (fHandle->mgmtInfo == NULL)
		return RC_FILE_NOT_FOUND;

	ret = fclose(fHandle->mgmtInfo);
	fHandle->mgmtInfo = NULL;

	////fHandle is allocated in heap thus needs to be freed. NULL ing is for defensive coding.
	//free(fHandle);
	//fHandle = NULL;
	return RC_OK;

}

//destroy(delete) a page file.
extern RC destroyPageFile (char *fileName) {

	//test access right first
	if (access(fileName, F_OK|W_OK) != -1) {

		// Deleting the file with given filename 
		remove(fileName);

		return RC_OK;

	}
	else {
		// file doesn't exist / cannot safe delete, then return error.
		return RC_FILE_SYS_ERROR;
	}
	
}

//The method reads the pageNumth block from a file and stores its content in the memory pointed to by the memPage page handle. 
//If the file has less than pageNum pages, the method should return RC_READ_NON_EXISTING_PAGE.
extern RC readBlock (int pageNum, SM_FileHandle *fHandle, SM_PageHandle memPage) {

	RC ret;

	//first check if SM_FileHandle is valid (FILE is opened and not locked)
	if (fHandle->mgmtInfo == NULL)
		return RC_FILE_HANDLE_NOT_INIT;

	//check wether memPage is valid
	if (memPage == NULL)
		return RC_BAD_PARAMETER;

	//second check if pageNum is in range.
	if (pageNum < 0 || pageNum >= fHandle->totalNumPages)
		return RC_READ_NON_EXISTING_PAGE;

	//Calc the position
	long pos = pageNum * PAGE_SIZE;

	//set cursor position
	ret = fseek(fHandle->mgmtInfo, pos, SEEK_SET);

	if (ret!=0)
		return RC_SM_FSEEK_ERROR;

	//read to buffer
	ret = fread(memPage, sizeof(char), PAGE_SIZE, fHandle->mgmtInfo);

	if (ret != PAGE_SIZE)
		return RC_SM_FREAD_ERROR;

	//Set page pos
	fHandle->curPagePos = pageNum;
	
	return RC_OK;

}

//Return the current page position in a file
extern int getBlockPos (SM_FileHandle *fHandle) {
	return fHandle->curPagePos;
}

//Read the first page in a file
extern RC readFirstBlock (SM_FileHandle *fHandle, SM_PageHandle memPage) {
	return readBlock(0, fHandle, memPage);
}

extern RC readPreviousBlock (SM_FileHandle *fHandle, SM_PageHandle memPage) {
	return readBlock(getBlockPos(fHandle) - 1, fHandle, memPage);
}

extern RC readCurrentBlock (SM_FileHandle *fHandle, SM_PageHandle memPage) {
	return readBlock(getBlockPos(fHandle), fHandle, memPage);
}

extern RC readNextBlock (SM_FileHandle *fHandle, SM_PageHandle memPage){
	return readBlock(getBlockPos(fHandle) + 1, fHandle, memPage);
}

//Read the first page in a file
extern RC readLastBlock (SM_FileHandle *fHandle, SM_PageHandle memPage){
	return readBlock(fHandle->totalNumPages - 1, fHandle, memPage);
}

//Write a page to disk using an absolute position.
extern RC writeBlock (int pageNum, SM_FileHandle *fHandle, SM_PageHandle memPage) {

	RC ret;

	//first check if SM_FileHandle is valid (FILE is opened and not locked)
	if (fHandle->mgmtInfo == NULL)
		return RC_FILE_HANDLE_NOT_INIT;

	//second check if pageNum is in range.
	if (pageNum < 0 || pageNum >= fHandle->totalNumPages)
		return RC_READ_NON_EXISTING_PAGE;

	//Check wether memPage is valid
	if (memPage == NULL)
		return RC_BAD_PARAMETER;

	//Calc the position
	long pos = pageNum * PAGE_SIZE;

	//set cursor position
	ret = fseek(fHandle->mgmtInfo, pos, SEEK_SET);

	if (ret!=0)
		return RC_SM_FSEEK_ERROR;

	//WRITE to buffer
	ret = fwrite(memPage, sizeof(char), PAGE_SIZE, fHandle->mgmtInfo);

	if (ret!= PAGE_SIZE)
		return RC_SM_FWRITE_ERROR;

	//Set page pos
	fHandle->curPagePos = pageNum;

	return RC_OK;


}

//Write a page to disk using the current position position.
extern RC writeCurrentBlock (SM_FileHandle *fHandle, SM_PageHandle memPage) {
	return writeBlock(getBlockPos(fHandle), fHandle, memPage);
}

//Increase the number of pages in the file by one. The new last page should be filled with zero bytes.
extern RC appendEmptyBlock (SM_FileHandle *fHandle) {

	//first check if SM_FileHandle is valid (FILE is opened and not locked)
	if (fHandle->mgmtInfo == NULL)
		return RC_FILE_HANDLE_NOT_INIT;

	RC ret;
	long fileCursor;

	//GET CURRENT CURSOR
	fileCursor = ftell(fHandle->mgmtInfo);

	if (fileCursor < 0)
		return RC_FILE_SYS_ERROR;

	//Move to end of the file
	ret = fseek(fHandle->mgmtInfo, 0, SEEK_END);

	if(ret!=RC_OK)
		return RC_SM_FSEEK_ERROR;

	//write 0 to it, first alloc buffer
	char* buffer = calloc(PAGE_SIZE, sizeof(char));

	//write
	ret = fwrite(buffer, sizeof(char), PAGE_SIZE, fHandle->mgmtInfo);

	if (ret != PAGE_SIZE)
		return RC_SM_FWRITE_ERROR;

	//INCREASE our own struct
	fHandle->totalNumPages++;

	//clean up
	free(buffer);

	ret = fseek(fHandle->mgmtInfo, fileCursor, SEEK_SET);

	if (ret != 0)
		return RC_SM_FSEEK_ERROR;

	return RC_OK;
}

//If the file has less than numberOfPages pages then increase the size to numberOfPages.
extern RC ensureCapacity (int numberOfPages, SM_FileHandle *fHandle) {
	
	//first check if SM_FileHandle is valid (FILE is opened and not locked)
	if (fHandle->mgmtInfo == NULL || numberOfPages <=0 )
		return RC_BAD_PARAMETER;


	//IF capacity is good then just return ok
	if (numberOfPages <= fHandle->totalNumPages)
		return RC_OK;

	//increase file to right one
	int newPageNeeded = numberOfPages - fHandle->totalNumPages;

	//append new pages rec.
	for (int i = 0; i < newPageNeeded; i++)
		appendEmptyBlock(fHandle);


	return RC_OK;
}
