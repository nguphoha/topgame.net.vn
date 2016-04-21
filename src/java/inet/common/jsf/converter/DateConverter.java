/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inet.common.jsf.converter;

import inet.common.log.Logger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter("inet.common.jsf.converter.DateConverter")
public class DateConverter implements Converter {

  
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Date date = FORMAT.parse(value);
            return new Timestamp(date.getTime());
        } catch (ParseException pe) {
            Logger logger = new Logger("error");
            logger.info(pe);
        }
        return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Timestamp) {
            Timestamp time = (Timestamp) value;
            return FORMAT.format(time);
        }
        return value.toString();
    }

    
}
