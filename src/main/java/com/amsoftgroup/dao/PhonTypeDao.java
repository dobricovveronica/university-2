package com.amsoftgroup.dao;

import com.amsoftgroup.model.PhoneType;

import java.util.List;

public interface PhonTypeDao {

    List<PhoneType> findAll();

    void add(PhoneType phoneType);

    void update(PhoneType phoneType);

}
