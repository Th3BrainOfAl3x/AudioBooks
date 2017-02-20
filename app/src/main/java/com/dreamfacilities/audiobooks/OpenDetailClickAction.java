package com.dreamfacilities.audiobooks;

import com.dreamfacilities.audiobooks.interfaces.ClickAction;

/**
 * Created by alex on 18/02/17.
 */

public class OpenDetailClickAction implements ClickAction {
    private final MainActivity mainActivity;

    public OpenDetailClickAction(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void execute(int position) {
        mainActivity.showDetail(position);
    }
}
