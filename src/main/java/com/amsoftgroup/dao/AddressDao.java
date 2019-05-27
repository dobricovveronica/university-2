package com.amsoftgroup.dao;

import com.amsoftgroup.model.Address;

import java.util.List;

public interface AddressDao {

    List<Address> findAll();

    void add(Address address);

    void update(Address address);

}
