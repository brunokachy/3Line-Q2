package com.verifycard.core.service;

import java.util.Optional;

public interface CrudService<T, ID> {

    Optional<T> findById(ID id);

    T getRecordById(ID id) throws RuntimeException;

    T saveRecord(T record);
}
