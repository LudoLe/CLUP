package Responses;

import polimi.it.DL.entities.Shop;

public class ShopResponse{
    private Shop shop;
    public ShopResponse(Shop shop){
        this.shop=shop;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
