package com.lakala.common.exception;

public class ApplicationException extends Exception {
    /**
     * ApplicationException 由 System.Exception 类继承而来。
     * System.Exception 是所有异常类的基类。
     * ApplicationException 由用户程序引发，而不是由公共语言运行库引发。
     * 如果打算设计需要创建自己的异常的应用程序，请从 ApplicationException 类派生。
     */
    private static final long serialVersionUID = 1L;

    public ApplicationException(String message) {
        super(message);
    }
}
