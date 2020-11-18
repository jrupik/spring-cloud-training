package pl.training.commons;

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
        return Monetary.getCurrency(new Locale("pl", "PL"));
    }

}
