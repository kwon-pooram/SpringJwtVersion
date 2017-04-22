package com.clamer.utility.exception;

/**
 * Created by sungman.you on 2017. 4. 21..
 */
public class GeneralException extends RuntimeException {

    /**********************************************************************
     *
     * 컨트롤러 및 서비스 레이어에서 에러 발생시 공통적으로 throw 하는 익셉션
     *
     * 단순 메세지만 담고 있음
     *
     * 특정한 익셉션 처리의 경우 추가적인 익셉션 추가 필요 (예정)
     *
     **********************************************************************/

    public GeneralException() {
    }

    public GeneralException(String message) {
        super(message);
    }

    public GeneralException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneralException(Throwable cause) {
        super(cause);
    }

    public GeneralException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
