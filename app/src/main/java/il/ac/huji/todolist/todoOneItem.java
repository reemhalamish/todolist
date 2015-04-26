package il.ac.huji.todolist;

import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This calss represents one task in the list
 * Created by Reem on 24/03/2015.
 */
class TodoOneItem {
    private String _item;
    private GregorianCalendar _date;
    private int _id;
    public TodoOneItem(String item, GregorianCalendar date, int id){
        _date = date;
        _item = item;
        _id = id;
    }
    public String get_text(){
        return _item;
    }
    public int getId(){
        return _id;
    }
    public GregorianCalendar get_date() {
        return _date;
    }
    public String getCallNumber() {
        String regexToFind = "([Cc]all)?(\\s)*(\\+)?([0-9])[0-9\\-]*";
        Pattern findCallWordAndNumber = Pattern.compile(regexToFind);
        Matcher matcher = findCallWordAndNumber.matcher(_item);
        if (matcher.find()) {
            String call = matcher.group();
            regexToFind = "(\\+)?([0-9])[0-9\\-]*";
            Pattern findNumber = Pattern.compile(regexToFind);
            matcher = findNumber.matcher(call);
            assert (matcher.find()); //will surely find because it's part of the earlier regex
            return matcher.group();
        } else {
            return null;
        }
    }
}
