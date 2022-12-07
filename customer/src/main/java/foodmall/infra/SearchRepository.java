package foodmall.infra;

import foodmall.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource(collectionResourceRel="searches", path="searches")
public interface SearchRepository extends PagingAndSortingRepository<Search, Long> {

    

    
}
