package com.bolnizar.datafun;

/**
 * Created by BoldijarPaul on 19/11/2016.
 */
public abstract class Presenter<View> {
    private final View view;

    protected Presenter(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }
}
