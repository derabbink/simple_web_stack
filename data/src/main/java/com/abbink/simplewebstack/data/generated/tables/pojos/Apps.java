/**
 * This class is generated by jOOQ
 */
package com.abbink.simplewebstack.data.generated.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Apps implements Serializable {

    private static final long serialVersionUID = 225783369;

    private Integer id;
    private String  xid;
    private String  name;
    private Integer enabled;
    private String  redirectUri;

    public Apps() {}

    public Apps(Apps value) {
        this.id = value.id;
        this.xid = value.xid;
        this.name = value.name;
        this.enabled = value.enabled;
        this.redirectUri = value.redirectUri;
    }

    public Apps(
        Integer id,
        String  xid,
        String  name,
        Integer enabled,
        String  redirectUri
    ) {
        this.id = id;
        this.xid = xid;
        this.name = name;
        this.enabled = enabled;
        this.redirectUri = redirectUri;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getXid() {
        return this.xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getRedirectUri() {
        return this.redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Apps other = (Apps) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (xid == null) {
            if (other.xid != null)
                return false;
        }
        else if (!xid.equals(other.xid))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (enabled == null) {
            if (other.enabled != null)
                return false;
        }
        else if (!enabled.equals(other.enabled))
            return false;
        if (redirectUri == null) {
            if (other.redirectUri != null)
                return false;
        }
        else if (!redirectUri.equals(other.redirectUri))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((xid == null) ? 0 : xid.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((enabled == null) ? 0 : enabled.hashCode());
        result = prime * result + ((redirectUri == null) ? 0 : redirectUri.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Apps (");

        sb.append(id);
        sb.append(", ").append(xid);
        sb.append(", ").append(name);
        sb.append(", ").append(enabled);
        sb.append(", ").append(redirectUri);

        sb.append(")");
        return sb.toString();
    }
}
