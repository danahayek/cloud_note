package com.example.cloudnoteassignment.model;

 public class note {
     String id;
     String address;
     String body;

     public note(String id,String address, String body) {
         this.id=id;
         this.address = address;
         this.body = body;
     }
//     private  note(){}
//     public note(String id, String address, String body) {
//         this.id = id;
//         this.address=address;
//         this.body=body;
//     }

     public void setId(String id) {
         this.id = id;
     }

     public String getId() {
         return id;
     }

     public String getAddress() {
         return address;
     }

     public String getBody() {
         return body;
     }
 }
