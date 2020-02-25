package hg.party.entity.party;

/**
 * 党员统计分析
 */
public class UserStatistics {
    /*总人数*/
    private int count;
    /*男性总人数*/
    private int maleCount;
    /*女性总人数*/
    private int femaleCount;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getMaleCount() {
        return maleCount;
    }

    public void setMaleCount(int maleCount) {
        this.maleCount = maleCount;
    }

    public int getFemaleCount() {
        return femaleCount;
    }

    public void setFemaleCount(int femaleCount) {
        this.femaleCount = femaleCount;
    }
}
