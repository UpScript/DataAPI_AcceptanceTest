package api;

public enum StatusCode {
    CODE_200(200, "OK"),
    CODE_201(201, "OK"),
    CODE_400(400, "OK"),
    CODE_401(401, "OK"),
    CODE_404(404, "OK");

    public final int code;
    public final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
