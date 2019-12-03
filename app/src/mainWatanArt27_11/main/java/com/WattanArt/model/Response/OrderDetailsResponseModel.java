package com.WattanArt.model.Response;

import java.util.List;

public class OrderDetailsResponseModel {
    /**
     * result : [{"ID":1,"OrderID":7,"MainImage":"sefsser","LowResultionImage":"sdfsdf","Quantiy":2,"Set":"gg","PiecePrice":0,"GroupKey":null,"PatternID":1,"Pattern":"8x8","CreatedDate":"2018-04-08T11:32:47.597"}]
     * ISResultHasData : 1
     */

    private int ISResultHasData;
    private List<ResultEntity> result;

    public void setISResultHasData(int ISResultHasData) {
        this.ISResultHasData = ISResultHasData;
    }

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    public int getISResultHasData() {
        return ISResultHasData;
    }

    public List<ResultEntity> getResult() {
        return result;
    }

    public static class ResultEntity {
        /**
         * ID : 1
         * OrderID : 7
         * MainImage : sefsser
         * LowResultionImage : sdfsdf
         * Quantiy : 2
         * Set : gg
         * PiecePrice : 0.0
         * GroupKey : null
         * PatternID : 1
         * Pattern : 8x8
         * CreatedDate : 2018-04-08T11:32:47.597
         */

        private int ID;
        private int OrderID;
        private String MainImage;
        private String LowResultionImage;
        private int Quantiy;
        private String Set;
        private double PiecePrice;
        private Object GroupKey;
        private int PatternID;
        private String Pattern;
        private String CreatedDate;

        public void setID(int ID) {
            this.ID = ID;
        }

        public void setOrderID(int OrderID) {
            this.OrderID = OrderID;
        }

        public void setMainImage(String MainImage) {
            this.MainImage = MainImage;
        }

        public void setLowResultionImage(String LowResultionImage) {
            this.LowResultionImage = LowResultionImage;
        }

        public void setQuantiy(int Quantiy) {
            this.Quantiy = Quantiy;
        }

        public void setSet(String Set) {
            this.Set = Set;
        }

        public void setPiecePrice(double PiecePrice) {
            this.PiecePrice = PiecePrice;
        }

        public void setGroupKey(Object GroupKey) {
            this.GroupKey = GroupKey;
        }

        public void setPatternID(int PatternID) {
            this.PatternID = PatternID;
        }

        public void setPattern(String Pattern) {
            this.Pattern = Pattern;
        }

        public void setCreatedDate(String CreatedDate) {
            this.CreatedDate = CreatedDate;
        }

        public int getID() {
            return ID;
        }

        public int getOrderID() {
            return OrderID;
        }

        public String getMainImage() {
            return MainImage;
        }

        public String getLowResultionImage() {
            return LowResultionImage;
        }

        public int getQuantiy() {
            return Quantiy;
        }

        public String getSet() {
            return Set;
        }

        public double getPiecePrice() {
            return PiecePrice;
        }

        public Object getGroupKey() {
            return GroupKey;
        }

        public int getPatternID() {
            return PatternID;
        }

        public String getPattern() {
            return Pattern;
        }

        public String getCreatedDate() {
            return CreatedDate;
        }
    }
}
