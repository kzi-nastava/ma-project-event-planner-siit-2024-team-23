package com.example.fusmobilni.viewModels.items.pricelist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PriceListViewModel extends ViewModel {

    private MutableLiveData<Double> price = new MutableLiveData<>(0.0);
    private MutableLiveData<Double> discount = new MutableLiveData<>(0.0);

    private MutableLiveData<Long> id = new MutableLiveData<>(null);

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
}
