package foodmall.domain;

import foodmall.domain.OrderPlaced;
import foodmall.OrderApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;


@Entity
@Table(name="OrderList_table")
@Data

public class OrderList  {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    private Long id;

    private String foodId;
    
    private String address;
    
    private String status;
    
    private String customerId;

    @PostPersist
    public void onPostPersist(){


        OrderPlaced orderPlaced = new OrderPlaced(this);
        orderPlaced.publishAfterCommit();

        // Get request from Payment
        //foodmall.external.Payment payment =
        //    Application.applicationContext.getBean(foodmall.external.PaymentService.class)
        //    .getPayment(/** mapping value needed */);

    }

    /**
     * 
     */
    @PreRemove
    public void onPreRemove(){

        // Get request from Payment
        foodmall.external.Payment payment =
           OrderApplication.applicationContext.getBean(foodmall.external.PaymentService.class)
           .getPayment(getId());

        if (payment == null) {
            throw new RuntimeException("It's before the payment");
        }

        if (payment.getCancel()) {
            // 이미 취소된 주문
            throw new RuntimeException("It's the payment");
        }

        if ("OrderPlace".equals(status) || "Paid".equals(status) || "OrderAccept".equals(status)) {
            // 정상 주문 취소
            //Following code causes dependency to external APIs
            // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.
            foodmall.external.CancelPaymentCommand cancelPaymentCommand = new foodmall.external.CancelPaymentCommand();
            cancelPaymentCommand.setCancel(true);
            // mappings goes here
            OrderApplication.applicationContext.getBean(foodmall.external.PaymentService.class)
                .cancelPayment(getId(), cancelPaymentCommand);

            OrderCanceled orderCanceled = new OrderCanceled(this);
            orderCanceled.publishAfterCommit();
        } else {
            // 이미 요리 시작 이후의 상태
            throw new RuntimeException("Order Status : " + status);
        }
    }    
    public static OrderListRepository repository(){
        OrderListRepository orderListRepository = OrderApplication.applicationContext.getBean(OrderListRepository.class);
        return orderListRepository;
    }


/**
    public void cancel(){
        OrderCanceled orderCanceled = new OrderCanceled(this);
        orderCanceled.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        foodmall.external.Payment payment = new foodmall.external.Payment();
        // mappings goes here
        OrderApplication.applicationContext.getBean(foodmall.external.PaymentService.class)
            .cancelPayment(payment);

    }
*/

    public static void updateStatus(OrderSync orderSync){

        /** Example 1:  new item 
        OrderList orderList = new OrderList();
        repository().save(orderList);

        */
        
        repository().findById(orderSync.getId()).ifPresent(orderList->{
            
            orderList.setStatus(orderSync.getStatus()); 
            repository().save(orderList);

         });
        
    }

    public void search(){
        
    }

}
