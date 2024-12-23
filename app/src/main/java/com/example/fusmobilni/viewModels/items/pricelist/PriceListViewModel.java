package com.example.fusmobilni.viewModels.items.pricelist;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.requests.items.pricelist.PriceListUpdateRequest;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.items.pricelist.PriceListGetResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PriceListViewModel extends ViewModel {

    private MutableLiveData<Double> price = new MutableLiveData<>(null);
    private MutableLiveData<Double> discount = new MutableLiveData<>(null);

    private MutableLiveData<Long> id = new MutableLiveData<>(null);

    public void cleanUp() {
        this.price.setValue(null);
        this.discount.setValue(null);
        this.id.setValue(null);
    }

    public MutableLiveData<Double> getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price.setValue(price);
    }

    public MutableLiveData<Double> getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount.setValue(discount);
    }

    public MutableLiveData<Long> getId() {
        return id;
    }

    public void setId(Long id) {
        this.id.setValue(id);
    }

    public void populate(PriceListGetResponse item) {
        setId(item.id);
        setPrice(item.price);
        setDiscount(item.discount);
    }

    public void submit(Context context) {
        Long spId = getUserId(context);
        if (price.getValue() == null || discount.getValue() == null || spId == null) {
            return;
        }
        PriceListUpdateRequest request = new PriceListUpdateRequest(price.getValue(), discount.getValue());
        Call<PriceListGetResponse> callback = ClientUtils.priceListService.update(spId ,id.getValue(), request);
        callback.enqueue(new Callback<PriceListGetResponse>() {
            @Override
            public void onResponse(Call<PriceListGetResponse> call, Response<PriceListGetResponse> response) {
            }

            @Override
            public void onFailure(Call<PriceListGetResponse> call, Throwable t) {

            }
        });
    }

    private Long getUserId(Context context) {
        LoginResponse user = CustomSharedPrefs.getInstance(context).getUser();
        if (user == null) {
            return null;
        }
        return user.getId();
    }
}
