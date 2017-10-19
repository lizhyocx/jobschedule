package com.lizhy.model;

import com.lizhy.BaseObject;

import java.util.List;

public class PageData<T> extends BaseObject {

    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = -547078136927625020L;

    /**
     * 数据model列表
     **/
    private List<T> dataList;
    /**
     * 总记录数
     **/
    private Integer totalNumber=0;

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }
}
