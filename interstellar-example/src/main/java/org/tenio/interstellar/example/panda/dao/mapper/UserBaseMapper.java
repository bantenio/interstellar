package org.tenio.interstellar.example.panda.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.tenio.interstellar.example.panda.model.UserBase;
import org.tenio.interstellar.example.panda.model.UserBaseExample;

import java.util.List;

public interface UserBaseMapper {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_base
     *
     */
    int countByExample(UserBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_base
     *
     */
    int deleteByExample(UserBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_base
     *
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_base
     *
     */
    int insert(UserBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_base
     *
     */
    int insertSelective(UserBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_base
     *
     */
    List<UserBase> selectByExample(UserBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_base
     *
     */
    UserBase selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_base
     *
     */
    int updateByExampleSelective(@Param("record") UserBase record, @Param("example") UserBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_base
     *
     */
    int updateByExample(@Param("record") UserBase record, @Param("example") UserBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_base
     *
     */
    int updateByPrimaryKeySelective(UserBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_base
     *
     */
    int updateByPrimaryKey(UserBase record);
}