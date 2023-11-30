package com.example.pantrypal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class IinFormatter {
    String dateMain;
    public IinFormatter(String iin) {
        String num = iin.substring(0,6);

        // Если числа представляют дату в формате ГГГГММДД, можно преобразовать их в дату
        if (num.length() >= 6) {
            String year = num.substring(0, 2);
            String month = num.substring(2, 4);
            String day = num.substring(4, 6);
            int ninyear = 19;
            int y = Integer.parseInt(year);
            if (y >= 0 && y <= 23) {
                ninyear = 20;
            }

            String birth = ninyear + year + "-" + month + "-" + day;

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                Date date1 = format.parse(birth);
                dateMain = format.format(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    public String getDateMain(){
        return dateMain;
    }
}
