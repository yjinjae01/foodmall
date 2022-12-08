package foodmall.infra;

import foodmall.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource(collectionResourceRel="payInfos", path="payInfos")
public interface PayInfoRepository extends PagingAndSortingRepository<PayInfo, Long> {

    List<PayInfo> findByOrderId(String orderId);

    void deleteByOrderId(String orderId);
    
}
