package viktorkhon.com.udacity_project_8_newsapp;

/**
 * Created by Viktor Khon on 8/9/2017.
 */

public class News {

    private String mTitle;
    private String mSectionName;
    private String mDatePublished;
    private String mWebUrl;

    public News(String title, String sectionName, String datePublished, String webUrl) {
        mTitle = title;
        mSectionName = sectionName;
        mDatePublished = datePublished;
        mWebUrl = webUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getDatePublished() {
        return mDatePublished;
    }

    public String getWebUrl() {
        return mWebUrl;
    }
}
