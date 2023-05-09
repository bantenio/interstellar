package org.tenio.interstellar.dao.mybatis;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 *
 * @param <T> TODO
 * @param <E> TODO
 * @param <K> TODO
 */
public interface GenericMapper<T, E, K> {

    /**
     *
     * TODO
     *
     * @param example TODO
     * @return TODO
     */
    long countByExample(E example);

    /**
     *
     * TODO
     *
     * @param example TODO
     * @return TODO
     */
    int deleteByExample(E example);

    /**
     *
     * TODO
     *
     * @param id TODO
     * @return TODO
     */
    int deleteByPrimaryKey(K id);

    /**
     *
     * TODO
     *
     * @param record TODO
     * @return TODO
     */
    int insert(T record);

    /**
     *
     * TODO
     *
     * @param record TODO
     * @return TODO
     */
    int insertSelective(T record);

    /**
     *
     * TODO
     *
     * @param example TODO
     * @return TODO
     */
    List<T> selectByExample(E example);

    /**
     *
     * TODO
     *
     * @param id TODO
     * @return TODO
     */
    T selectByPrimaryKey(K id);

    /**
     *
     * TODO
     *
     * @param record TODO
     * @param example TODO
     * @return TODO
     */
    int updateByExampleSelective(@Param("record") T record, @Param("example") E example);

    /**
     *
     * TODO
     *
     * @param record TODO
     * @param example TODO
     * @return TODO
     */
    int updateByExample(@Param("record") T record, @Param("example") E example);

    /**
     *
     * TODO
     *
     * @param record TODO
     * @return TODO
     */
    int updateByPrimaryKeySelective(T record);

    /**
     *
     * TODO
     *
     * @param record TODO
     * @return TODO
     */
    int updateByPrimaryKey(T record);
}
