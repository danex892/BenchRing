package com.benchring.contacts;

import java.util.List;

public interface ContactRepositoryCustom {

    /**
     * Both filters are optional. {@code limit} and {@code offset} map straight onto
     * SQL LIMIT/OFFSET so the semantics match the other implementations in the benchmark.
     */
    List<Contact> findByFilters(Integer externalId, String phoneNumber, int limit, int offset);
}
