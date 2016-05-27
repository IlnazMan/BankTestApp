package me.hg.ilnaz.bankapp.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ilnaz on 25.05.16, 3:22.
 */
public interface Api {
    @GET("/scripts/XML_bic.asp")
    Call<Banks> getBanks(
    );

    @GET("/service/api.php")
    Call<BankDetails> getBankByBic(
            @Query(value = "bic") String bic,
            @Query(value = "json") boolean json
    );
}
