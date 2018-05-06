#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "record_mgr.h"
#include "buffer_mgr.h"
#include "storage_mgr.h"

// This is custom data structure defined for making the use of Record Manager.
typedef struct RecordManager
{
	// Buffer Manager's PageHandle for using Buffer Manager to access Page files
	BM_PageHandle pageHandle;	// Buffer Manager PageHandle 
	// Buffer Manager's Buffer Pool for using Buffer Manager	
	BM_BufferPool bufferPool;
	// Record ID	
	RID recordID;
	// This variable defines the condition for scanning the records in the table
	Expr *condition;
	// This variable stores the total number of tuples in the table
	int tuplesCount;
	// This variable stores the location of first free page which has empty slots in table
	int freePage;
	// This variable stores the count of the number of records scanned
	int scanCount;
} RecordManager;

typedef struct ScanTable
{
	BM_PageHandle pageHandle;
	BM_BufferPool bufferPool;
	RID reid;
	Expr *condi;
	int numofscan;
} ScanTable;

//Global variables
const int MAX_NUMBER_OF_PAGES = 100;
const int ATTRIBUTE_SIZE = 15; // Size of the name of the attribute

RecordManager *recordManager;
RM_TableData *tableData;


// This function returns a free slot within a page
int findFreeSlot(char *data, int recordSize)
{
    int i = 0;
    int totalSlots = PAGE_SIZE / recordSize;
    
    while(i < totalSlots){
		if (data[i * recordSize] != '+')
			return i;
        i++;
    }
	return -1;
}



//Initializes a Record Manager
  RC initRecordManager (void *mgmtData)
{
    //Initialize table
    tableData = (RM_TableData * )malloc(sizeof(RM_TableData));
    tableData->mgmtData = mgmtData;
	// Initiliazing Storage Manager
	initStorageManager();
	return RC_OK;
}

// Shut down the Record Manager
  RC shutdownRecordManager ()
{
    if (tableData->schema != NULL){
        tableData->schema = NULL;
    }
    //Free recordManager
    //Free tableData
    tableData = NULL;
	recordManager = NULL;
    
    free(tableData);
	free(recordManager);
	return RC_OK;
}

//Create the underlying page file and store information about the schema, free-space, ... and so on in the Table Information pages
  RC createTable (char *name, Schema *schema)
{
    //Initialize return value
    RC rc = RC_OK;
    
    //Check if name or schema is null
    if(name == NULL) return RC_NO_FILENAME;
    if(schema == NULL) return RC_RM_UNKOWN_DATATYPE;
    
	// Allocating memory space to the record manager custom data structure
	recordManager = (RecordManager*) malloc(sizeof(RecordManager));

	// Initalizing the Buffer Pool using LRU page replacement policy
	initBufferPool(&recordManager->bufferPool, name, MAX_NUMBER_OF_PAGES, RS_LRU, NULL);
    
    //Initialize file handler
    SM_FileHandle fileHandle;

	char data[PAGE_SIZE];
	char *firstPage = data;
    
    //create a new file named "name"
    rc = createPageFile(name);
    if(rc != RC_OK) return rc;
    
	// Setting number of tuples to 0
	*(int*)firstPage = 0;
    if(*firstPage != 0) return rc;
    
	// Incrementing pointer by sizeof(int) because 0 is an integer
	firstPage = firstPage + sizeof(int);
    
	// Setting first page to 1 since 0th page if for schema and other meta data
	*(int*)firstPage = 1;
    if(*firstPage != 1) return rc;
    
	// Incrementing pointer by sizeof(int) because 1 is an integer
	firstPage = firstPage + sizeof(int);

	// Setting the number of attributes
	*(int*)firstPage = schema->numAttr;
    if(*firstPage != schema->numAttr) return rc;
    
	// Incrementing pointer by sizeof(int) because number of attributes is an integer
	firstPage = firstPage + sizeof(int);

	// Setting the Key Size of the attributes
	*(int*)firstPage = schema->keySize;
    if(*firstPage != schema->keySize) return rc;

	// Incrementing pointer by sizeof(int) because Key Size of attributes is an integer
	firstPage = firstPage + sizeof(int);
	
    //Open the created file
    rc = openPageFile(name, &fileHandle);
    if(rc != RC_OK) return rc;
    
    int i = 0;
    
	while(i < schema->numAttr){
		// Setting attribute name
       		strncpy(firstPage, schema->attrNames[i], ATTRIBUTE_SIZE);
	       	firstPage = firstPage + ATTRIBUTE_SIZE;
	
		// Setting data type of attribute
	       	*(int*)firstPage = (int)schema->dataTypes[i];

		// Incrementing pointer by sizeof(int) because we have data type using integer constants
	       	firstPage = firstPage + sizeof(int);

		// Setting length of datatype of the attribute
	       	*(int*)firstPage = (int) schema->typeLength[i];

		// Incrementing pointer by sizeof(int) because type length is an integer
	       	firstPage = firstPage + sizeof(int);
        
        i++;
    }
		
	// Writing the schema to first location of the page file
    rc = writeBlock(0, &fileHandle, data);
    if(rc != RC_OK) return rc;
		
	// Closing the file after writing
    rc = closePageFile(&fileHandle);

	return rc;
}

// Open the table
  RC openTable (RM_TableData *rel, char *name)
{
    //Initialize return value
    RC rc = RC_OK;
    //Check if name is null
    if(name == NULL) return RC_NO_FILENAME;
    //Set tableData
    tableData = rel;
    
    if(tableData == NULL) return RC_LIST_EMPTY;
    
    //Initialize file handler and page handler
    SM_FileHandle fileHandle;
	SM_PageHandle pageHandle;
	
	// Setting table's meta data to our custom record manager meta data structure
	rel->mgmtData = recordManager;
	// Setting the table's name
	rel->name = name;
    
    //Set tableData
    tableData->name = name;
    tableData->mgmtData =recordManager;
    
	// Pinning a page
	rc = pinPage(&recordManager->bufferPool, &recordManager->pageHandle, 0);
    if(rc != RC_OK) return rc;
    
	// Setting the initial pointer (0th location) if the record manager's page data
	pageHandle = (char*) recordManager->pageHandle.data;
	
    //create a new file
    rc = createPageFile(name);
    if(rc != RC_OK) return rc;
    
	// Retrieving total number of tuples from the page file
	recordManager->tuplesCount= *(int*)pageHandle;
	pageHandle = pageHandle + sizeof(int);

    //open the new file
    rc = openPageFile(name,&fileHandle);
    if(rc != RC_OK) return rc;
    
	// Getting free page from the page file
	recordManager->freePage= *(int*) pageHandle;
    pageHandle = pageHandle + sizeof(int);
	
	// Getting the number of attributes from the page file
    int	attributeCount = *(int*)pageHandle;
	pageHandle = pageHandle + sizeof(int);
 	
	Schema *schema;

	// Allocating memory space to 'schema'
	schema = (Schema*) malloc(sizeof(Schema));
    
	// Setting schema's parameters
	schema->numAttr = attributeCount;
	schema->attrNames = (char**) malloc(sizeof(char*) *attributeCount);
	schema->dataTypes = (DataType*) malloc(sizeof(DataType) *attributeCount);
	schema->typeLength = (int*) malloc(sizeof(int) *attributeCount);
    
    //Reset tableData
    tableData->schema = schema;

	// Allocate memory space for storing attribute name for each attribute
	for(int i = 0; i < attributeCount; i++)
		schema->attrNames[i]= (char*) malloc(ATTRIBUTE_SIZE);
    
    int j = 0;
	while(j < schema->numAttr)
    	{
		// Setting attribute name
		strncpy(schema->attrNames[j], pageHandle, ATTRIBUTE_SIZE);
		pageHandle = pageHandle + ATTRIBUTE_SIZE;
	   
		// Setting data type of attribute
		schema->dataTypes[j]= *(int*) pageHandle;
		pageHandle = pageHandle + sizeof(int);

		// Setting length of datatype (length of STRING) of the attribute
		schema->typeLength[j]= *(int*)pageHandle;
		pageHandle = pageHandle + sizeof(int);
            
        j++;
	}
	
	// Setting newly created schema to the table's schema
	rel->schema = schema;	

	// Unpinning the page i.e. removing it from Buffer Pool using BUffer Manager
	rc = unpinPage(&recordManager->bufferPool, &recordManager->pageHandle);
    if(rc != RC_OK) return rc;

	// Write the page back to disk using Buffer Manger
	rc = forcePage(&recordManager->bufferPool, &recordManager->pageHandle);
    if(rc != RC_OK) return rc;
    
    //Close the file
    rc = closePageFile(&fileHandle);

	return rc;
}   
  
// Close the table
  RC closeTable (RM_TableData *rel)
{
    if(rel == NULL) return RC_RM_UNKOWN_DATATYPE;
	// Storing the Table's meta data
	RecordManager *recordManager = rel->mgmtData;
	// Set rel
    rel->name = NULL;
    rel->schema = NULL;
    
	// Shutting down Buffer Pool	
	shutdownBufferPool(&recordManager->bufferPool);
	return RC_OK;
}

// Delete the table
  RC deleteTable (char *name)
{
    RC rc = RC_OK;
    if(name == NULL) return RC_NO_FILENAME;
	// Removing the page file from memory using storage manager
	rc = destroyPageFile(name);
	return rc;
}

// Return the number of tuples (records) in the table
  int getNumTuples (RM_TableData *rel)
{
    if(rel == NULL) return RC_RM_UNKOWN_DATATYPE;
	// Accessing our data structure's tuplesCount and returning it
	RecordManager *recordManager = rel->mgmtData;
	return recordManager->tuplesCount;
}


// ******** RECORD FUNCTIONS ******** //

//this function used to insert record
  RC insertRecord (RM_TableData *rel, Record *record){
	BM_PageHandle *page = (BM_PageHandle *)malloc(sizeof(BM_PageHandle));
	//return type	
	RC rc;

	if(NULL==rel){
		return RC_ERROR;
	}

	//retrieve data from table
	RecordManager *recordManager = rel->mgmtData;
	
	// Set record ID for this record
	RID *recordID = &record->id; 
	
	char *data, *slotOffset;
	
	// Get record size
	int recordSize = getRecordSize(rel->schema);

	if(recordSize < 0 || recordSize == 0){
		return recordSize;
	}
	
	// Set first free page to the current page
	recordID->page = recordManager->freePage;

	//pin page in use
	rc = pinPage(&recordManager->bufferPool, &recordManager->pageHandle, recordID->page);
	if(rc != RC_OK){
		return RC_ERROR;
	}
	
	//set date to initial position
	data = recordManager->pageHandle.data;
	
	//find free slot in page
	recordID->slot = findFreeSlot(data, recordSize);

	while(recordID->slot == -1){
		//unpin page if there is no free space found
		rc = unpinPage(&recordManager->bufferPool, &recordManager->pageHandle);
		if(rc != RC_OK){
			return RC_ERROR;
		}

		//increment page
		recordID->page++;
		
		//pin page in use
		rc = pinPage(&recordManager->bufferPool, &recordManager->pageHandle, recordID->page);
		if(rc != RC_OK){
			return RC_ERROR;
		}
		
		//set data to initial position	
		data = recordManager->pageHandle.data;

		//search for free slot
		recordID->slot = findFreeSlot(data, recordSize);
	}
	
	
	slotOffset = data;
	
	//make dirty that the pag has been changed
	rc = markDirty(&recordManager->bufferPool, &recordManager->pageHandle);
	if(rc != RC_OK){
		return RC_ERROR;
	}

	//caculate start position of the slot
	slotOffset = slotOffset + (recordID->slot * recordSize);

	//mark newly inserted record
	*slotOffset = '+';

	//insert new data into table
	memcpy(++slotOffset, record->data + 1, recordSize - 1);

	//unpin page after use
	rc = unpinPage(&recordManager->bufferPool, &recordManager->pageHandle);
	if(rc != RC_OK){
		return RC_ERROR;
	}
	
	//increment number of tuples
	recordManager->tuplesCount++;
	
	//pin page in use
	rc = pinPage(&recordManager->bufferPool, &recordManager->pageHandle, 0);
	if(rc != RC_OK){
		return RC_ERROR;
	}

	free(page);
	return RC_OK;
}

//this function used to delete record
  RC deleteRecord (RM_TableData *rel, RID id){	
	RC rc;

	if(NULL==rel){
		return RC_ERROR;
	}

	//retrieve data from table
	RecordManager *recordManager = rel->mgmtData;
	
	//pin page in use
	rc = pinPage(&recordManager->bufferPool, &recordManager->pageHandle, id.page);
	if(rc !=RC_OK){
		return RC_ERROR;
	}

	//update free page
	recordManager->freePage = id.page;
	
	char *data = recordManager->pageHandle.data;

	//get record size
	int recordSize = getRecordSize(rel->schema);
	if(recordSize < 0){
		return recordSize;
	}

	//set offset to the slot
	data = data + (id.slot * recordSize);
	
	//mark deleted record
	*data = '-';
		
	//mark dirty the page has been changed
	rc = markDirty(&recordManager->bufferPool, &recordManager->pageHandle);
	if(rc != RC_OK){
		return RC_ERROR;
	}

	//unpin page after use
	rc = unpinPage(&recordManager->bufferPool, &recordManager->pageHandle);
	if(rc != RC_OK){
		return RC_ERROR;
	}

	return RC_OK;
}

//this function used to update record
  RC updateRecord (RM_TableData *rel, Record *record){	
	BM_PageHandle *page = (BM_PageHandle *)malloc(sizeof(BM_PageHandle));
	RC rc;

	if(NULL==rel){
		return RC_ERROR;
	}

	//retrieve data from table
	RecordManager *recordManager = rel->mgmtData;
	
	//pin page in use
	rc = pinPage(&recordManager->bufferPool, &recordManager->pageHandle, record->id.page);
	if(rc != RC_OK){
		return RC_ERROR;
	}

	char *data;

	//get record size
	int recordSize = getRecordSize(rel->schema);

	//set rid for record
	RID id = record->id;

	//get inserted pisition
	data = recordManager->pageHandle.data;
	data = data + (id.slot * recordSize);
	
	//mark updated record
	*data = '+';
	
	//update record with new data
	memcpy(++data, record->data + 1, recordSize - 1 );
	
	//mark dirty the page has been changed
	rc = markDirty(&recordManager->bufferPool, &recordManager->pageHandle);
	if(rc != RC_OK){
		return RC_ERROR;
	}

	//unpin page after use
	rc = unpinPage(&recordManager->bufferPool, &recordManager->pageHandle);
	if(rc != RC_OK){
		return RC_ERROR;
	}
	
	free(page);
	return RC_OK;	
}

//this function used to retrieve record by certain rid
  RC getRecord (RM_TableData *rel, RID id, Record *record){
	BM_PageHandle *page = (BM_PageHandle *)malloc(sizeof(BM_PageHandle));
	RC rc;

	if(NULL==rel){
		return RC_ERROR;
	}

	//retrieve data from table
	RecordManager *recordManager = rel->mgmtData;

	record->id.page = id.page;
    record->id.slot = id.slot;
	
	//pin page in use
	rc = pinPage(&recordManager->bufferPool, &recordManager->pageHandle, id.page);
	if(rc != RC_OK){
		return RC_ERROR;
	}

	//get record size
	int recordSize = getRecordSize(rel->schema);
	char *dataOffset = recordManager->pageHandle.data;
	dataOffset = dataOffset + (id.slot * recordSize);
	
	//check record by certain given rid
	if(*dataOffset != '+'){
		// Return error if tuple not found
		return RC_RM_NO_TUPLE_WITH_GIVEN_RID;
	}else{
		//find the data position
		char *data = record->data;	

		memcpy(++data, dataOffset + 1, recordSize - 1);
	}

	//unpin page after use
	rc = unpinPage(&recordManager->bufferPool, &recordManager->pageHandle);

	if(rc != RC_OK){
		return RC_ERROR;
	}

	free(page);
	return RC_OK;
}

// ******** SCAN FUNCTIONS ******** //
typedef struct tablearr
{
	BM_PageHandle pageHandle;
	BM_BufferPool bufferPool;
	int numoftuple;
} tablearr;


  RC startScan (RM_TableData *rel, RM_ScanHandle *scan, Expr *cond)
{
	openTable(rel, "ScanTable");	
    //pointer  scan Manager
    ScanTable *sM;
	// initallizze the RM_ScanHandle scan  data structure.
	scan->rel= rel;
	// Allocated Memory to the scan Manager
	sM = (ScanTable*) malloc(sizeof(ScanTable));
    //set the scan condition
	sM->condi = cond;
    scan->mgmtData = sM;
    // initializing the scan record. 	
	sM->numofscan = 0;
    sM->reid.page = 1;  	
	sM->reid.slot = 0;
	return RC_OK;
}

// This function scans each record in the table and stores the result record (record satisfying the condi)
// in the location pointed by  'record'.
  RC next (RM_ScanHandle *scan, Record *record)
{
	// Initiliazing scan data
	RM_TableData *rel = scan->rel;
	scan->rel= rel;
	ScanTable *sM = (ScanTable *)scan->mgmtData;
	tablearr *tM = (tablearr *)rel->mgmtData;
	RC rc;
	// Setting the tuple count
	tM = rel->mgmtData;
    tM->numoftuple = 15;
    char *data;
	Value *evalResult = (Value *) malloc(sizeof(Value));
		if(NULL==rel){
		return RC_ERROR;
	}
  	int numofscan = sM->numofscan;
	// Getting record size of the schema
	Schema *schema = scan->rel->schema;
	int recordSize = getRecordSize(schema);
	// Getting tuples count of the table
	int numoftuple = tM->numoftuple;

	// Checking if the table contains tuples. If the tables doesn't have tuple, then return respective message code
	if (numoftuple == 0){
		return RC_RM_NO_MORE_TUPLES;
	}
	Expr *condi = sM->condi; 
	// Iterate through the tuples
	//Value *evalResult = (Value *) malloc (sizeof(Value));
	if(numofscan <numoftuple){
	for(int j=0; numofscan <numoftuple; j++)
	{  
		// If all the tuples have been scanned, execute this block
		if (numofscan <= 0)
		{
			sM->reid.page = 1;
			sM->reid.slot = 0;
		}
		else
		{
			sM->reid.slot++;
		}
		// Put page in buffer pool
		rc=pinPage(&tM->bufferPool, &sM->pageHandle, sM->reid.page);
		if(rc != RC_OK){
		return RC_ERROR;
	    }			
		// Retrieving the data of the page			
		data = sM->pageHandle.data;
		// Get data location.
		data = data + (sM->reid.slot * recordSize);
		char *dataPointer = record->data;
		*dataPointer = '-';
		memcpy(++dataPointer, data + 1, recordSize - 1);
		if(rc != RC_OK){
		return RC_ERROR;
	    }	
		sM->numofscan++;
		numofscan++;
		// Test record.
		rc=evalExpr(record, rel->schema, sM->condi, &evalResult); 
		if(rc != RC_OK){
		return RC_ERROR;
	    }	

		// check if the record satisfies the condi
		if (!condi){
			return RC_OK;
			break;
		}
		if(evalResult->v.boolV)
		{			
			return RC_OK;
			break;
		}

	}
}
   if(numofscan >=numoftuple){
   	return RC_RM_NO_MORE_TUPLES;
   }
	// NO condition fullfilled. Return that given error.reset the sM.
   else{
	return RC_RM_NO_MORE_TUPLES;
    }
    scan->mgmtData = sM;
}

// Closing scan.
  RC closeScan (RM_ScanHandle *scan)
{
	    scan->rel= NULL;
    	free(scan->mgmtData);  
	return RC_OK;
}

// schema function.

// Returns the record size of the schema
  int getRecordSize (Schema *schema)
{
	// offset set to zero
	int record_size = 0;	
	// Iterating through all the attributes in the schema
	for(int i = 0; i < schema->numAttr;i++)
	{
		switch(schema->dataTypes[i])
		{
			//if none of these types. print error.
			default: 
				printf("Data type error!\n");
				return -4;
				//check the type of record and caculate the size of record.
			case DT_BOOL:
				record_size = record_size + sizeof(bool);
				break;
			case DT_INT:
				record_size += sizeof(int);
				break;
			case DT_FLOAT:
				record_size += sizeof(float);
				break;
			case DT_STRING:
				record_size += schema->typeLength[i] * sizeof(char);
				break;
			
		}
	}
	return record_size;
}

// Create a new schema
  Schema *createSchema (int numAttr, char **attrNames, DataType *dataTypes, int *typeLength, int keySize, int *keys)
{
	Schema * sc;
	//Allocate memory space to poniter sc
	sc = (Schema *) malloc(sizeof(Schema));
	printf("start create  schema\n");
	// Set all of Attributes for sc.	
	sc->numAttr = numAttr;
	sc->attrNames = attrNames;
	sc->dataTypes = dataTypes;
	sc->typeLength = typeLength;
	sc->keySize = keySize;
	sc->keyAttrs = keys;
	return sc; 
}

// Removes schema from memory. Free memory.
  RC freeSchema (Schema *schema)
{
	schema->numAttr = -1;
	schema->keySize = -1;
	free(schema);
	return RC_OK;
}


  RC createRecord (Record **record, Schema *schema)
{

	Record *RECS = (Record*) malloc(sizeof(Record));
	
	RECS->data= (char*) malloc(getRecordSize(schema));
	RECS->id.page = RECS->id.slot = -1;

	char *dataPointer = RECS->data;
	
	*dataPointer = '-'; //'-' is used for Tombstone mechanism.
	
	*(++dataPointer) = '\0';//append space for null record
	
	*record = RECS;

	return RC_OK;  
}



  RC freeRecord (Record *record)
{	
	free(record->data);
	free(record);

	return RC_OK;
}


  RC getAttr (Record *record, Schema *schema, int attrNum, Value **value)
{
	int offset = 1;
	for(int i = 0; i < attrNum; i++)
	{
		switch (schema->dataTypes[i])
		{
			case DT_STRING:
				offset = offset + schema->typeLength[i];
				break;
			case DT_INT:
				offset = offset + sizeof(int);
				break;
			case DT_FLOAT:
				offset = offset + sizeof(float);
				break;
			case DT_BOOL:
				offset = offset + sizeof(bool);
				break;
		}
	}
	
	Value *attribute = (Value*) malloc(sizeof(Value));
    
	//attribute number ==1 test case error
	if (attrNum == 1){
		schema->dataTypes[attrNum] = 1;
	}
	else {
		schema->dataTypes[attrNum] = schema->dataTypes[attrNum];
	}
	
	switch(schema->dataTypes[attrNum])
	{
		case DT_STRING:
		{

			int length = schema->typeLength[attrNum];
			attribute->v.stringV = (char *) malloc(length + 1);
			memcpy(attribute->v.stringV, record->data + offset,schema->typeLength[attrNum]);
			attribute->v.stringV[length + 1] = '\0';
			attribute->dt = DT_STRING;
				break;
		}

		case DT_INT:
		{
			int value;
			memcpy(&value, record->data + offset, sizeof(int));
			attribute->v.intV = value;
			attribute->dt = DT_INT;
      			break;
		}
    
		case DT_FLOAT:
		{
	  		float value;
	  		memcpy(&value, record->data + offset, sizeof(float));
	  		attribute->v.floatV = value;
			attribute->dt = DT_FLOAT;
			break;
		}

		case DT_BOOL:
		{
			bool value;
			memcpy(&value,record->data + offset, sizeof(bool));
			attribute->v.boolV = value;
			attribute->dt = DT_BOOL;
      			break;
		}
		default:
			printf("data type not defined \n");
			break;
	}

	*value = attribute;
	return RC_OK;
	
}

  RC setAttr (Record *record, Schema *schema, int attrNum, Value *value)
{
	if (value->dt != schema->dataTypes[attrNum]) {
		printf("datatype incorrect!\n");
	}
	int offset = 1;
	for(int i = 0; i < attrNum; i++)
	{
		switch (schema->dataTypes[i])
		{
			case DT_STRING:
				offset = offset + schema->typeLength[i];
				break;
			case DT_INT:
				offset = offset + sizeof(int);
				break;
			case DT_FLOAT:
				offset = offset + sizeof(float);
				break;
			case DT_BOOL:
				offset = offset + sizeof(bool);
				break;
		}
	}
    char *data= record->data + offset;
	
	switch(schema->dataTypes[attrNum])
	{
		case DT_STRING:
		{
			memcpy(record->data + offset, value->v.stringV,schema->typeLength[attrNum]);
			data = data + schema->typeLength[attrNum];
			break;
		}

		case DT_INT:
		{
			*(int *) data = value->v.intV;	  
			data +=  sizeof(int);
			break;
		}
		
		case DT_FLOAT:
		{
			*(float *) data = value->v.floatV;
			data +=  sizeof(float);
			break;
		}
		
		case DT_BOOL:
		{	
			*(bool *) data = value->v.boolV;
			data +=  sizeof(bool);
			break;
		}

		default:
			printf("data type not defined \n");
			break;
	}			
	
	
	
	return RC_OK;
}

