package com.amsoftgroup.dao;

import com.amsoftgroup.model.Phone;

import java.util.List;

public interface PhoneDao {

    List<Phone> findAll();

    void add(Phone phone);

    void update(Phone phone);


}
