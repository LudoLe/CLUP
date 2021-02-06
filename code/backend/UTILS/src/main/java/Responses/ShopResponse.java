package Responses;

import polimi.it.DL.entities.Shop;

public class ShopResponse{

    Shop shop;
    public ShopResponse(String status, Shop shop, Boolean hasContent){
        this.shop=shop;
    }
}
