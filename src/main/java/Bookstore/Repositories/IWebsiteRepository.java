package Bookstore.Repositories;

import Bookstore.Models.CustomerCart;
import Bookstore.Models.Website;

import java.util.UUID;

public interface IWebsiteRepository {

    Website checkCartStatus(UUID cartId, CustomerCart cart);

    void updateWebsite(Website website);

}
