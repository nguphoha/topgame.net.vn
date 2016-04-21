/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.entities;

import inet.util.StringUtil;
import java.io.Serializable;

/**
 *
 * @author Tutl
 */
public class Seo implements Serializable {

    private String url;
    private String title;
    private String description;
    private String keyword;
    private String image;
    private String pageDescription;

//    private static final String SEO_PATTERN = "\n\t<title>~TITLE~</title>\n"
//            + "\t<meta name=\"description\" content=\"~DESCRIPTION~\"/>\n"
//            + "\t<meta name=\"keywords\" content=\"~KEYWORD~\"/>\n"
//            + "\t<meta content=\"index, follow\" name=\"robots\"/>\n"
//            + "\t<link rel=\"canonical\" href=\"~PARAM~\"/>\n"
//            + "\t<meta content=\"nhanmenh.sms.vn\" name=\"abstract\"/>\n"
//            + "\t<meta content=\"nhanmenh.sms.vn\" name=\"generator\"/>\n"
//            + "\t<meta http-equiv=\"refresh\" content=\"1200\" />\n"
//            + "\t<meta property=\"og:title\" content=\"~TITLE~\" />\n"
//            + "\t<meta property=\"og:locale\" content=\"en_US\" />\n"
//            + "\t<meta property=\"og:url\" content=\"~PARAM~\" />\n"
//            + "\t<meta property=\"og:description\" content=\"~DESCRIPTION~\" />\n"
//            + "\t<meta property=\"og:image\" content=\"~IMAGE~\" />\n"
//            + "\t<meta property=\"og:site_name\" content=\"Hệ thống phong thủy Nhân Mệnh\" />\n"
//            + "\t<meta property=\"article:publisher\" content=\"https://nhanmenh.sms.vn\" />\n"
//            + "\t<meta name=\"revisit-after\" content=\"1 days\" />\n"
//            + "\t<meta http-equiv=\"content-language\" content=\"vi\"/>\n";
    
    private static final String SEO_PATTERN = "\n\t<title>~TITLE~</title>\n"
            + "\t<meta name=\"description\" content='~DESCRIPTION~'>\n"
            + "\t<meta name='keywords' content='~KEYWORD~'/>\n"
            + "\t<meta name='news_keywords' content='~KEYWORD~'/>\n"
            + "\t<meta name=\"author\" content=\"nhanmenh.vn\">\n"
            + "\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
            + "\t<meta name='robots' content='index, follow, noodp'/>\n"
            + "\t<meta content=\"nhanmenh\" name=\"copyright\" />\n"
            + "\t<meta property=\"og:type\" content=\"Article\" />\n"
            + "\t<meta property=\"og:title\" content=\"~TITLE~\" />\n"
            + "\t<meta property=\"og:description\" content='~DESCRIPTION~' />\n"
            + "\t<meta property=\"og:image\" content='~IMAGE~'/>\n"
            + "\t<meta property=\"og:url\" content='~PARAM~' />\n"
            + "\t<meta property=\"og:site_name\" content=\"Hệ thống phong thủy - Nhân Mệnh\" />\n"
            + "\t<meta property='article:publisher' content='https://nhanmenh.vn' />\n"
            + "\t<meta property=\"og:locale\" content=\"vi_VN\" />\n"
            + "\t<meta name=\"abstract\" content=\"\" />\n"
            + "\t<meta name=\"distribution\" content=\"global\">\n"
            + "\t<meta http-equiv=\"refresh\" content=\"1200\" />\n"
            + "\t<meta name=\"revisit-after\" content=\"1 days\" />\n"
            + "\t<meta name=\"rating\" content=\"general\">\n"
            + "\t<meta name=\"language\" content=\"vietnamese\" />\n";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getResult() {
        String str = SEO_PATTERN;
        image = StringUtil.nvl(image, "");
        title = StringUtil.nvl(title, "");
        description = StringUtil.nvl(description, "");
        keyword = StringUtil.nvl(keyword, "");
        url = StringUtil.nvl(url, "");
        str = str.replaceAll("~TITLE~", title);
        str = str.replaceAll("~DESCRIPTION~", description);
        str = str.replaceAll("~KEYWORD~", keyword);
        str = str.replaceAll("~IMAGE~", image);
        str = str.replaceAll("~PARAM~", url);
        return str;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPageDescription() {
        return pageDescription;
    }

    public void setPageDescription(String pageDescription) {
        this.pageDescription = pageDescription;
    }

}
