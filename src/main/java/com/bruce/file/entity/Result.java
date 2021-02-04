package com.bruce.file.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName file
 * @Date 2021/2/4 21:49
 * @Author Bruce
 */
@Data
@NoArgsConstructor
public class Result<T> implements Serializable {

    private static int SUCCESS_CODE = 200;

    private static int FAIL_CODE = 500;

    /**
     * 请求id
     */
    private String requestId;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 服务端处理时间 unixTime
     */
    private long time;

    /**
     * 状态码
     */
    private int code;

    /**
     * 返回的数据
     */
    private T data;


    public Result(int code, String msg, T data) {
        this.time = Instant.now().getEpochSecond();
        this.requestId = UUID.randomUUID().toString();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(FAIL_CODE, msg, null);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, "", data);
    }

    public static <T> Result<T> success() {
        return new Result<>(SUCCESS_CODE, "", null);
    }
}
