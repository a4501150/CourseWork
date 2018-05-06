#include <stdio.h>
#include <stdlib.h>

#include "storage_mgr.h"
/*the file handling structures and interfaces of methods
 *the structure SM_FileHandle stores the file name.
 *total number of pages in the file is stored in the variable totalNumPages.
 *curPagePos stores the current page position.
 *use mgmtInfo to store bookkeeping information about a file this storage manager needs.*/
#include "dberror.h"
/*contains some errors about creating,opening,finding,reading or writing to a file.*/

/*initialize the storage manager*/
void initStorageManager(void) {

}

/*create a file with an empty page*/
RC createPageFile(char* fileName) {
	//assume the page can be created successfully;
	RC rc = RC_OK;

	//no filename error
	if (fileName == NULL) 
	{
		return RC_NO_FILENAME;
	}

	//create an empty file for both reading and writing.
	FILE *fp = fopen( fileName, "w+");

	//the file pointer is NULL
	if (fp == NULL) {
		return RC_FILE_NOT_FOUND;
	}

	//create an empty page and request a block of memory for the empty page.
	//1 represents one page.
	SM_PageHandle emptyPage = (SM_PageHandle)calloc(1, PAGE_SIZE);

	//the pointer to the allocated memory is NULL, request fails.
	if (emptyPage == NULL) {
		return RC_FILE_HANDLE_NOT_INIT;
	}

	//write the empty page to the file
	//variable element is the total number of elements successfully written
	int element = fwrite(emptyPage, PAGE_SIZE, 1, fp);

	//examine the value of element
	if (element ==0){
		rc = RC_WRITE_FAILED;
	}
	
	//free the memory
	free(emptyPage);

	//close the file
	int close = fclose(fp);
	//close failed EOF means the End of File Reached. 
	if (close == EOF){
		rc = RC_FILE_NOT_CLOSED;
	}

	//check if there was any error during the creation
	if (rc != RC_OK){
		return RC_FILE_CREATION_FAILED;
	}
	else{
		return rc;
	}
}

/*open an existing page file*/
RC openPageFile(char *fileName, SM_FileHandle *fHandle) {
	//assume the file can be opened successfully;
	RC rc = RC_OK;

	//no filename error
	if (fileName == NULL){
		return RC_NO_FILENAME;
	}

	//open a file to update both reading and writing and the file must exist
	FILE *fp = fopen(fileName, "r+");

	//the file pointer is NULL
	if (fp == NULL){
		return RC_FILE_NOT_FOUND;
	}

	//to get the total page of the file, find the end of the file first.
	//use fseek to go to the end of the file.
	int toend = fseek(fp,0L,SEEK_END);

	//fail to go to the end of file
	if (toend != 0){
		return RC_FILE_OFFSET_FAILED;
	}

	//use ftell to get the pointer of end position of the file
	long int endPos = ftell(fp);

	//Initialize the file handler
	fHandle->fileName = fileName;
	fHandle->totalNumPages = endPos / PAGE_SIZE;
	fHandle->curPagePos = 0; // the start page of file should be 0
	fHandle->mgmtInfo = fp;// stores the file pointer

	return rc;
}

/*close an open page file*/
RC closePageFile(SM_FileHandle *fHandle) {
	//assume the file can be closed successfully
	RC rc = RC_OK;

	//get the file pointer of the handler
	FILE *fp = fHandle->mgmtInfo;

	//the file pointer is NULL if the file handled by the handler not initialized
	if (fp == NULL){
		return RC_FILE_NOT_INITIALIZED;
	}

	//close the file
	int close = fclose(fp);

	//close the file failed
	if (close == EOF){
		rc = RC_FILE_NOT_CLOSED;
	}

	return rc;
}

/*destory a page file*/
RC destroyPageFile(char *fileName) {
	//assume the file can be destoryed successfully
	RC rc = RC_OK;

	//no filename error
	if(fileName == NULL){
		return RC_NO_FILENAME;
	}

	//if remove=-1, delete failed. int remove
	int removeStatus = remove(fileName);

	//remove failed
	if (removeStatus == -1)
		return RC_NOT_REMOVED;

	// no related error, so return RC_OK
	return rc;
}

/*read the pageNumth block from a file and stores its content in the memory pointed to by the memPage page handle*/
RC readBlock(int pageNum, SM_FileHandle *fHandle, SM_PageHandle memPage) {
	//assume the file can be read successfully
	RC rc = RC_OK;

	//the handeler is NULL
	if (fHandle == NULL){
		return RC_FILE_HANDLE_NOT_INIT;
	}

	FILE *fp = fHandle->mgmtInfo;

	//the file pointer is NULL if the file handled by the handler not initialized
	if (fp == NULL){
		return RC_FILE_NOT_INITIALIZED;
	}

	long int offset = pageNum * PAGE_SIZE;

	//use fseek to go to the pageNumth page of the file
	int toPageNum = fseek(fp, offset, SEEK_SET);

	//fail to go to the correct position
	if (toPageNum != 0){
		rc = RC_FILE_OFFSET_FAILED;
	}

	//variable element is the total number of elements successfully read
	int element = fread(memPage, 1, PAGE_SIZE, fp);

	//set the currPage to the PageNum
	fHandle->curPagePos = pageNum;

	fHandle->curPagePos = pageNum;

	return rc;
}

/* get the current page position in a file */
int getBlockPos(SM_FileHandle *fHandle) {
	//the handeler is NULL
	if (fHandle == NULL){
		return RC_FILE_HANDLE_NOT_INIT;
	}

	FILE *fp = fHandle->mgmtInfo;

	//the file pointer is NULL if the file handled by the handler not initialized
	if (fp == NULL){
		return RC_FILE_NOT_INITIALIZED;
	}

	return fHandle->curPagePos;
}

/*read the first page in the file*/
RC readFirstBlock(SM_FileHandle *fHandle, SM_PageHandle memPage) {
	//call the readblock method and set the pageNum = 0;
	return readBlock(0, fHandle, memPage);
}

/*read the previous block in the file*/
RC readPreviousBlock(SM_FileHandle *fHandle, SM_PageHandle memPage) {
	//if the current position is at the first page, read failed
	if (fHandle->curPagePos - 1 < 0){
		return RC_CURRENT_PAGE_NOT_FOUND;
	}
	//call the readblock and set pageNum to current position -1 which is the previous page
	else{
		return readBlock(fHandle->curPagePos - 1, fHandle, memPage);
	}
}

/*read the current block in the file*/
RC readCurrentBlock(SM_FileHandle *fHandle, SM_PageHandle memPage) {
	//call the readblock and set pageNum to current position
	return readBlock(fHandle->curPagePos, fHandle, memPage);
}

/*read the next block in the file*/
RC readNextBlock(SM_FileHandle *fHandle, SM_PageHandle memPage) {
	//if the current position is at the last page, read failed
	if (fHandle->curPagePos + 1 > fHandle->totalNumPages - 1){
		return RC_CURRENT_PAGE_NOT_FOUND;
	}
	//call the readblock and set pageNum to current position +1 which is the next page
	else{
		return readBlock(fHandle->curPagePos + 1, fHandle, memPage);
	}
}

/*read the last block in the file*/
RC readLastBlock(SM_FileHandle *fHandle, SM_PageHandle memPage) {
	//call the readblock and set pageNum to last page which is the total number of page
	return readBlock(fHandle->totalNumPages - 1, fHandle, memPage);
}

/*write a page to disk using an absolute position*/
RC writeBlock(int pageNum, SM_FileHandle *fHandle, SM_PageHandle memPage) {
	//assume the message can be written successfully
	RC rc = RC_OK;

	//the handeler is NULL
	if (fHandle == NULL){
		return RC_FILE_HANDLE_NOT_INIT;
	}

	FILE *fp = fHandle->mgmtInfo;

	//the file pointer is NULL if the file handled by the handler not initialized
	if (fp == NULL){
		return RC_FILE_NOT_INITIALIZED;
	}

	//allow to write on the new page next to the last page and change the value of total pages
	else if (pageNum == fHandle->totalNumPages){
		fHandle->totalNumPages++;
	}

	long int offset = pageNum * PAGE_SIZE;

	//use fseek to go to the pageNumth page of the file
	int toPageNum = fseek(fp, offset, SEEK_SET);

	//fail to go to the correct position
	if (toPageNum != 0){
		rc = RC_FILE_OFFSET_FAILED;
	}

	//element is the total number of elements are written successfully
	int element = fwrite(memPage, 1, PAGE_SIZE, fp);

	//write failed
	if (element != PAGE_SIZE){
		rc = RC_WRITE_FAILED;
	}

	//set the currPage to the PageNum
	fHandle->curPagePos = pageNum;

	return rc;
}

/*write to disk using the current position*/
RC writeCurrentBlock(SM_FileHandle *fHandle, SM_PageHandle memPage) {
	//call the writeblock and set pageNum to current position
	return writeBlock(fHandle->curPagePos, fHandle, memPage);
}

/*increase the number of pages in the file by one*/
RC appendEmptyBlock(SM_FileHandle *fHandle) {
	//assume the empty block can be appended successfully;
	RC rc = RC_OK;

	//the handeler is NULL
	if (fHandle == NULL){
		return RC_FILE_HANDLE_NOT_INIT;
	}

	FILE *fp = fHandle->mgmtInfo;

	//the file pointer is NULL if the file handled by the handler not initialized
	if (fp == NULL){
		return RC_FILE_NOT_INITIALIZED;
	}

	//create an empty page and request a block of memory for the empty page.
	//1 represents one page.
	SM_PageHandle emptyPage = (SM_PageHandle)calloc(1, PAGE_SIZE);

	//the pointer to the allocated memory is NULL, request fails.
	if (emptyPage == NULL){
		return RC_FILE_HANDLE_NOT_INIT;
	}

	//write the empty page at the last page
	rc = writeBlock(fHandle->totalNumPages, fHandle, emptyPage);

	return rc;
}

/*if the file has less than numberOfPages pages then increase the size to numberOfPages*/
RC ensureCapacity(int numberOfPages, SM_FileHandle *fHandle) {
	RC rc = RC_OK;

	//the handeler is NULL
	if (fHandle == NULL){
		return RC_FILE_HANDLE_NOT_INIT;
	}

	FILE *fp = fHandle->mgmtInfo;

	//the file pointer is NULL if the file handled by the handler not initialized
	if (fp == NULL){
		return RC_FILE_NOT_INITIALIZED;
	}

	//get the number of pages to be added
	int pageDifference = numberOfPages - fHandle->totalNumPages;

	//do appendEmptyBlock for pageDifference times
	for (int i=1; i <= pageDifference; i++) {
		rc = appendEmptyBlock(fHandle);

		if (rc != RC_OK) {
			return rc;
		}
	}

	return rc;
}
