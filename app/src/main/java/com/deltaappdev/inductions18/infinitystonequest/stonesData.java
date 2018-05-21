package com.deltaappdev.inductions18.infinitystonequest;

public class stonesData{
    public int stoneNo;
    private int stoneImage;
    private String stoneName;
    private int stoneColour;

    //public stonesData(){}

    public stonesData(int sNo, int sId, String sN, int sC){
        stoneNo=sNo;
        stoneImage=sId;
        stoneName=sN;
        stoneColour=sC;
    }

    public int getStoneColour(){
        return stoneColour;
    }

    public String getStoneName() {
        return stoneName;
    }

    public int getStoneImage(){
        return stoneImage;
    }



}
