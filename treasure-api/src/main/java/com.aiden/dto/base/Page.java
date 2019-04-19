package com.aiden.dto.base;

import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2019/4/16/016.
 */
@Data
public class Page<T> {

    public final static Integer MAX_PAGE_SIZE = 1000;

    // 每页显示记录数
    private Integer pageSize = 20;

    // 当前页数
    private Integer currentPage = 1;

    /**
     * 总的数量
     */
    private Integer total;

    /**
     * 内容
     */
    private List<T> result;

    /**
     * 总的页数
     */
    private Integer totalPage;

    public Integer getPageSize() {
        if (pageSize == null || pageSize == 0) {
            pageSize = 20;
        } else if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
        return pageSize;
    }

    public Integer getCurrentPage() {
        if (currentPage == null || currentPage <= 0 || pageSize > MAX_PAGE_SIZE) {
            currentPage = 1;
        }else if (currentPage > getTotalPage()) {
            currentPage = getTotalPage();
        }
        return currentPage;
    }

    public Integer getTotalPage() {
        if (total == null || total == 0) {
            totalPage = 1;
        } else {
            Integer size = getPageSize();
            totalPage = total % size == 0 ? total / size : (total - total % size) / size + 1;
        }
        return totalPage;
    }

    public <E> Page<E> convert(List<E> list){
        Page<E> page=new Page<>();
        page.setCurrentPage(currentPage);
        page.setTotal(total);
        page.setPageSize(pageSize);
        page.setTotalPage(totalPage);
        page.setResult(list);
        return page;
    }

}
