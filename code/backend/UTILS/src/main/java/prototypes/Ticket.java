package prototypes;


import java.io.Serializable;
import java.util.Date;

public class Ticket implements Serializable{
    private static final long serialVersionUID = 1L;

    private int id;
        private String status;
        private Date enterTime;
        private Date exitTime;
        private Date expectedDuration;
        private Date scheduledEnteringTime;
        private Date scheduledExitingTime;
        private ShopProto shop;

        public int getId() {
                return id;
                }
        public void setId(int id) {
                this.id = id;
                }

        public String getStatus() {
                return status;
                }
        public void setStatus(String status) {
                this.status = status;
                }

        public Date getEnterTime() {
                return enterTime;
                }
        public void setEnterTime(Date enterTime) {
                this.enterTime = enterTime;
                }

        public Date getExitTime() {
                return exitTime;
                }
        public void setExitTime(Date exitTime) {
                this.exitTime = exitTime;
                }

        public Date getExpectedDuration() {
                return expectedDuration;
                }
        public void setExpectedDuration(Date expectedDuration) {
                this.expectedDuration = expectedDuration;
                }

        public Date getScheduledEnteringTime() {
                return scheduledEnteringTime;
                }
        public void setScheduledEnteringTime(Date scheduledEnteringTime) {
                this.scheduledEnteringTime = scheduledEnteringTime;
                }

        public Date getScheduledExitingTime() {
                return scheduledExitingTime;
                }
        public void setScheduledExitingTime(Date scheduledExitingTime) {
                this.scheduledExitingTime = scheduledExitingTime;
                }


        public ShopProto getShop() {
                return shop;
                }

        public void setShop(ShopProto shop) {
                this.shop = shop;
                }



}