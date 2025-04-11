package tech.wvs.smartstockapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tech.wvs.smartstockapp.entity.PurchaseRequestEntity;

public interface PurchaseRequestRepository extends MongoRepository<PurchaseRequestEntity, String> {
}
