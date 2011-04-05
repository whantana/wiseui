package eu.wisebed.wiseui.server.domain;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Testbed configuration business object (BO) for Hibernate persistence.
 *
 * @author Soenke Nommensen
 */
@Entity
@Table(name = "testbed_config")
public class TestbedConfigurationBo implements Bo {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String testbedUrl;
    private String snaaEndpointUrl;
    private String rsEndpointUrl;
    private String sessionmanagementEndpointUrl;
    @ElementCollection
    @CollectionTable(name = "urn_prefix")
    private List<String> urnPrefixes;
    private boolean isFederated;
    private int testbedId;

    public TestbedConfigurationBo() {
    }

    public TestbedConfigurationBo(final String name, final String testbedUrl,
                                  final String snaaEndpointUrl, final String rsEndpointUrl,
                                  final String sessionmanagementEndpointUrl, final boolean isFederated) {
        this.name = name;
        this.testbedUrl = testbedUrl;
        this.snaaEndpointUrl = snaaEndpointUrl;
        this.rsEndpointUrl = rsEndpointUrl;
        this.sessionmanagementEndpointUrl = sessionmanagementEndpointUrl;
        this.urnPrefixes = new ArrayList<String>();
        this.isFederated = isFederated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getTestbedUrl() {
        return testbedUrl;
    }

    public void setTestbedUrl(final String testbedUrl) {
        this.testbedUrl = testbedUrl;
    }

    public String getSnaaEndpointUrl() {
        return snaaEndpointUrl;
    }

    public void setSnaaEndpointUrl(final String snaaEndpointUrl) {
        this.snaaEndpointUrl = snaaEndpointUrl;
    }

    public String getRsEndpointUrl() {
        return rsEndpointUrl;
    }

    public void setRsEndpointUrl(final String rsEndpointUrl) {
        this.rsEndpointUrl = rsEndpointUrl;
    }

    public String getSessionmanagementEndpointUrl() {
        return sessionmanagementEndpointUrl;
    }

    public void setSessionmanagementEndpointUrl(final String sessionmanagementEndpointUrl) {
        this.sessionmanagementEndpointUrl = sessionmanagementEndpointUrl;
    }

    public List<String> getUrnPrefixes() {
        return urnPrefixes;
    }

    public void setUrnPrefixes(final List<String> urnPrefixes) {
        this.urnPrefixes = urnPrefixes;
    }

    public boolean isFederated() {
        return isFederated;
    }

    public void setFederated(final boolean federated) {
        isFederated = federated;
    }

    public int getTestbedId() {
        return testbedId;
    }

    public void setTestbedId(final int testbedId) {
        this.testbedId = testbedId;
    }

    @Override
    public String toString() {
        return "TestbedConfigurationBo{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", testbedUrl='" + testbedUrl + '\''
                + ", snaaEndpointUrl='" + snaaEndpointUrl + '\''
                + ", rsEndpointUrl='" + rsEndpointUrl + '\''
                + ", sessionmanagementEndpointUrl='" + sessionmanagementEndpointUrl + '\''
                + ", urnPrefixList=" + urnPrefixes
                + ", isFederated=" + isFederated
                + ", testbedId=" + testbedId
                + '}';
    }
}
