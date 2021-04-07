package by.alekseyshysh.task2.validator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

public class MedsNamespaceFilter extends XMLFilterImpl {

    private String requiredNamespace = "tns";

    public MedsNamespaceFilter(XMLReader parent) {
        super(parent);
    }

    @Override
    public void startElement(String arg0, String arg1, String arg2, Attributes arg3) throws SAXException {
        if(!arg0.equals(requiredNamespace)) 
            arg0 = requiredNamespace;
        super.startElement(arg0, arg1, arg2, arg3);
    }       
}