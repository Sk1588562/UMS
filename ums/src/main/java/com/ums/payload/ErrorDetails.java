package com.ums.payload;


import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetails {

    private String message;
    private Date date;
    private String description;

    //constructor
    public ErrorDetails(String message, Date date, String description) {
        this.message = message;
        this.date = date;
        this.description=description;
    }

    //getters
//   public String getMessage(){
//        return message;
//    }
//
//    public Date getDate(){
//        return date;
//    }
}
