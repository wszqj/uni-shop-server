package com.wszqj.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @ClassName TimeUtils
 * @description: TODO
 * @date 2024年08月05日
 * @author: wszqj
 * @version: 1.0
 */
public class TimeUtils {

    public static final long TIME_MAX = 60 * 30;
    /**
     * 计算两个 LocalDateTime 之间的秒数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 秒数
     */
    public static long calculateSecondsBetween(LocalDateTime start, LocalDateTime end) {
        // 将 LocalDateTime 转换为 Instant
        Instant startInstant = start.atZone(ZoneId.systemDefault()).toInstant();
        Instant endInstant = end.atZone(ZoneId.systemDefault()).toInstant();

        // 计算两个 Instant 之间的秒数
        Duration duration = Duration.between(startInstant, endInstant);
        return duration.getSeconds();
    }
}
