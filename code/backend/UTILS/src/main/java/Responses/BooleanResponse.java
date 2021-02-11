package Responses;

import java.io.Serializable;

public class BooleanResponse implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Boolean bol;

    public BooleanResponse(boolean bol){
        this.bol=bol;
    }

    public Boolean isBol() {
        return bol;
    }

    public void setBol(Boolean bol) {
        this.bol = bol;
    }
}
