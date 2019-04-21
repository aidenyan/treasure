package com.aiden.base;

import java.util.List;

/**
 * Created by Administrator on 2019/4/16/016.
 */

public class Page<T,E> {

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

    private E data;
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
        } else if (currentPage > getTotalPage()) {
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

    public <V,G> Page<V,G> convert(List<V> list,G g) {
        Page<V,G> page = new Page<>();
        page.setCurrentPage(currentPage);
        page.setTotal(total);
        page.setPageSize(pageSize);
        page.setTotalPage(totalPage);
        page.setResult(list);
        page.setData(g);
        return page;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
