package com.project.commons;

import com.google.gson.Gson;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * describe:
 *
 * @author
 */
public class ProjectMessageConverter extends AbstractHttpMessageConverter<ResData> {
    private static final Gson GSON = new Gson();

    /**
     * Indicates whether the given class is supported by this converter.
     *
     * @param clazz the class to test for support
     * @return {@code true} if supported; {@code false} otherwise
     */
    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    /**
     * Abstract template method that reads the actual object. Invoked from {@link #read}.
     *
     * @param clazz        the type of object to return
     * @param inputMessage the HTTP input message to read from
     * @return the converted object
     * @throws IOException                     in case of I/O errors
     * @throws HttpMessageNotReadableException in case of conversion errors
     */
    @Override
    protected ResData readInternal(Class<? extends ResData> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    /**
     * Abstract template method that writes the actual body. Invoked from {@link #write}.
     *
     * @param resData       the object to write to the output message
     * @param outputMessage the HTTP output message to write to
     * @throws IOException                     in case of I/O errors
     * @throws HttpMessageNotWritableException in case of conversion errors
     */
    @Override
    protected void writeInternal(ResData resData, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        OutputStream body = outputMessage.getBody();
        body.write(GSON.toJson(resData).getBytes("utf-8"));
    }
}
