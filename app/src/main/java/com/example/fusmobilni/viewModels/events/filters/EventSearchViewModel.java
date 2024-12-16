package com.example.fusmobilni.viewModels.events.filters;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.responses.events.EventTypeResponse;
import com.example.fusmobilni.responses.events.filter.EventPaginationResponse;
import com.example.fusmobilni.responses.events.filter.EventsPaginationResponse;
import com.example.fusmobilni.responses.location.LocationResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventSearchViewModel extends ViewModel {
    private MutableLiveData<List<EventPaginationResponse>> _events = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<Long> _totalElements = new MutableLiveData<>(0L);
    private MutableLiveData<Integer> _totalPages = new MutableLiveData<>(0);
    private MutableLiveData<String> _constraint = new MutableLiveData<>("");
    private MutableLiveData<List<EventTypeResponse>> eventTypes = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<EventTypeResponse> _eventType = new MutableLiveData<>(null);
    private MutableLiveData<Integer> _currentPage = new MutableLiveData<>(0);
    private MutableLiveData<Integer> _pageSize = new MutableLiveData<>(5);
    private MutableLiveData<LocationResponse> _location = new MutableLiveData<>(null);
    private MutableLiveData<List<LocationResponse>> _locations = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<String> _date = new MutableLiveData<>("");

    private LocalDate today = LocalDate.now();
    private LocalDate inAWeek = LocalDate.now().plusDays(7);

    public void doFilter() {
        Map<String, String> queryParams = new HashMap<>();
        if (!_constraint.getValue().isEmpty())
            queryParams.put("constraint", _constraint.getValue());
        if (_eventType.getValue() != null)
            queryParams.put("typeId", String.valueOf(_eventType.getValue().getId()));
        if (!_date.getValue().isEmpty())
            queryParams.put("date", _date.getValue());
        if (_location.getValue() != null)
            queryParams.put("city", _location.getValue().getCity());

        Call<EventsPaginationResponse> call = ClientUtils.eventsService.findFilteredAndPaginated(this._currentPage.getValue(), this._pageSize.getValue(), queryParams);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<EventsPaginationResponse> call, Response<EventsPaginationResponse> response) {
                if (response.isSuccessful()) {
                    _events.setValue(response.body().content);
                }
            }

            @Override
            public void onFailure(Call<EventsPaginationResponse> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });
    }

    public void prevPage() {
        if (_currentPage.getValue() > 0) {
            _currentPage.setValue(_currentPage.getValue() - 1);
            doFilter();
        }
    }

    public void nextPage() {
        if ((_currentPage.getValue() + 1) * _pageSize.getValue() < _totalElements.getValue()) {
            _currentPage.setValue(_currentPage.getValue() + 1);
            doFilter();
        }
    }

    public void resetPage() {
        _currentPage.setValue(0);
    }

    public void setInitialPageSize(int pageSize) {
        _pageSize.setValue(pageSize);
    }

    public MutableLiveData<List<LocationResponse>> getLocations() {
        return _locations;
    }

    public void setLocations(List<LocationResponse> _locations) {
        this._locations.setValue(_locations);
    }

    public MutableLiveData<String> getConstraint() {
        return _constraint;
    }

    public MutableLiveData<List<EventTypeResponse>> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(List<EventTypeResponse> eventTypes) {
        this.eventTypes.setValue(eventTypes);
    }

    public void resetFilters() {
        _constraint.setValue("");
        _date.setValue("");
        _currentPage.setValue(0);
        _location.setValue(null);
        _eventType.setValue(null);
        _pageSize.setValue(5);
        Call<EventsPaginationResponse> call = ClientUtils.eventsService.findFilteredAndPaginated(_currentPage.getValue(), _pageSize.getValue());
        call.enqueue(new Callback<EventsPaginationResponse>() {
            @Override
            public void onResponse(Call<EventsPaginationResponse> call, Response<EventsPaginationResponse> response) {
                if (response.isSuccessful()) {
                    _events.setValue(response.body().content);
                }
            }

            @Override
            public void onFailure(Call<EventsPaginationResponse> call, Throwable t) {

            }
        });
    }


    public void setConstraint(String _constraint) {
        this._constraint.setValue(_constraint);
        doFilter();
    }

    public MutableLiveData<Integer> getCurrentPage() {
        return _currentPage;
    }

    public void setCurrentPage(Integer _currentPage) {
        this._currentPage.setValue(_currentPage);
    }

    public MutableLiveData<String> getDate() {
        return _date;
    }

    public void setDate(String _date) {
        this._date.setValue(_date);
    }

    public MutableLiveData<List<EventPaginationResponse>> getEvents() {
        return _events;
    }

    public void setEvents(List<EventPaginationResponse> _events) {
        this._events.setValue(_events);
    }

    public MutableLiveData<EventTypeResponse> getEventType() {
        return _eventType;
    }

    public void setEventType(EventTypeResponse _eventType) {
        this._eventType.setValue(_eventType);
    }

    public MutableLiveData<LocationResponse> getLocation() {
        return _location;
    }

    public void setLocation(LocationResponse _location) {
        this._location.setValue(_location);
    }

    public MutableLiveData<Integer> getPageSize() {
        return _pageSize;
    }

    public void setPageSize(Integer _pageSize) {
        this._pageSize.setValue(_pageSize);
        doFilter();
    }

    public LocalDate getInAWeek() {
        return inAWeek;
    }

    public void setInAWeek(LocalDate inAWeek) {
        this.inAWeek = inAWeek;
    }

    public LocalDate getToday() {
        return today;
    }

    public void setToday(LocalDate today) {
        this.today = today;
    }

    public MutableLiveData<Long> getTotalElements() {
        return _totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this._totalElements.setValue(totalElements);
    }

    public MutableLiveData<Integer> getTotalPages() {
        return _totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this._totalPages.setValue(totalPages);
    }
}
