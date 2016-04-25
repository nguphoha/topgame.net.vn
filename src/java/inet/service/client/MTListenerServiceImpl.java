
package inet.service.client;

import inet.service.client.*;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "MTListenerServiceImpl", targetNamespace = "http://mtws/xsd")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface MTListenerServiceImpl {


    /**
     * 
     * @param content
     * @param username
     * @param dest
     * @param source
     * @param password
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "mtRequest", targetNamespace = "http://mtws/xsd", className = "inet.service.client.MtRequest")
    @ResponseWrapper(localName = "mtRequestResponse", targetNamespace = "http://mtws/xsd", className = "inet.service.client.MtRequestResponse")
    @Action(input = "http://mtws/xsd/MTListenerServiceImpl/mtRequestRequest", output = "http://mtws/xsd/MTListenerServiceImpl/mtRequestResponse")
    public String mtRequest(
        @WebParam(name = "username", targetNamespace = "http://mtws/xsd")
        String username,
        @WebParam(name = "password", targetNamespace = "http://mtws/xsd")
        String password,
        @WebParam(name = "source", targetNamespace = "http://mtws/xsd")
        String source,
        @WebParam(name = "dest", targetNamespace = "http://mtws/xsd")
        String dest,
        @WebParam(name = "content", targetNamespace = "http://mtws/xsd")
        String content);

}
