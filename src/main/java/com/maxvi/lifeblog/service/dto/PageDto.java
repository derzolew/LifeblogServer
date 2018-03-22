package com.maxvi.lifeblog.service.dto;

import java.util.List;

public class PageDto<T>
{
    private List<T> items;
    private long totalElements;
    private long totalPages;

    public PageDto() {}

    public PageDto(List<T> items, long totalElements, long totalPages)
    {
        this.items = items;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<T> getItems()
    {
        return items;
    }

    public void setItems(List<T> items)
    {
        this.items = items;
    }

    public long getTotalElements()
    {
        return totalElements;
    }

    public void setTotalElements(long totalElements)
    {
        this.totalElements = totalElements;
    }

    public long getTotalPages()
    {
        return totalPages;
    }

    public void setTotalPages(long totalPages)
    {
        this.totalPages = totalPages;
    }
}
