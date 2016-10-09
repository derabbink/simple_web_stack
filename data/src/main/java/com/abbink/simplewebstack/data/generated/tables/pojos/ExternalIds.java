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
public class ExternalIds implements Serializable {

    private static final long serialVersionUID = -1519779862;

    private String xid;
    private String type;

    public ExternalIds() {}

    public ExternalIds(ExternalIds value) {
        this.xid = value.xid;
        this.type = value.type;
    }

    public ExternalIds(
        String xid,
        String type
    ) {
        this.xid = xid;
        this.type = type;
    }

    public String getXid() {
        return this.xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ExternalIds other = (ExternalIds) obj;
        if (xid == null) {
            if (other.xid != null)
                return false;
        }
        else if (!xid.equals(other.xid))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        }
        else if (!type.equals(other.type))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((xid == null) ? 0 : xid.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ExternalIds (");

        sb.append(xid);
        sb.append(", ").append(type);

        sb.append(")");
        return sb.toString();
    }
}
