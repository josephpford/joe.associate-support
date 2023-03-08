# Exercise: Install Apache Cassandra

1. Use Docker Compose to launch Cassandra.
1. Use `cqlsh` to connect to Cassandra and run CQL commands.

## Docker Compose

There is already a [docker-compose.yml](../server/docker-compose.yml) file in the `server` folder of this lesson.

This Compose file defines a single service called `my-cassandra` that uses the official Cassandra Docker image, maps ports `9042` and `9160` to the host machine, and mounts a local directory called `data` to the container's `/var/lib/cassandra` directory to persist the data.

```
cd associate-support/cassandra/server
docker compose up -d
```

## Connect using CQLSH

Watch the Cassandra startup logs

```
docker logs my-cassandra --follow
```

Continue watching the logs until you see this output, and then press CTRL+C to stop following the logs.

```
Starting listening for CQL clients on /0.0.0.0:9042 (unencrypted)...
Startup complete
```

Now you can access cqlsh using:

```
docker exec -it my-cassandra cqlsh
```

## Next 
[Cassandra Data Model](data-model.md)

[Back to Overview](../README.md)