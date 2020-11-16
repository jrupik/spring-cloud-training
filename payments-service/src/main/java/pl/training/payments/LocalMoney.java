package pl.training.payments;

import org.javamoney.moneta.FastMoney;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.Locale;

public class LocalMoney {

    public static FastMoney of(Number number) {
        return FastMoney.of(number, getLocalCurrencyUnit());
    }

    public static FastMoney zero() {
        return FastMoney.zero(getLocalCurrencyUnit());
    }

    private static CurrencyUnit getLocalCurrencyUnit() {
        var locale = Locale.getDefault();
        return Monetary.getCurrency(locale);
    }

}
