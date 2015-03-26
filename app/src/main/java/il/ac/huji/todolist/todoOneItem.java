package il.ac.huji.todolist;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Reem on 24/03/2015.
 */
class TodoOneItem {
    private String _item;
    private GregorianCalendar _date;
    public TodoOneItem(String item, GregorianCalendar date){
        _date = date;
        _item = item;
    }
    public String get_text(){
        return _item;
    }
    public GregorianCalendar get_date() {
        return _date;
    }
    public String getCallNumber() {
        //TODO: if regex of numbers [0-9] and "-" is in, return it. else return null
        String regexToFind = "([Cc]all)?(\\s)*(\\+)?([0-9])[0-9\\-]*";
        Pattern findCallWordAndNumber = Pattern.compile(regexToFind);
        Matcher matcher = findCallWordAndNumber.matcher(_item);
        if (matcher.find()) {
            String call = matcher.group();
            regexToFind = "(\\+)?([0-9])[0-9\\-]*";
            Pattern findNumber = Pattern.compile(regexToFind);
            matcher = findNumber.matcher(call);
            matcher.find(); //will surely find because it's part of the earlier regex
            return matcher.group();
        } else {
            return null;
        }
    }
}
