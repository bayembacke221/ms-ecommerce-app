package sn.bmbacke.pad.eca.orderline;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;
    private OrderLineMapper mapper;
    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {

        var orderLine = mapper.toOrderLine(orderLineRequest);
        return orderLineRepository.save(orderLine).getId();
    }
}
