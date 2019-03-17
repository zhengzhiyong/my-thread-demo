package com.thread.demo.demo01.masterWorker;
/**
 * @author ZhiYong
 * @date 11点37分
 * @desc master-worker并行计算demo
 */
public class Task {
    private int id;

    private String name;

    private long time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
