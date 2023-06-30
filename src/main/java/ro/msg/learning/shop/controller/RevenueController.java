package ro.msg.learning.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.dto.RevenueDTO;
import ro.msg.learning.shop.mapper.RevenueMapper;
import ro.msg.learning.shop.service.RevenueService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/revenues")
public class RevenueController {
    @Autowired
    private RevenueService revenueService;

    @GetMapping(value = "export/{date}", produces = "text/csv")
    public ResponseEntity<List<RevenueDTO>> exportRevenueToCsv(@PathVariable("date") LocalDate localDate) {
        return ResponseEntity.ok(revenueService.getAllByDate(localDate).stream().map(RevenueMapper::toDto).toList());
    }

}
