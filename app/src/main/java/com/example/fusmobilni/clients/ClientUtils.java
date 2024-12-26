package com.example.fusmobilni.clients;

import com.example.fusmobilni.BuildConfig;
import com.example.fusmobilni.clients.auth.AuthService;
import com.example.fusmobilni.clients.services.categories.CategoryService;
import com.example.fusmobilni.clients.services.categories.proposals.ProposalService;
import com.example.fusmobilni.clients.services.eventTypes.EventTypeService;
import com.example.fusmobilni.clients.services.events.EventsService;
import com.example.fusmobilni.clients.services.events.inivtations.InvitationsService;
import com.example.fusmobilni.clients.services.fastRegister.FastRegisterService;
import com.example.fusmobilni.clients.services.items.ItemsService;
import com.example.fusmobilni.clients.services.items.pricelist.PriceListService;
import com.example.fusmobilni.clients.services.items.reviews.notifications.ItemReviewNotificationsService;
import com.example.fusmobilni.clients.services.products.ProductsService;
import com.example.fusmobilni.clients.services.serviceOfferings.ServiceOfferingReservationService;
import com.example.fusmobilni.clients.services.serviceOfferings.ServiceOfferingService;
import com.example.fusmobilni.clients.users.UserService;
import com.example.fusmobilni.core.CustomSharedPrefs;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientUtils {

    public static final String SERVICE_API_PATH = "http://" + BuildConfig.IP_ADDR + ":8080/api/v1/";

    private static Retrofit retrofit;

    public static void initialize(CustomSharedPrefs sharedPrefs) {
        if (retrofit == null) {
            retrofit = createRetrofitInstance(sharedPrefs);

            serviceOfferingService = retrofit.create(ServiceOfferingService.class);
            categoryService = retrofit.create(CategoryService.class);
            eventTypeService = retrofit.create(EventTypeService.class);
            productsService = retrofit.create(ProductsService.class);
            proposalService = retrofit.create(ProposalService.class);
            eventsService = retrofit.create(EventsService.class);
            invitationsService = retrofit.create(InvitationsService.class);
            fastRegisterService = retrofit.create(FastRegisterService.class);
            authService = retrofit.create(AuthService.class);
            serviceReservationService = retrofit.create(ServiceOfferingReservationService.class);
            itemsService = retrofit.create(ItemsService.class);
            userService = retrofit.create(UserService.class);
            priceListService = retrofit.create(PriceListService.class);
            itemReviewNotificationsService = retrofit.create(ItemReviewNotificationsService.class);
        }
    }

    private static Retrofit createRetrofitInstance(CustomSharedPrefs sharedPrefs) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = getClient(sharedPrefs);

        return new Retrofit.Builder()
                .baseUrl(SERVICE_API_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    //    public static OkHttpClient test() {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(120, TimeUnit.SECONDS)
//                .readTimeout(120, TimeUnit.SECONDS)
//                .writeTimeout(120, TimeUnit.SECONDS)
//                .addInterceptor(interceptor).build();
//
//        return client;
//    }
    private static OkHttpClient getClient(CustomSharedPrefs sharedPrefs) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder().connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(new AuthInterceptor(sharedPrefs))
                .build();
    }


    public static ServiceOfferingService serviceOfferingService;
    public static CategoryService categoryService;
    public static EventTypeService eventTypeService;
    public static ProductsService productsService;
    public static ProposalService proposalService;
    public static EventsService eventsService;
    public static InvitationsService invitationsService;
    public static FastRegisterService fastRegisterService;
    public static AuthService authService;
    public static ServiceOfferingReservationService serviceReservationService;
    public static ItemsService itemsService;
    public static UserService userService;
    public static PriceListService priceListService;
    public static ItemReviewNotificationsService itemReviewNotificationsService;

}
