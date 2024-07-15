package com.example.literalura.literalura.service;

public interface IConvertirData {
    <T> T getData(String json, Class<T> tClass);
}
