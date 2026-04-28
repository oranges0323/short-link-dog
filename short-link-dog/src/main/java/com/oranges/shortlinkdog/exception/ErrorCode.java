package com.oranges.shortlinkdog.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "请求参数错误"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    EXPIRY_ERROR(50002, "链接已过期"),
    OPERATION_ERROR(50001, "操作失败");


    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
