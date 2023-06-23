package ro.msg.learning.shop.converter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class CsvHttpMessageConverter extends AbstractGenericHttpMessageConverter<Object> {

    private static final MediaType CSV_MEDIA_TYPE = new MediaType("text", "csv");

    public CsvHttpMessageConverter() {
        super(CSV_MEDIA_TYPE);
    }

    @Override
    protected void writeInternal(Object o, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        List<Object> items = (o instanceof Collection) ? new ArrayList<>((Collection<Object>) o) : Collections.singletonList(o);
        CsvConverter.toCsv((Class<Object>) items.get(0).getClass(), items, outputMessage.getBody());
    }

    @Override
    protected Object readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return CsvConverter.fromCsv(clazz, inputMessage.getBody());
    }

    @Override
    public Object read(Type type, Class contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return readInternal(contextClass, inputMessage);
    }
}
