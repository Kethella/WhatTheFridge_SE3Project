package de.hdm.se3project.backend.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/* Enum for SecurityQuestion field in Account
 * author: ag186
 */
//https://stackoverflow.com/questions/12468764/jackson-enum-serializing-and-deserializer/13368831#13368831
public enum SecurityQuestion {
    //source: https://nordvpn.com/blog/security-questions/
    //source: https://sites.google.com/site/pwordsecuritykate/home/list-of-ideas-security-questions
    @JsonProperty("Q1")
    Q1("What was the first exam you failed?"),
    @JsonProperty("Q2")
    Q2("What was the name of your first stuffed animal?"),
    @JsonProperty("Q3")
    Q3("In what city did you meet your spouse/significant other?"),
    @JsonProperty("Q4")
    Q4("What was your childhood nickname?"),
    @JsonProperty("Q5")
    Q5("What was the last name of your third grade teacher?\n");

    private String text;

    SecurityQuestion(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}