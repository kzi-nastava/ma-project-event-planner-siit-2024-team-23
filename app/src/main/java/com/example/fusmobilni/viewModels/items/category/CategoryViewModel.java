package com.example.fusmobilni.viewModels.items.category;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.requests.categories.CreateCategoryRequest;
import com.example.fusmobilni.requests.categories.GetCategoryResponse;
import com.example.fusmobilni.requests.categories.UpdateCategoryRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryViewModel extends ViewModel {

    private MutableLiveData<String> name = new MutableLiveData<>("");
    private MutableLiveData<String> description = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> isUpdating = new MutableLiveData<>(false);

    public void setName(String name) {
        this.name.setValue(name);
    }

    public void setDescription(String description) {
        this.description.setValue(description);
    }

    public void setIsUpdating(boolean isUpdating) {
        this.isUpdating.setValue(isUpdating);
    }

    public MutableLiveData<String> getName() {
        return this.name;
    }

    public MutableLiveData<String> getDescription() {
        return this.description;
    }
    public LiveData<Boolean> getIsUpdating() {return isUpdating; }

    private Long categoryId;

    public void populate(GetCategoryResponse category) {
        categoryId = category.id;
        this.name.setValue(category.name);
        this.description.setValue(category.description);
        this.isUpdating.setValue(true);
    }

    public void cleanUp() {
        this.categoryId = null;
        this.name.setValue("");
        this.description.setValue("");
        this.isUpdating.setValue(false);
    }

    public void submit() {
        if (!isUpdating.getValue()) {
            createCategory();
        } else {
            updateCategory();
        }
        cleanUp();
    }

    private void createCategory() {
        CreateCategoryRequest request = new CreateCategoryRequest(name.getValue(), description.getValue());
        Call<GetCategoryResponse> response = ClientUtils.categoryService.create(request);
        response.enqueue(new Callback<GetCategoryResponse>() {
            @Override
            public void onResponse(Call<GetCategoryResponse> call, Response<GetCategoryResponse> response) {
                Log.d("tag", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<GetCategoryResponse> call, Throwable t) {

            }
        });
    }


    private void updateCategory() {
        UpdateCategoryRequest request = new UpdateCategoryRequest(name.getValue(), description.getValue());
        Call<GetCategoryResponse> response = ClientUtils.categoryService.update(request, categoryId);
        response.enqueue(new Callback<GetCategoryResponse>() {
            @Override
            public void onResponse(Call<GetCategoryResponse> call, Response<GetCategoryResponse> response) {
                Log.d("tag", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<GetCategoryResponse> call, Throwable t) {

            }
        });
    }
}
