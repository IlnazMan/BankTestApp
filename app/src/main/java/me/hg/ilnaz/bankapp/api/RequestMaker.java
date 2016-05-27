package me.hg.ilnaz.bankapp.api;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by ilnaz on 25.05.16, 3:49.
 */
public class RequestMaker<T> {
    public T makeRequest(String url, Class<T> tClass, Converter.Factory factory) {
        return new Retrofit.Builder()
                .addConverterFactory(factory)
                .baseUrl(url)
                .build()
                .create(tClass);
    }
}
