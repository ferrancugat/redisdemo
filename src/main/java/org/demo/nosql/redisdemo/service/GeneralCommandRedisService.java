package org.demo.nosql.redisdemo.service;

import java.util.Optional;

public interface GeneralCommandRedisService {

    /**
     *  Fetch size of Database
     *
     * @return
     */
    Optional<Long> dbSize();
}
