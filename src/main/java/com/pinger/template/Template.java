package com.pinger.template;

public enum Template {

    SITE_STATUS_TABLE("site_status_table.htm"),
    SITE_STATUS_MAIL("site_status_mail.htm"),
    ERROR_MAIL("error_mail.htm");

    Template(String fileName) {
        this.fileName = fileName;
    }

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
