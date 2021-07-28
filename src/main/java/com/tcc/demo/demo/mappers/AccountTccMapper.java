package com.tcc.demo.demo.mappers;

import com.tcc.demo.demo.annotation.DS;
import com.tcc.demo.demo.entities.AccountTcc;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTccMapper {
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
    int insert(AccountTcc record);

    /**
    * insertSelective
    * @param record record
    * @return int int
    */
    int insertSelective(AccountTcc record);

    /**
    * selectByPrimaryKey
    * @param id id
    * @return AccountTcc AccountTcc
    */
    AccountTcc selectByPrimaryKey(Long id);

    /**
    * updateByPrimaryKeySelective
    * @param record record
    * @return int int
    */
    int updateByPrimaryKeySelective(AccountTcc record);

    /**
    * updateByPrimaryKey
    * @param record record
    * @return int int
    */
    int updateByPrimaryKey(AccountTcc record);

    int freezeAmount(@Param("accountTcc") AccountTcc record);

    int updateStatusToCommit(@Param("trxId") String trxId);

    int updateStatusToCancel(@Param("trxId") String trxId);

    int unFreeze(@Param("trxId") String trxId);
}