package com.linkallcloud.um.domain.queue;

public class LacQueues {
    public static int QUEUE_STATUS_ACTIVE = 1;// 有效可处理（active）
    public static int QUEUE_STATUS_LOCKED = 3;// 临时被占用 （locked）
    public static int QUEUE_STATUS_CANCEL = 4;// 作废
    public static int QUEUE_STATUS_DELETED = 5;//处理完毕 标记删除（deleted）

    public static int QUEUE_STATUS_CONSUMER_NONE = 1;// 未消费
    public static int QUEUE_STATUS_CONSUMER_SUCCESS = 2;// 消费成功
    public static int QUEUE_STATUS_CONSUMER_FAILED = 3;// 消费失败，等待下次消费
    public static int QUEUE_STATUS_CONSUMER_CANCEL = 4;// 作废

    public static int QUEUE_MAX_CONSUME_COUNT = 3;// 最大消费次数

    public static String SERVICE_DATABASE_FALIURE = "SERVICE_DATABASE_FALIURE";

    public static long getNextConsumeTime(int consumeCount) {
        return getNextConsumeTime(consumeCount, 0);
    }

    public static long getNextConsumeSecond(int consumeCount) {
        return getNextConsumeTime(consumeCount, 0);
    }

    public static long getNextConsumeTime(int cousumeCount, int addInteval) {
        int secends = getNextConsumeSecond(cousumeCount, addInteval);
        return System.currentTimeMillis() + secends * 1000;
    }

    public static int getNextConsumeSecond(int cousumeCount, int addInteval) {
        if (cousumeCount == 1) {
            return addInteval + 10;
        } else if (cousumeCount == 2) {
            return addInteval + 60;
        } else if (cousumeCount == 3) {
            return addInteval + 60 * 5;
        } else if (cousumeCount == 4) {
            return addInteval + 60 * 15;
        } else if (cousumeCount == 5) {
            return addInteval + 60 * 60;
        } else if (cousumeCount == 6) {
            return addInteval + 60 * 60 * 2;
        } else if (cousumeCount == 7) {
            return addInteval + 60 * 60 * 5;
        } else {
            return addInteval + 60 * 60 * 10;
        }
    }

}
