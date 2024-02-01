package com.mingink.common.core.utils.id;

import com.mingink.common.core.utils.time.SystemClock;

import java.util.Date;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/2/1 11:17
 * @description 雪花算法生成分布式序列号
 */
public class SnowFlake {
    /**
     * 起始的时间戳:2024-01-26 16:44:04，使用时此值不可修改
     */
    private final static long START_STMP = 1706258644000L;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT   = 12; //序列号占用的位数
    private final static long MACHINE_BIT    = 5;   //机器标识占用的位数
    private final static long DATACENTER_BIT = 5;//数据中心占用的位数

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM    = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE_NUM   = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT    = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT   = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;  //数据中心
    private long machineId;     //机器标识
    private long sequence = 0L; //序列号
    private long lastStmp = -1L;//上一次时间戳

    public SnowFlake(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     * 线程安全
     * 该id若使用long类型，可能在传到前端时发生精度溢出
     */
    public synchronized String nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE_NUM;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        long id = (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
        return String.valueOf(id);
    }

    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp() {
        return SystemClock.millisClock().now();
    }

    public static Long getMaxDataCeneterNum() {
        return MAX_DATACENTER_NUM;
    }

    public static Long getMaxMachineNum() {
        return MAX_MACHINE_NUM;
    }

    public static void main(String[] args) {
        System.out.println(MAX_DATACENTER_NUM);
        System.out.println(MAX_MACHINE_NUM);
        System.out.println(MAX_SEQUENCE_NUM);
        Date date = new Date();
        System.out.println(date);
        System.out.println(date.getTime());
    }
}
