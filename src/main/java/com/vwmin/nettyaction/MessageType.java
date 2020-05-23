package com.vwmin.nettyaction;

public enum MessageType {

    /**在线列表*/
    Login(0),

    /** 表示数据类型是心跳包*/
    Heatbeat(1),

    /**表示数据类型是聊天*/
    Chat(2),

    Response(3);

    private int value;

    public int value(){
        return value;
    }

    MessageType(int value){
        this.value = value;
    }
}
