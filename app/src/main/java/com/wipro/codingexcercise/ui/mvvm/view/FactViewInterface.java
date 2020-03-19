package com.wipro.codingexcercise.ui.mvvm.view;

import java.util.List;

import com.wipro.codingexcercise.model.Rows;
import com.wipro.codingexcercise.base.BaseInterface;

public interface FactViewInterface extends BaseInterface {
    void setFacts(String title, List<Rows> rowsList);
}
