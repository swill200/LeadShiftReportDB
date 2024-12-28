package controller;

import view.PreviousReportsView;

public class PreviousReportsController {
    private PreviousReportsView prevView;

    public PreviousReportsController(PreviousReportsView prevView) {
        this.prevView = prevView;
    }

    // This controller could handle actions like sign-off, etc.
    // Currently, it's just displaying reports passed from DateSelectionController.
}
