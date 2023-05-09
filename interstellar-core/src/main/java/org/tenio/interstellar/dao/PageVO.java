package org.tenio.interstellar.dao;

import java.util.Collections;
import java.util.List;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 *
 * @param <T> TODO
 */
public class PageVO<T> {

    /**
     * 查询数据列表
     */
    private List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    private long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    private int size = 10;

    /**
     * 当前页
     */
    private int current = 1;

    /**
     * 分页构造函数
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public PageVO(int current, int size) {
        this.current = current;
        this.size = size;
    }

    /**
     * TODO
     *
     * @param current TODO
     * @param size    TODO
     * @param total   TODO
     */
    public PageVO(int current, int size, long total) {
        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.total = total;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public List<T> getRecords() {
        return records;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public int getCurrent() {
        return current;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public int getSize() {
        return size;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public long getTotal() {
        return total;
    }

    /**
     * TODO
     *
     * @param records TODO
     * @return TODO
     */
    public PageVO<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    /**
     * TODO
     *
     * @param total TODO
     * @return TODO
     */
    public PageVO<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    /**
     * TODO
     *
     * @param size TODO
     * @return TODO
     */
    public PageVO<T> setSize(int size) {
        this.size = size;
        return this;
    }

    /**
     * TODO
     *
     * @param current TODO
     * @return TODO
     */
    public PageVO<T> setCurrent(int current) {
        this.current = current;
        return this;
    }

    @Override
    public String toString() {
        return "PageVO{" +
                "records=" + records +
                ", total=" + total +
                ", size=" + size +
                ", current=" + current +
                '}';
    }
}
