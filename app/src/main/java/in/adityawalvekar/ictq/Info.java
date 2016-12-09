package in.adityawalvekar.ictq;

public class Info {

    public String Title;
    public String SubText;
    public String Body;
    public String url;


    public Info(){}

    public Info(String Title, String SubText, String Body, String url){
        this.Body = Body;
        this.SubText = SubText;
        this.Title = Title;
        this.url = url;
    }
}
