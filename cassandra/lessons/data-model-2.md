# Data Model Part 2

## Partitions

Apache Cassandra is a distributed database that stores data across a cluster of nodes. A partition key is used to partition data among the nodes. Cassandra partitions data over the storage nodes using a variant of consistent hashing for data distribution. Hashing is a technique used to map data with which given a key, a hash function generates a hash value (or simply a hash) that is stored in a hash table. A partition key is generated from the first field of a primary key. Data partitioned into hash tables using partition keys provides for rapid lookup. Fewer the partitions used for a query faster is the response time for the query.

## Primary Key, Partition Key, Clustering Key

Primary Key
  - uniquely identifies a single record
  - Partition Key (required) + Clustering Key (optional)

Partition Key (required)
  - determines the partition the record will be stored on
  - can be simple (1 column) or compound (>1 columns)

Clustering Key (optional)
  - determines the order the records will be stored within the partition
  - made up of zero or more columns from the record.

The choice of the primary key and partition key is important to distribute data evenly across the cluster. Keeping the number of partitions read for a query to a minimum is also important because different partitions could be located on different nodes and the coordinator would need to send a request to each node adding to the request overhead and latency. Even if the different partitions involved in a query are on the same node, fewer partitions make for a more efficient query.

Examples:

```
CREATE TABLE field_agent.security_clearance_by_id ( 
  id int,
  name text,
  PRIMARY KEY (id)
);
```

Partition Key = `id`
Clustering Key = none
Primary Key = `id`

```
CREATE TABLE solar_farm.solar_panels_by_section ( 
  section text,
  row int,
  column int,
  year int,
  material text,
  tracking boolean,
  PRIMARY KEY (section, row, column)
);
```

Partition Key = `section`
Clustering Key = `row, column`
Primary Key = Partition Key + Clustering Key => `section, row, column`

If you wish to order them differently at insertion time, you can specify a `WITH CLUSTERING ORDER BY` statement.

```
CREATE TABLE solar_farm.solar_panels_by_section_and_year ( 
  section text,
  row int,
  column int,
  year int,
  material text,
  tracking boolean,
  PRIMARY KEY ((section, year), row, column)
) WITH CLUSTERING ORDER BY (row DESC, column DESC);
```

Recall:
  - What key is used to determine where the record will be stored in the cluster? What can it be composed of?
  - What key is used to change the default ordering of how the records are stored within the partition? What can it be composed of?
  - What key is used to uniquely identify a record? What can it be composed of?

Example

As an example of partitioning, consider table farm_info_by_id in which id is the only field in the primary key. You need the id of the farm in order to retrieve the farm entity.

```
CREATE TABLE farm_by_id (
  id int,
  name text,
  address text,
  owner text,
  year_found int,
  primary key (id)
);
```

This table could be used to retrieve information about an individual farm. However, it should not be used to retrieve the list of all farms. For that, there should be a separate table:

```
CREATE TABLE farms_by_partition (
  partition_id int,
  farm_id_list text
  primary key (partition_id)
);
```

## Next
[Exercise: Data Modeling](data-model-exercise.md)

[Back to Overview](../README.md)