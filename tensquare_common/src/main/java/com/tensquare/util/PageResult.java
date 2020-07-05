package com.tensquare.util;

import lombok.Data;

import java.util.List;

/**
 * 分页显示的结果
 * @param <T>
 */
@Data
public class PageResult<T> {
    private long total;   // 总条数
    private List<T> rows; // 所有结果

    public PageResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
}
