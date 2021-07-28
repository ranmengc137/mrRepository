package com.tcc.demo.demo.mappers;

import com.tcc.demo.demo.annotation.DS;
import com.tcc.demo.demo.entities.InventoryTcc;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryTccMapper {
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
    int insert(InventoryTcc record);

    /**
    * insertSelective
    * @param record record
    * @return int int
    */
    int insertSelective(InventoryTcc record);

    /**
    * selectByPrimaryKey
    * @param id id
    * @return InventoryTcc InventoryTcc
    */
    InventoryTcc selectByPrimaryKey(Long id);

    /**
    * updateByPrimaryKeySelective
    * @param record record
    * @return int int
    */
    int updateByPrimaryKeySelective(InventoryTcc record);

    /**
    * updateByPrimaryKey
    * @param record record
    * @return int int
    */
    int updateByPrimaryKey(InventoryTcc record);
}