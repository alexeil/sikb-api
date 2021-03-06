package com.boschat.sikb;

import com.boschat.sikb.common.configuration.ResponseCode;

import java.util.Arrays;

public class SwaggerUtils {

    private SwaggerUtils() {

    }

    public static void main(String[] args) {
        Arrays.stream(ResponseCode.values()).forEach(SwaggerUtils::printResponseCode);
    }

    private static void printResponseCode(ResponseCode responseCode) {
        Short httpCode = responseCode.getCodeHttp();
        int code = responseCode.getCode();
        String message = responseCode.getErrorMessage();

        if (httpCode > 300) {
            System.out.println("- description: " + httpCode + "/" + code);
            System.out.println("  code: " + code);
            System.out.println("  message: \"" + message + "\"");
        }

    }

}
