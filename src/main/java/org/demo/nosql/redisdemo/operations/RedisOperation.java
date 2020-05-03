package org.demo.nosql.redisdemo.operations;

import org.demo.nosql.redisdemo.domain.RedisRequest;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.demo.nosql.redisdemo.storage.Store;

public interface RedisOperation {

    /**
     * After reading a bit about redis, apparently there are many options for database setup. Master, Master-Slave,
     * Clustered Setup (sharding), Clustered Setup Master-Slave. https://dzone .com/articles/multi-tenancy-in-redis-enterprise.
     * The decision taken is that RedisOperation should be agnostic of the logical instance where the data is fetched.
     * Thats the reason why the DB reference is passed as argument.
     *
     * @param db      reference to the database.
     * @param request Redis command
     * @return response.
     */
    RedisResponse execute(Store db, RedisRequest request);
}
