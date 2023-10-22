package com.jadifans.opert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class ImportHandler implements XmlToObject {

    File file;
   Object object;
    ImportHandler(File file){
        this.file = file;
    }

    @Override
    public Object ConvertXmlToObject() {
        try {
            JAXBContext context = JAXBContext.newInstance(State.class);
            Unmarshaller um = context.createUnmarshaller();
            object = um.unmarshal(file);
        } catch (JAXBException e) {
            System.out.println("unable to retrieve settings");
            throw new RuntimeException(e);
        }
        return object;
    }
}
