package party.portlet.transport.entity;

import hg.party.entity.partyMembers.JsonPageResponse;

import java.util.List;

/**
 * @author jesse
 * @Filename PageQueryResult
 * @description
 * @Version 1.0
 * @History <br/>
 * @Date: 2020/5/12</li>
 */
public class PageQueryResult<T> {
    private List<T> list;
    private int totalPage;
    private int pageNow;
    private int count;
    private int pageSize;

    public PageQueryResult(List<T> list, int count, int pageNow, int pageSize) {
        this.list = list;
        this.pageSize = pageSize;
        this.count = count;
        this.totalPage = (int)Math.ceil(Double.parseDouble(String.valueOf(count)) / Double.parseDouble(String.valueOf(pageSize)));
        this.pageNow = pageNow + 1;
    }

    public List<T> getList() {
        return this.list;
    }

    public int getPageNow() {
        return this.pageNow;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public JsonPageResponse toJsonPageResponse(){
        return new JsonPageResponse(0,null,this.getList(),this.getCount());
    }

}
