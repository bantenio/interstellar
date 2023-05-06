package org.tenio.interstellar.example.panda.dao;

import org.apache.ibatis.annotations.Mapper;
import org.tenio.interstellar.dao.mybatis.DataObjectDaoMapper;
import org.tenio.interstellar.example.panda.dao.mapper.UserBaseMapper;
import org.tenio.interstellar.example.panda.model.UserBase;

@Mapper
public interface UserBaseDataObjectDao extends DataObjectDaoMapper<UserBase>, UserBaseMapper {
}
