package com.WattanArt.model.Response;

import java.util.List;

public class FAQresponseModel {
    /**
     * result : [{"ModuleID":172,"ModuleCatID":10,"ModuleHintAr":null,"ModuleMainLink":null,"ModuleImage":null,"ModuleHintEn":null,"ModuleIsActive":true,"Order":null,"Title":"TESTAR2","Descrption":"TESTAR2","Language":1,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":1,"CurrentPage":0,"RowsPerPage":10},{"ModuleID":171,"ModuleCatID":10,"ModuleHintAr":null,"ModuleMainLink":null,"ModuleImage":null,"ModuleHintEn":null,"ModuleIsActive":true,"Order":null,"Title":"TESTAR","Descrption":"TESTAR","Language":1,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":1,"CurrentPage":0,"RowsPerPage":10}]
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
         * ModuleID : 172
         * ModuleCatID : 10
         * ModuleHintAr : null
         * ModuleMainLink : null
         * ModuleImage : null
         * ModuleHintEn : null
         * ModuleIsActive : true
         * Order : null
         * Title : TESTAR2
         * Descrption : TESTAR2
         * Language : 1
         * UserIdValue : null
         * Base64 : null
         * Extension : null
         * SharedLink : null
         * PagesCount : 1
         * CurrentPage : 0
         * RowsPerPage : 10
         */

        private int ModuleID;
        private int ModuleCatID;
        private Object ModuleHintAr;
        private Object ModuleMainLink;
        private Object ModuleImage;
        private Object ModuleHintEn;
        private boolean ModuleIsActive;
        private Object Order;
        private String Title;
        private String Descrption;
        private int Language;
        private Object UserIdValue;
        private Object Base64;
        private Object Extension;
        private Object SharedLink;
        private int PagesCount;
        private int CurrentPage;
        private int RowsPerPage;

        public void setModuleID(int ModuleID) {
            this.ModuleID = ModuleID;
        }

        public void setModuleCatID(int ModuleCatID) {
            this.ModuleCatID = ModuleCatID;
        }

        public void setModuleHintAr(Object ModuleHintAr) {
            this.ModuleHintAr = ModuleHintAr;
        }

        public void setModuleMainLink(Object ModuleMainLink) {
            this.ModuleMainLink = ModuleMainLink;
        }

        public void setModuleImage(Object ModuleImage) {
            this.ModuleImage = ModuleImage;
        }

        public void setModuleHintEn(Object ModuleHintEn) {
            this.ModuleHintEn = ModuleHintEn;
        }

        public void setModuleIsActive(boolean ModuleIsActive) {
            this.ModuleIsActive = ModuleIsActive;
        }

        public void setOrder(Object Order) {
            this.Order = Order;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public void setDescrption(String Descrption) {
            this.Descrption = Descrption;
        }

        public void setLanguage(int Language) {
            this.Language = Language;
        }

        public void setUserIdValue(Object UserIdValue) {
            this.UserIdValue = UserIdValue;
        }

        public void setBase64(Object Base64) {
            this.Base64 = Base64;
        }

        public void setExtension(Object Extension) {
            this.Extension = Extension;
        }

        public void setSharedLink(Object SharedLink) {
            this.SharedLink = SharedLink;
        }

        public void setPagesCount(int PagesCount) {
            this.PagesCount = PagesCount;
        }

        public void setCurrentPage(int CurrentPage) {
            this.CurrentPage = CurrentPage;
        }

        public void setRowsPerPage(int RowsPerPage) {
            this.RowsPerPage = RowsPerPage;
        }

        public int getModuleID() {
            return ModuleID;
        }

        public int getModuleCatID() {
            return ModuleCatID;
        }

        public Object getModuleHintAr() {
            return ModuleHintAr;
        }

        public Object getModuleMainLink() {
            return ModuleMainLink;
        }

        public Object getModuleImage() {
            return ModuleImage;
        }

        public Object getModuleHintEn() {
            return ModuleHintEn;
        }

        public boolean getModuleIsActive() {
            return ModuleIsActive;
        }

        public Object getOrder() {
            return Order;
        }

        public String getTitle() {
            return Title;
        }

        public String getDescrption() {
            return Descrption;
        }

        public int getLanguage() {
            return Language;
        }

        public Object getUserIdValue() {
            return UserIdValue;
        }

        public Object getBase64() {
            return Base64;
        }

        public Object getExtension() {
            return Extension;
        }

        public Object getSharedLink() {
            return SharedLink;
        }

        public int getPagesCount() {
            return PagesCount;
        }

        public int getCurrentPage() {
            return CurrentPage;
        }

        public int getRowsPerPage() {
            return RowsPerPage;
        }
    }
}
