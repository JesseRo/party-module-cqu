package hg.util.postgres;

import hg.party.entity.partyMembers.JsonPageResponse;

import java.util.List;

public class PostgresqlPageResult<T> {
    private List<T> list;
    private int count;
    private int pageNow;

    public PostgresqlPageResult(List<T> list, int count, int pageNow) {
        this.list = list;
        this.count = count;
        this.pageNow = pageNow + 1;
    }

    public List<T> getList() {
        return this.list;
    }

    public int getPageNow() {
        return this.pageNow;
    }

    public int getCount() {
        return count;
    }
    public JsonPageResponse toJsonPageResponse(){
        return new JsonPageResponse(0,null,this.getList(),this.getCount());
    }
}
