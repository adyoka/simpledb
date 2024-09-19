# SimpleDB

SimpleDB is a minimalist, in-memory relational database system writte in Java.
This project involves implementing core features of a database, from storage management to query execution and transactions. 

This project is an implementation as part of NYU Database Systems (CS-UH 2214) course, which follows MIT's 6.830: Database Systems.
The goal was to deepen understanding of database internals by building a functioning system from the ground up.

## Features

### 1. Storage Management
- **Page and Record Storage**: Implemented page structures to store and retrieve records. Pages can be written to and read from disk as part of a file-backed database system.
- **Heap Files**: Designed a heap file structure for managing collections of pages, allowing for efficient access and storage of database tuples.

### 2. Buffer Pool Management
- **Buffer Pool**: Implemented a buffer pool to cache pages in memory for efficient access, managing limited memory resources.
- **Page Replacement Policy**: Added a Least Recently Used (LRU) page replacement policy to handle cases when the buffer pool exceeds its memory limit.

### 3. Query Execution
- **Iterators**: Implemented an iterator-based query execution engine to process select, join, and aggregate operations.
- **Operators**:
  - **Sequential Scan**: Basic table scanning functionality.
  - **Join Operator**: Support for different join algorithms, including nested-loop joins.
  - **Aggregation**: Implemented support for simple aggregate functions (SUM, AVG, MIN, MAX).

### 4. Transactions
- **Transaction Management**: Built a framework for handling concurrent transactions, ensuring ACID properties.
- **Locking**: Implemented two-phase locking to control concurrent access to shared resources.
- **Logging and Recovery**: Added write-ahead logging (WAL) for crash recovery, ensuring that committed transactions can be recovered after system failure.


## Getting Started

### Prerequisites
- **Java Development Kit (JDK)**: This project is written in Java, so you'll need JDK 8 or above.
- **Ant build tool**: to compile the code and run tests, the build is written in XML.
  
### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/adyoka/simpledb.git
   cd simpledb
   ```
2. Build project:
    ```bash
    ant
    ```
3. To run unit tests:
    ```bash
    ant test
    ```
4. To run system tests:
    ```bash
    ant systemtest
    ```

To load a schema file and start the interpreter, use the following command:
    ```bash
    java -jar dist/simpledb.jar parser dblp_data/dblp_simpledb.schema
    ```

