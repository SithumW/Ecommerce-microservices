package com.ecommerce.order.repository;




import com.ecommerce.order.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    //Find based on the User and the Product
    //CartItem findByUserAndProduct(User user, Product product);
      CartItem findByUserIdAndProductId(String userId, String productId);

      //void deleteByUserAndProduct(User user, Product product);
        void deleteByUserIdAndProductId(String userId, String productId);

      //List<CartItem> findByUser(User user);
        List<CartItem> findByUserId(String userId);

       void deleteByUserId(String userId); //delete cart items related to a particular user
}
