package foodmall.infra;

import foodmall.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource(collectionResourceRel="deliveryInfos", path="deliveryInfos")
public interface DeliveryInfoRepository extends PagingAndSortingRepository<DeliveryInfo, Long> {

    List<DeliveryInfo> findByOrderId(String orderId);


    
}
