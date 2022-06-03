package com.cs5520.assignments.numad22su_nisargpatel;

import androidx.annotation.NonNull;

public enum BuyType {
    BUY_1x, BUY_10x, BUY_100x, BUY_NEXT;

    public int getCount() {
        switch (this) {
            case BUY_10x:
                return 10;
            case BUY_100x:
                return 100;
            case BUY_NEXT:
                return -1;
            case BUY_1x:
            default:
                return 1;
        }
    }
}
