package br.ufjf.nenc.lp.core.pl.ir;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Params {

    private List<Param> param;

    public List<Param> getParam() {
        return param;
    }

    public void setParam(List<Param> param) {
        this.param = param;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Params{");
        sb.append("param=").append(param);
        sb.append('}');
        return sb.toString();
    }
}
