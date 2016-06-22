package ru.technotrack.divlev.messenger.config;

public class ApplicationConfig {
    private ApplicationConfig() {}

    //public static final String SERVER_ADDRESS = "188.166.49.215";
    public static final String SERVER_ADDRESS = "192.168.1.24";
    public static final int SERVER_PORT = 7777;

    public static class ServerError {
        public static final int ErrOK              = 0;  // Все хорошо
        public static final int ErrAlreadyExist    = 1; // Логин или ник или канал уже заняты
        public static final int ErrInvalidPass     = 2;  // Невалидный логин или пароль
        public static final int ErrInvalidData     = 3;  // Невалидный JSON
        public static final int ErrEmptyField      = 4;  // Пустой ник, логин, пароль или имя канала
        public static final int ErrAlreadyRegister = 5;  // Пользователь уже зарегистрирован (лишний вызов register)
        public static final int ErrNeedAuth        = 6;  // Нужна авторизация
        public static final int ErrNeedRegister    = 7;  // Нужна регистрация
        public static final int ErrUserNotFound    = 8;  // По указанному UserId не найден пользователь
        public static final int ErrChannelNotFound = 9; // Не найден канал по ID
        public static final int ErrInvalidChannel  = 10; // Пользователь не в канале
    }

    public static String cid;
    public static String sid;

}
