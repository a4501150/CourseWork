README.txt:
---------------------------------------------------------
****cs525_group16******
A20351954  Jiahui Hou 
A20317851  Jinyang Li
A20401033  Junrui Gong
A20380272  Ruoshan Chen	
A20308281  Zihan Niu
----------------------------------------------------------

(1) How to use the Makefile:
----------------------------------------------------------
1.open the terminal and cd to the address of the "assign3” where you put.

2.Type in "make clean" and click "enter". (make sure no other files except as the above files in the assign3)
If shows as:
"rm -f  test *.o *~ *.log *.bin"
Continue step 3.   Else: Check if address is correct.

3.Type in "make" or "make test", Click "enter".(run test case in test_assign3_1)
If shows as :
"gcc  -c test_assign3_1.c
gcc  -c dberror.c
gcc  -c expr.c
gcc  -c  record_mgr.c
gcc  -c rm_serializer.c
gcc  -c storage_mgr.c
gcc -c buffer_mgr.c
gcc  -c buffer_mgr_stat.c
gcc -o test test_assign3_1.o dberror.o expr.o record_mgr.o rm_serializer.o storage_mgr.o buffer_mgr.o -lm buffer_mgr_stat.o"
Then continue step 4. Else: repeat step 2.

4.Type in "make run" , Click "enter".(Output the testing result)

5. If you Wanna try again, start from step 2.
----------------------------------------------------------


(3) Functions Introductions:
----------------------------------------------------------
Assignment#3 Record Manager

**********Table and Record Manager Functions*************
----------------------------------------------------------
1. initRecordManager (void *mgmtData):
This function initializes a record manager. Parameter mgmtData is initialized to tableData->mgmtData and storage manager is initialized.
2. shutdownRecordManager ()
This function shuts down the record manager. All global variables are set NULL and freed.
3. createTable (char *name, Schema *schema):
This function creates the underlying page file and store information about the schema, free-space, ... and so on in the Table Information pages. Initialized a buffer manager first. An empty page is allocated and the information of schema is stored into the empty page. Then this page is written into the first page and file name is set to the parameter name.
4. openTable (RM_TableData *rel, char *name)
This function opens the table. MgmtData is set to the recordManager first. Then a new page is pinned and total number of tuples is retrieved. Next set the schema information and unpin the page.
5.closeTable (RM_TableData *rel)
This function closes the table. Set the recordManager to mgmtData of rel. Then call the shutdownBufferPool function.
6.deleteTable (char *name)
This function delete the table. Call destroyPageFile function.
7.int getNumTuples (RM_TableData *rel)
This function returns the number of tuples (records) in the table. The number of tuples stores in the recordManager.

****Record Functions**********
----------------------------------------------------------
1. Insert record function, this function used to insert new record into table, and when new record is inserted, the record manager assign an RID to it and inserted the new record. Retrieve data from table first, set an RID for this record, then set data inserted and the offset for the slot, set the free page for current in use and then pin it. Set data to initial point and then find free slot using a defined function, if there are no free page on this page, unpin it and find on another page. When free space found, mark dirty to it, copy new data into it and mark the newly inserted with mark “+”, unpin page after use and return OK.

2. Delete record function, this function used to delete a record from the table. Retrieve data from table first with given table rel, pin the page in use and update free page with id. Get record size and set offset to the slot. Delete the record, mark page dirty that the page has been changed, use “-” to mark this record has been deleted and finally unpin the page after use and return OK.

3. Update record function, this function used to update a record with new value. Retrieve data from table first with given table rel, pin the page in use, get to the record we want to update by RID, get inserted position in the table and then update/copy new value to the record position, finally, mark page dirty and unpin page after use and return OK.

4. Get record function, this function used to retrieve record from certain table with given RID. Retrieve data from table with given table rel, pin page in use, get record size and offset to the data in record. Check record by certain id given, find the position of the data and retrieve it, if the required record not found, then just return the function. Finally, unpin page after use and return OK.


*******scan functions***********
----------------------------------------------------------

The Scan related functions are used to get all tuples from a table. Starting a scan initializes the RM_ScanHandle data structure passed as an argument to startScan. The next method is made which returns the next tuple that fulfills the scan condition. If NULL is passed as a scan condition, it returns RC_SCAN_CONDITION_NOT_FOUND. next returns RC_RM_NO_MORE_TUPLES once the scan is completed and RC_OK.

startScan():
This function starts a scan, Allocated Memory to the scan Manager, and initializes the RM_ScanHandle data structure passed as an argument to startScan().

next():
This function scans each record in the table and stores the result record (record satisfying the condition) in the location pointed by  'record'.Checking if the table contains tuples. If the tables doesn't have tuple, then return respective message code. If NO condition fulfilled. Return that given error.reset the sM.

closeScan(): Free all schema and if success return RC_OK.


*********SCHEMA FUNCTIONS*************
----------------------------------------------------------

These functions are used to return the actual size of records for a given schema and then create a new schema. 

getRecordSize():
This function returns the size of a record in the specified schema. By checking the kinds of record, get the attributes of the schema. And calculate the size of record.

createSchema()
This create a new schema in memory. Allocated memory space for the objects and set attributes for the parameters.

freeSchema()
Free the schema from the memory. Reset all attributes. Free memory.

*******Attribute Functions**********
----------------------------------------------------------
These functions are used to get or set the attribute values of a record and create a new record for a given schema. Creating a new record should allocate enough memory to the data field to hold the binary representations for all attributes of this record as determined by the schema.

1.createRecord functions
    this function create a new record base on schema. first we allocate memory space for new record and data for record.
    then we set all record to -1 as init empty record. then we set data to "-" for tombstone as they are empty.
    append \0 for Null after tombstone

    
2.freerecord function
   free record , free memory space of record

3.get attribute 
   this function get attribute from record into schema
   the main point is we need we need retrieve attribute offset base on attribute number
   then we get attribute value base one type, in each type get the attribute. 
   the problem we face when coding is attribute lenghth and if attribute number equal to 1 will cause test fail in test case
   
4.set attribute
   this function set attribute from schema into record
   similar with last function but this time we do reverse way. by copy the attribute value into crosboding location point by record's data.
   again, in this function we need retrieve attribute offset base on attribute number either