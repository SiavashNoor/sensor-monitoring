package com.jadifans.opert;

import java.io.Serial;
import java.io.Serializable;

public class ExportHandler implements Serializable {

    @Serial
    private static final long serialVersionUID =1L;

    Object object ;

    ExportHandler(Object object){
        this.object=object;
    }

}
