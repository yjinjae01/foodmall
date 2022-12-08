package foodmall.domain;

import foodmall.domain.*;
import foodmall.infra.AbstractEvent;
import lombok.*;
import java.util.*;
@Data
@ToString
public class Delivered extends AbstractEvent {

    private Long id;
    private String address;
    private String status;
    private String orderId;

    
}


