package vasclient.wsdl;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.11
 * 2014-06-19T23:42:05.971+08:00
 * Generated source version: 2.7.11
 * 
 */
@WebServiceClient(name = "VASClient", 
                  wsdlLocation = "file:/C:/source-git/rasocac-master/rasocac-master/sps-emlpp-api/src/main/resources/sps-emlpp-api/wsdl/emlpp-api.xml",
                  targetNamespace = "urn:VASClient/wsdl") 
public class VASClient extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("urn:VASClient/wsdl", "VASClient");
    public final static QName VASClientSEIPort = new QName("urn:VASClient/wsdl", "VASClientSEIPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/C:/source-git/rasocac-master/rasocac-master/sps-emlpp-api/src/main/resources/sps-emlpp-api/wsdl/emlpp-api.xml");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(VASClient.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/C:/source-git/rasocac-master/rasocac-master/sps-emlpp-api/src/main/resources/sps-emlpp-api/wsdl/emlpp-api.xml");
        }
        WSDL_LOCATION = url;
    }

    public VASClient(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public VASClient(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public VASClient() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public VASClient(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public VASClient(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public VASClient(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns VASClientSEI
     */
    @WebEndpoint(name = "VASClientSEIPort")
    public VASClientSEI getVASClientSEIPort() {
        return super.getPort(VASClientSEIPort, VASClientSEI.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns VASClientSEI
     */
    @WebEndpoint(name = "VASClientSEIPort")
    public VASClientSEI getVASClientSEIPort(WebServiceFeature... features) {
        return super.getPort(VASClientSEIPort, VASClientSEI.class, features);
    }

}
