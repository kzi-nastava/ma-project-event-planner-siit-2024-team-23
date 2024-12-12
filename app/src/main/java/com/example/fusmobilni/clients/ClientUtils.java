package com.example.fusmobilni.clients;

import com.example.fusmobilni.BuildConfig;
import com.example.fusmobilni.clients.services.categories.CategoryService;
import com.example.fusmobilni.clients.services.categories.proposals.ProposalService;
import com.example.fusmobilni.clients.services.eventTypes.EventTypeService;
import com.example.fusmobilni.clients.services.serviceOfferings.ServiceOfferingService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientUtils {

    public static final String SERVICE_API_PATH = "http://"+ BuildConfig.IP_ADDR +":8080/api/v1/";


    public static OkHttpClient test(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        return client;
    }

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVICE_API_PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .client(test())
            .build();

    public static ServiceOfferingService serviceOfferingService = retrofit.create(ServiceOfferingService.class);
    public static CategoryService categoryService = retrofit.create(CategoryService.class);
    public static EventTypeService eventTypeService = retrofit.create(EventTypeService.class);

    public static ProposalService proposalService = retrofit.create(ProposalService.class);
}
