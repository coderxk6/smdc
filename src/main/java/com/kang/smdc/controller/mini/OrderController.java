package com.kang.smdc.controller.mini;

import com.kang.smdc.common.result.Result;
import com.kang.smdc.controller.mini.dto.OrderCreateDTO;
import com.kang.smdc.service.OrderService;
import com.kang.smdc.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/mini/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Result<?> createOrder(@RequestBody @Valid OrderCreateDTO dto) {
        return orderService.createOrder(dto);
    }

    @PostMapping("/pay")
    public Result<?> payOrder(@RequestBody Map<String, Object> params) {
        Long orderId = Long.valueOf(params.get("orderId").toString());
        Integer payMethod = Integer.valueOf(params.get("payMethod").toString());
        return orderService.payOrder(orderId, payMethod);
    }

    @PostMapping("/cancel")
    public Result<?> cancelOrder(@RequestBody Map<String, Object> params) {
        Long orderId = Long.valueOf(params.get("orderId").toString());
        return orderService.cancelOrder(orderId);
    }

    @GetMapping("/list")
    public Result<?> listOrders(@RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer size) {
        return orderService.listOrders(page, size);
    }

    @GetMapping("/detail/{id}")
    public Result<OrderVO> orderDetail(@PathVariable Long id) {
        return orderService.getOrderDetail(id);
    }

    @GetMapping("/table/{code}")
    public Result<?> getTableInfo(@PathVariable String code) {
        return orderService.getTableInfoByCode(code);
    }
} 