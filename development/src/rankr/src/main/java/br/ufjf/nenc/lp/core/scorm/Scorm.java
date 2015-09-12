package br.ufjf.nenc.lp.core.scorm;

import br.ufjf.nenc.lp.core.pl.Product;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;

@XmlRootElement
public class Scorm implements Product {

    private final File path;

    Scorm(File path){
        this.path = path;
    }

    @Override
    public File getFile() {
        return path;
    }
}
