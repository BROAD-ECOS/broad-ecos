package br.ufjf.nenc.lp.core.pl;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;

@XmlRootElement
public interface Product {

    public File getFile();
}
