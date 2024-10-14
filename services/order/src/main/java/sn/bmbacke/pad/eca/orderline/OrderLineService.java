package sn.bmbacke.pad.eca.orderline;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;
    private OrderLineMapper mapper;
    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {

        var orderLine = mapper.toOrderLine(orderLineRequest);
        return orderLineRepository.save(orderLine).getId();
    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        return  orderLineRepository.findAllByOrderId(orderId).stream()
                .map(mapper::toOrderLineResponse)
                .toList();
    }
}
