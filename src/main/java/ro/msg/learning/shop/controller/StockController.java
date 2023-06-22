package ro.msg.learning.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.mapper.StockMapper;
import ro.msg.learning.shop.service.StockService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping(value = "export/{locationId}", produces = "text/csv")
    public ResponseEntity<List<StockDTO>> exportStockToCsv(@PathVariable("locationId") UUID locationId) {
        return ResponseEntity.ok(stockService.getAllByLocation(locationId).stream().map(StockMapper::toDto).collect(Collectors.toList()));
    }

}
