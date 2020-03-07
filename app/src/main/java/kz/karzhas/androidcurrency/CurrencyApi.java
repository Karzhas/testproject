package kz.karzhas.androidcurrency;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CurrencyApi {
    @GET("/v4/latest/{base}")
    Call<Currency> getCurrencyRate(@Path("base") String base);
}
