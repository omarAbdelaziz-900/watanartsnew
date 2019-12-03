package com.WattanArt.model.Response;

public class AboutUsResponseModel {


    private ResultEntity result;
    private int ISResultHasData;

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public void setISResultHasData(int ISResultHasData) {
        this.ISResultHasData = ISResultHasData;
    }

    public ResultEntity getResult() {
        return result;
    }

    public int getISResultHasData() {
        return ISResultHasData;
    }

    public static class ResultEntity {


        private int ContentID;
        private String ContentName;
        private String MissionAR;
        private Object MissionEN;
        private String VisionAR;
        private Object VisionEN;
        private String DescriptionAR;
        private String DescriptionEN;
        private int Language;
        private Object UserIdValue;
        private Object Base64;
        private Object Extension;
        private Object SharedLink;
        private int PagesCount;
        private int CurrentPage;
        private int RowsPerPage;

        public void setContentID(int ContentID) {
            this.ContentID = ContentID;
        }

        public void setContentName(String ContentName) {
            this.ContentName = ContentName;
        }

        public void setMissionAR(String MissionAR) {
            this.MissionAR = MissionAR;
        }

        public void setMissionEN(Object MissionEN) {
            this.MissionEN = MissionEN;
        }

        public void setVisionAR(String VisionAR) {
            this.VisionAR = VisionAR;
        }

        public void setVisionEN(Object VisionEN) {
            this.VisionEN = VisionEN;
        }

        public void setDescriptionAR(String DescriptionAR) {
            this.DescriptionAR = DescriptionAR;
        }

        public void setDescriptionEN(String DescriptionEN) {
            this.DescriptionEN = DescriptionEN;
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

        public int getContentID() {
            return ContentID;
        }

        public String getContentName() {
            return ContentName;
        }

        public String getMissionAR() {
            return MissionAR;
        }

        public Object getMissionEN() {
            return MissionEN;
        }

        public String getVisionAR() {
            return VisionAR;
        }

        public Object getVisionEN() {
            return VisionEN;
        }

        public String getDescriptionAR() {
            return DescriptionAR;
        }

        public String getDescriptionEN() {
            return DescriptionEN;
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
