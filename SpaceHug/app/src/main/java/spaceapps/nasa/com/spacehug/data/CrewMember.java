package spaceapps.nasa.com.spacehug.data;

/**
 * Created by Nic on 4/11/2015.
 */
public class CrewMember {
    private String _name;

    public String getCraft() {
        return _craft;
    }

    public String getName() {
        return _name;
    }

    private String _craft;

    public CrewMember(String name, String craft){
        _name = name;
        _craft = craft;
    }


}
