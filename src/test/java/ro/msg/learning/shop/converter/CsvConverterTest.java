package ro.msg.learning.shop.converter;

import org.junit.jupiter.api.Test;
import ro.msg.learning.shop.dto.StockDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvConverterTest {

    @Test
    void testFromCsv() throws IOException {
        // Given
        String stockCsvData = """
                productId,productName,locationId,locationName,quantity
                33dc66a4-d73f-4402-9039-026ad75129c0,aviator,85233c0c-663c-4d03-bab9-1cd5506da089,Cluj,11
                968dc446-e4dc-4aeb-9ec1-08b329e9a758,browline,85233c0c-663c-4d03-bab9-1cd5506da089,Cluj,3""";
        InputStream inputStream = new ByteArrayInputStream(stockCsvData.getBytes());

        // When
        List<StockDTO> result = CsvConverter.fromCsv(StockDTO.class, inputStream);

        // Then
        assertEquals(2, result.size());
        assertEquals(UUID.fromString("33dc66a4-d73f-4402-9039-026ad75129c0"), result.get(0).getProductId());
        assertEquals("aviator", result.get(0).getProductName());
        assertEquals(UUID.fromString("85233c0c-663c-4d03-bab9-1cd5506da089"), result.get(0).getLocationId());
        assertEquals("Cluj", result.get(0).getLocationName());
        assertEquals(11, result.get(0).getQuantity());

    }

    @Test
    void testToCsv() throws IOException {
        // Given
        List<StockDTO> stockDTOS = Arrays.asList(
                StockDTO.builder()
                        .productId(UUID.fromString("33dc66a4-d73f-4402-9039-026ad75129c0"))
                        .productName("aviator")
                        .locationId(UUID.fromString("85233c0c-663c-4d03-bab9-1cd5506da089"))
                        .locationName("Cluj").quantity(11).build(),
                StockDTO.builder()
                        .productId(UUID.fromString("968dc446-e4dc-4aeb-9ec1-08b329e9a758"))
                        .productName("browline")
                        .locationId(UUID.fromString("85233c0c-663c-4d03-bab9-1cd5506da089"))
                        .locationName("Cluj").quantity(3).build());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // When
        CsvConverter.toCsv(StockDTO.class, stockDTOS, outputStream);

        // Then
        String csvResult = outputStream.toString();
        System.out.println(csvResult);
        assertTrue(csvResult.contains("productId,productName,locationId,locationName,quantity"));
        assertTrue(csvResult.contains("\"33dc66a4-d73f-4402-9039-026ad75129c0\",aviator,\"85233c0c-663c-4d03-bab9-1cd5506da089\",Cluj,11\n"));
        assertTrue(csvResult.contains("\"968dc446-e4dc-4aeb-9ec1-08b329e9a758\",browline,\"85233c0c-663c-4d03-bab9-1cd5506da089\",Cluj,3"));
    }

}