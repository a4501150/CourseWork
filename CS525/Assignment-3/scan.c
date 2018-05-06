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
//Scan allrecords.
extern RC startScan (RM_TableData *rel, RM_ScanHandle *scan, Expr *cond)
{
	openTable(rel, "ScanTable");
    //pointer  scan Manager
    RecordManager *sM;
    //pointer table manager
	RecordManager *tM;
	// initallizze the RM_ScanHandle scan  data structure.
	scan->rel= rel;
	// Allocated Memory to the scan Manager
    sM = (RecordManager*) malloc(sizeof(RecordManager));
    	
	// Setting the scan's meta data to our meta data
    scan->mgmtData = sM;
    // initializing the scan record as 0.   	
	sM->scanCount = 0;
    	
	//scan from 1st page
    sM->recordID.page = 1;
    	
	// 0 to start scan from the first slot	
	sM->recordID.slot = 0;

	// Scan condition as Exor *cond
    sM->condition = cond;
    	
	// Setting the our meta data to the table's meta data
    tM = rel->mgmtData;

	// Setting the tuple count
    tM->tuplesCount = ATTRIBUTE_SIZE;

	return RC_OK;
}

// This function scans each record in the table and stores the result record (record satisfying the condition)
// in the location pointed by  'record'.
extern RC next (RM_ScanHandle *scan, Record *record)
{
	// Initiliazing scan data
	RecordManager *sM = scan->mgmtData;
	RecordManager *tM = scan->rel->mgmtData;
    	Schema *schema = scan->rel->schema;
	
	// Checking if scan condition (test expression) is present
	if (sM->condition == NULL)
	{
		return RC_SCAN_CONDITION_NOT_FOUND;
	}

	Value *result = (Value *) malloc(sizeof(Value));
   
	char *data;
   	
	// Getting record size of the schema
	int recordSize = getRecordSize(schema);

	// Calculating Total number of slots
	int totalSlots = PAGE_SIZE / recordSize;

	// Getting Scan Count
	int scanCount = sM->scanCount;

	// Getting tuples count of the table
	int tuplesCount = tM->tuplesCount;

	// Checking if the table contains tuples. If the tables doesn't have tuple, then return respective message code
	if (tuplesCount == 0)
		return RC_RM_NO_MORE_TUPLES;

	// Iterate through the tuples
	while(scanCount <= tuplesCount)
	{  
		// If all the tuples have been scanned, execute this block
		if (scanCount <= 0)
		{
			// printf("INSIDE If scanCount <= 0 \n");
			// Set PAGE and SLOT to first position
			sM->recordID.page = 1;
			sM->recordID.slot = 0;
		}
		else
		{
			// printf("INSIDE Else scanCount <= 0 \n");
			sM->recordID.slot++;

			// If all the slots have been scanned execute this block
			if(sM->recordID.slot >= totalSlots)
			{
				sM->recordID.slot = 0;
				sM->recordID.page++;
			}
		}

		// Pinning the page i.e. putting the page in buffer pool
		pinPage(&tM->bufferPool, &sM->pageHandle, sM->recordID.page);
			
		// Retrieving the data of the page			
		data = sM->pageHandle.data;

		// Calulate the data location from record's slot and record size
		data = data + (sM->recordID.slot * recordSize);
		
		// Set the record's slot and page to scan manager's slot and page
		record->id.page = sM->recordID.page;
		record->id.slot = sM->recordID.slot;

		// Intialize the record data's first location
		char *dataPointer = record->data;

		// '-' is used for Tombstone mechanism.
		*dataPointer = '-';
		
		memcpy(++dataPointer, data + 1, recordSize - 1);

		// Increment scan count because we have scanned one record
		sM->scanCount++;
		scanCount++;

		// Test the record for the specified condition (test expression)
		evalExpr(record, schema, sM->condition, &result); 

		// v.boolV is TRUE if the record satisfies the condition
		if(result->v.boolV == TRUE)
		{
			// Unpin the page i.e. remove it from the buffer pool.
			unpinPage(&tM->bufferPool, &sM->pageHandle);
			// Return SUCCESS			
			return RC_OK;
		}
	}
	
	// Unpin the page i.e. remove it from the buffer pool.
	unpinPage(&tM->bufferPool, &sM->pageHandle);
	
	// Reset the Scan Manager's values
	sM->recordID.page = 1;
	sM->recordID.slot = 0;
	sM->scanCount = 0;
	
	// None of the tuple satisfy the condition and there are no more tuples to scan
	return RC_RM_NO_MORE_TUPLES;
}

// Closing the scan.
extern RC closeScan (RM_ScanHandle *scan)
{
	//free scan and memory
	    scan->rel = NULL;
    	scan->mgmtData = NULL;
    	free(scan->mgmtData);  
	
	return RC_OK;
}


// SCHEMA FUNCTIONS

// Returns the record size of the schema
extern int getRecordSize (Schema *schema)
{
	// offset set to zero
	int record_size = 0;	
	// Iterating through all the attributes in the schema
	for(int i = 0; i < schema->numAttr; i++)
	{
		switch(schema->dataTypes[i])
		{
			//if none of these types. print error.
			default: 
				printf("Data type error!\n");
				return -4;
			case DT_BOOL:
				// If attribite is BOOLEAN, then add record_size of BOOLEAN
				record_size = record_size + sizeof(bool);
				break;
			case DT_INT:
				// If attribute is INTEGER, then add record_size of INT
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
extern Schema *createSchema (int numAttr, char **attrNames, DataType *dataTypes, int *typeLength, int keySize, int *keys)
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
extern RC freeSchema (Schema *schema)
{
	free(schema);
	return RC_OK;
}
