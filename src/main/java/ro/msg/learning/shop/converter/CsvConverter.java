package ro.msg.learning.shop.converter;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class CsvConverter {

    private CsvConverter() {

    }

    public static <T> List<T> fromCsv(Class<T> importedItemClass, InputStream csvFileInputStream) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<T> it = csvMapper
                .readerFor(importedItemClass)
                .with(csvMapper.schemaFor(importedItemClass).withHeader())
                .readValues(csvFileInputStream);
        return it.readAll();
    }

    public static <T> void toCsv(Class<T> exportedItemClass, List<T> items, OutputStream csvFileOutputStream) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        SequenceWriter writer = csvMapper
                .writerFor(exportedItemClass)
                .with(csvMapper.schemaFor(exportedItemClass).withHeader())
                .writeValues(csvFileOutputStream);
        writer.writeAll(items);
    }

}
