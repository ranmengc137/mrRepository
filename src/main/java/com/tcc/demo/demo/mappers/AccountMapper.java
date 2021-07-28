package com.tcc.demo.demo.mappers;

import com.tcc.demo.demo.annotation.DS;
import com.tcc.demo.demo.entities.Account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@DS("account")
public interface AccountMapper {
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
    int insert(Account record);

    /**
    * insertSelective
    * @param record record
    * @return int int
    */
    int insertSelective(Account record);

    /**
    * selectByPrimaryKey
    * @param id id
    * @return Account Account
    */
    Account selectByPrimaryKey(Long id);

    /**
    * updateByPrimaryKeySelective
    * @param record record
    * @return int int
    */
    int updateByPrimaryKeySelective(Account record);

    /**
    * updateByPrimaryKey
    * @param record record
    * @return int int
    */
    int updateByPrimaryKey(Account record);


    int deductAmount(@Param("account_name") String accountName, @Param("amountLeft")int amountLeft);
}