package foodmall.domain;

import foodmall.domain.*;
import foodmall.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class OrderSync extends AbstractEvent {

    private Long id;
    private String status;

    public OrderSync(OrderStatus aggregate){
        super(aggregate);
    }
    public OrderSync(){
        super();
    }
}
