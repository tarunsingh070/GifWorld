package tarun.example.com.gifworld.data.model;

import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("total_count")
    private int totalCount;

    private int count;

    private int offset;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
