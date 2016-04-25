
package inet.service.client;

import inet.service.client.*;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the inet.service.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _MtRequestResponse_QNAME = new QName("http://mtws/xsd", "mtRequestResponse");
    private final static QName _MtRequest_QNAME = new QName("http://mtws/xsd", "mtRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: inet.service.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MtRequest }
     * 
     */
    public MtRequest createMtRequest() {
        return new MtRequest();
    }

    /**
     * Create an instance of {@link MtRequestResponse }
     * 
     */
    public MtRequestResponse createMtRequestResponse() {
        return new MtRequestResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MtRequestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mtws/xsd", name = "mtRequestResponse")
    public JAXBElement<MtRequestResponse> createMtRequestResponse(MtRequestResponse value) {
        return new JAXBElement<MtRequestResponse>(_MtRequestResponse_QNAME, MtRequestResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MtRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mtws/xsd", name = "mtRequest")
    public JAXBElement<MtRequest> createMtRequest(MtRequest value) {
        return new JAXBElement<MtRequest>(_MtRequest_QNAME, MtRequest.class, null, value);
    }

}
