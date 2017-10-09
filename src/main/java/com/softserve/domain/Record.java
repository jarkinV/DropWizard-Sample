
package com.softserve.domain;

public class Record {
    private String key;
    private String value;
    private int partition;
    private long offset;

    public Record(String key, String value, int partition, long offset) {
        super();
        this.key = key;
        this.value = value;
        this.partition = partition;
        this.offset = offset;
    }

    public Record() {
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public int getPartition() {
        return partition;
    }

    public long getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return "Record [key=" + key + ", value=" + value + ", partition=" + partition + ", offset=" + offset + "]";
    }

}
