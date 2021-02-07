package Responses;

import java.io.Serializable;

public class BooleanResponse implements Serializable
{
    private static final long serialVersionUID = 1L;
    private boolean bol;

    public BooleanResponse(boolean bol){
        this.bol=bol;
    }

    public boolean isBol() {
        return bol;
    }

    public void setBol(boolean bol) {
        this.bol = bol;
    }
}
