package com.example.fusmobilni.viewModels.events.filters;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.model.items.category.Category;
import com.example.fusmobilni.model.event.Event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventSearchViewModel extends ViewModel {
    private MutableLiveData<List<Event>> _allEvents = new MutableLiveData<>();
    private MutableLiveData<List<Event>> _filteredEvents = new MutableLiveData<>();
    private MutableLiveData<List<Event>> _pagedEvents = new MutableLiveData<>();
    private MutableLiveData<String> _constraint = new MutableLiveData<>("");
    private MutableLiveData<Category> _category = new MutableLiveData<>(null);
    private MutableLiveData<Integer> _currentPage = new MutableLiveData<>(0);
    private MutableLiveData<Integer> _pageSize = new MutableLiveData<>(5);
    private MutableLiveData<String> _location = new MutableLiveData<>("");
    private MutableLiveData<String> _date = new MutableLiveData<>("");

    private LocalDate today = LocalDate.now();
    private LocalDate inAWeek = LocalDate.now().plusDays(7);

    public boolean IsInThisWeek(LocalDate date) {
        return today.compareTo(date) >= 0 && inAWeek.compareTo(date) <= 0;
    }

    public void loadPage(int page) {
        if (page < 0 || page * _pageSize.getValue() > _filteredEvents.getValue().size()) {
            return;
        }

        _currentPage.setValue(page);

        int start = page * _pageSize.getValue();
        int end = start + _pageSize.getValue();
        if (end > _filteredEvents.getValue().size()) {
            end = _filteredEvents.getValue().size();
        }

        List<Event> pageCategories = _filteredEvents.getValue().subList(start, end);

        _pagedEvents.setValue(pageCategories);

    }

    public void setData(List<Event> services) {
        this._allEvents.setValue(new ArrayList<>(services));
        this._filteredEvents.setValue(new ArrayList<>(services));
        this._pagedEvents.setValue(new ArrayList<>(services));
        applyFilters();
    }


    public void applyFilters() {
        List<Event> filteredList = new ArrayList<>();

        for (Event event : _allEvents.getValue()) {
            boolean matchesConstraint = _constraint.getValue().isEmpty() || event.getTitle().toLowerCase().trim().contains(_constraint.getValue().trim().toLowerCase());
            boolean matchesLocation = _location.getValue().isEmpty() || event.getLocation().toLowerCase().trim().equals(_location.getValue().toLowerCase().trim());
            boolean matchesCategory = _category.getValue() == null || event.getCategory().toLowerCase().trim().equals(_category.getValue().getName().toLowerCase().trim());
            boolean matchesDate = _date.getValue().isEmpty() || event.getDate().toLowerCase().trim().equals(_date.getValue().toLowerCase().trim());
            if (matchesConstraint && matchesLocation && matchesCategory && matchesDate) {
                filteredList.add(event);
            }
        }
        _filteredEvents.getValue().clear();
        _filteredEvents.setValue(filteredList);
        loadPage(0);
    }


    public void resetFilters() {
        _constraint.setValue("");
        _location.setValue("");
        _date.setValue("");
        _category.setValue(null);
        applyFilters();
    }

    public void prevPage() {
        if (_currentPage.getValue() > 0) {
            loadPage(_currentPage.getValue() - 1);
        }
    }

    public void nextPage() {
        if ((_currentPage.getValue() + 1) * _pageSize.getValue() < _filteredEvents.getValue().size()) {
            loadPage(_currentPage.getValue() + 1);
        }
    }

    public MutableLiveData<List<Event>> getAllEvents() {
        return _allEvents;
    }

    public void setAllEvents(List<Event> _allEvents) {
        this._allEvents.setValue(_allEvents);
    }

    public MutableLiveData<Category> getCategory() {
        return _category;
    }

    public void setCategory(Category _category) {
        this._category.setValue(_category);
        applyFilters();
    }

    public MutableLiveData<String> getConstraint() {
        return _constraint;
    }

    public void setConstraint(String _constraint) {
        this._constraint.setValue(_constraint);
        applyFilters();
    }

    public MutableLiveData<Integer> getCurrentPage() {
        return _currentPage;
    }

    public void setCurrentPage(Integer _currentPage) {
        this._currentPage.setValue(_currentPage);
        applyFilters();
    }

    public MutableLiveData<String> getDate() {
        return _date;
    }

    public void setDate(String _date) {
        this._date.setValue(_date);
        applyFilters();
    }

    public MutableLiveData<List<Event>> getFilteredEvents() {
        return _filteredEvents;
    }

    public void setFilteredEvents(List<Event> _filteredEvents) {
        this._filteredEvents.setValue(_filteredEvents);
        applyFilters();
    }

    public MutableLiveData<String> getLocation() {
        return _location;
    }

    public void setLocation(String _location) {
        this._location.setValue(_location);
        applyFilters();
    }

    public MutableLiveData<List<Event>> getPagedEvents() {
        return _pagedEvents;
    }

    public void setPagedEvents(List<Event> _pagedEvents) {
        this._pagedEvents.setValue(_pagedEvents);
        applyFilters();
    }

    public MutableLiveData<Integer> getPageSize() {
        return _pageSize;
    }

    public void setPageSize(Integer _pageSize) {
        this._pageSize.setValue(_pageSize);
        applyFilters();
    }
}
