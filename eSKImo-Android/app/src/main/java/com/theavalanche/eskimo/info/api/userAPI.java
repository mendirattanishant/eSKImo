

public interface userAPI {
    @GET("/users/{user}")
    public void getFeed(@Path("user") String user,Callback<userInfo> response);
}