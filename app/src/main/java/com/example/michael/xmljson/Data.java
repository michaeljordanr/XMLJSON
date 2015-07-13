package com.example.michael.xmljson;

public class Data {

    private int min;
    private int max;
    private int qtde;

    public Data(int min, int max, int qtde){
        this.min = min;
        this.max = max;
        this.qtde = qtde;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getQtde() {
        return qtde;
    }
}
