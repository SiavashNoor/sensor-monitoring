package com.jadifans.opert;

import java.io.File;
import javax.xml.bind.*;
public class ExportHandler implements ObjectToXml{


   File file ;
   State state;

    ExportHandler(State state,File file){
        this.state = state;
        this.file = file;
    }

    @Override
    public void writeObjectToFile() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(State.class);
            Marshaller jaxMarshaller = jaxbContext.createMarshaller();
            jaxMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
            jaxMarshaller.marshal(state,file);

        } catch (JAXBException e) {
            System.out.println("Unable to handle jaxb Object to File transformation.");
            throw new RuntimeException(e);
        }
    }
}
