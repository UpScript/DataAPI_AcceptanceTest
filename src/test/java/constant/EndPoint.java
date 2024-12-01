package constant;

public enum EndPoint {

    LOGIN("/login"),
    GET_PATIENTS("/patient");

    public final String url;

    EndPoint(String url){
        this.url = url;
    }
}
