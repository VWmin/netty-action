package com.vwmin.nettyaction;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomProtocol implements Serializable {
    private static final long serialVersionUID = 4671171056588401542L;

    @SerializedName("Id")
    private String id;

    @SerializedName("To")
    private String to;

    /**heartbeat=1, chat=2*/
    @SerializedName("Type")
    private int type;

    @SerializedName("Content")
    private String content ;
}
