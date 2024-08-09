package com.wszqj.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName OrderIdGenerator
 * @description: TODO
 * @date 2024年08月04日
 * @author: wszqj
 * @version: 1.0
 */
public class OrderIdGenerator {

    private static final long START_TIME = 1609459200000L; // 起始时间戳（例如 2021-01-01 00:00:00）
    private static final long SEQUENCE_BITS = 12L; // 序列号的位数
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS); // 序列号最大值

    private static final AtomicLong lastTimestamp = new AtomicLong(-1L);
    private static final AtomicLong sequence = new AtomicLong(0L);

    /**
     * 生成唯一的订单ID
     *
     * @return 唯一的订单ID
     */
    public static synchronized String generateOrderId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp == lastTimestamp.get()) {
            // 同一时间戳内，序列号递增
            sequence.compareAndSet(MAX_SEQUENCE, 0);
            if (sequence.get() > MAX_SEQUENCE) {
                // 序列号溢出
                while (timestamp <= lastTimestamp.get()) {
                    timestamp = System.currentTimeMillis();
                }
                sequence.set(0);
            }
        } else {
            // 新时间戳，重置序列号
            sequence.set(0);
        }
        lastTimestamp.set(timestamp);
        return String.valueOf((timestamp - START_TIME) << SEQUENCE_BITS | sequence.get());
    }
}