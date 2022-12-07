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

    public static OrderListRepository repository(){
        OrderListRepository orderListRepository = OrderApplication.applicationContext.getBean(OrderListRepository.class);
        return orderListRepository;
    }



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

    public static void updateStatus(OrderSync orderSync){

        /** Example 1:  new item 
        OrderList orderList = new OrderList();
        repository().save(orderList);

        */

        /** Example 2:  finding and process
        
        repository().findById(orderSync.get???()).ifPresent(orderList->{
            
            orderList // do something
            repository().save(orderList);


         });
        */

        
    }


}
