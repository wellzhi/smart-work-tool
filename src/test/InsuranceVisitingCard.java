package test;


import java.io.Serializable;

/**
 * 保险顾问名片
 */

public class InsuranceVisitingCard implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String cover;
    private String qrcode;
    private String name;
    private String company;
    private String businessLicence;
    private String grade;
}