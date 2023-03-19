package org.tenio.interstellar.example.panda.dao;

import org.tenio.interstellar.dao.mybatis.DaoMapper;
import org.tenio.interstellar.example.panda.dao.mapper.UserBaseMapper;
import org.tenio.interstellar.example.panda.model.UserBase;

public interface UserBaseDao extends DaoMapper<UserBase>, UserBaseMapper {
}
