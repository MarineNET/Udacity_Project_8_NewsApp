package viktorkhon.com.udacity_project_8_newsapp;

/**
 * Created by Viktor Khon on 8/9/2017.
 */

public class News {

    private String mTitle;
    private String mSectionName;
    private long mDatePublished;
    private String mWebUrl;

    public News(String title, String sectionName, long datePublished, String webUrl) {
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

    public long getDatePublished() {
        return mDatePublished;
    }

    public String getWebUrl() {
        return mWebUrl;
    }
}
