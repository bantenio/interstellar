package org.tenio.interstellar.example.panda.dao.mapper;

import org.tenio.interstellar.dao.mybatis.GenericMapper;
import org.tenio.interstellar.example.panda.model.UserBase;
import org.tenio.interstellar.example.panda.model.UserBaseExample;

public interface UserBaseMapper extends GenericMapper<UserBase, UserBaseExample, Integer> {
}