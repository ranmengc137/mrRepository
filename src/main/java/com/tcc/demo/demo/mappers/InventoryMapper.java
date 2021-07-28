package com.tcc.demo.demo.mappers;

import com.tcc.demo.demo.annotation.DS;
import com.tcc.demo.demo.entities.Inventory;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryMapper {
    /**
    * deleteByPrimaryKey
    * @param id id
    * @return int int
    */
    int deleteByPrimaryKey(Long id);

    /**
    * insert
    * @param record record
    * @return int int
    */
    int insert(Inventory record);

    /**
    * insertSelective
    * @param record record
    * @return int int
    */
    int insertSelective(Inventory record);

    /**
    * selectByPrimaryKey
    * @param id id
    * @return Inventory Inventory
    */
    Inventory selectByPrimaryKey(Long id);

    /**
    * updateByPrimaryKeySelective
    * @param record record
    * @return int int
    */
    int updateByPrimaryKeySelective(Inventory record);

    /**
    * updateByPrimaryKey
    * @param record record
    * @return int int
    */
    int updateByPrimaryKey(Inventory record);
}