package sn.bmbacke.pad.eca.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Integer> createOrder(
            @RequestBody @Valid OrderRequest orderRequest
    ) {
        return  ResponseEntity.ok(orderService.createOrder(orderRequest));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable("order-id") Integer id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }


}
