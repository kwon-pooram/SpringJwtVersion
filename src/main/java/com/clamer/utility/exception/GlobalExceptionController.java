package com.clamer.utility.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by sungman.you on 2017. 4. 21..
 */

@RestControllerAdvice
public class GlobalExceptionController {

    /**********************************************************************
     *
     * Exception 등록 클래스
     *
     **********************************************************************/


    // GeneralException 클래스 등록
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<?> generalException(GeneralException gx) {

        // GeneralException 을 던진 곳에서 getMessage(), 406 에러(NOT_ACCEPTABLE) status 반환
        return new ResponseEntity<>(gx.getMessage(), null, HttpStatus.NOT_ACCEPTABLE);
    }

}
