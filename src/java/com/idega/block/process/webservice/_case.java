/**
 * _case.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.idega.block.process.webservice;

public class _case  implements java.io.Serializable {
    private java.lang.String id;

    private java.lang.String external_case_id;

    private java.lang.String created;

    private java.lang.String code;

    private java.lang.String modified;

    private java.lang.String status;

    private java.lang.String subject;

    private java.lang.String body;

    private com.idega.block.process.webservice.Owner owner;

    private com.idega.block.process.webservice.Handler handler;

    private com.idega.block.process.webservice.Item[] metadata;

    public _case() {
    }

    public _case(
           java.lang.String id,
           java.lang.String external_case_id,
           java.lang.String created,
           java.lang.String code,
           java.lang.String modified,
           java.lang.String status,
           java.lang.String subject,
           java.lang.String body,
           com.idega.block.process.webservice.Owner owner,
           com.idega.block.process.webservice.Handler handler,
           com.idega.block.process.webservice.Item[] metadata) {
           this.id = id;
           this.external_case_id = external_case_id;
           this.created = created;
           this.code = code;
           this.modified = modified;
           this.status = status;
           this.subject = subject;
           this.body = body;
           this.owner = owner;
           this.handler = handler;
           this.metadata = metadata;
    }


    /**
     * Gets the id value for this _case.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this _case.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the external_case_id value for this _case.
     * 
     * @return external_case_id
     */
    public java.lang.String getExternal_case_id() {
        return external_case_id;
    }


    /**
     * Sets the external_case_id value for this _case.
     * 
     * @param external_case_id
     */
    public void setExternal_case_id(java.lang.String external_case_id) {
        this.external_case_id = external_case_id;
    }


    /**
     * Gets the created value for this _case.
     * 
     * @return created
     */
    public java.lang.String getCreated() {
        return created;
    }


    /**
     * Sets the created value for this _case.
     * 
     * @param created
     */
    public void setCreated(java.lang.String created) {
        this.created = created;
    }


    /**
     * Gets the code value for this _case.
     * 
     * @return code
     */
    public java.lang.String getCode() {
        return code;
    }


    /**
     * Sets the code value for this _case.
     * 
     * @param code
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }


    /**
     * Gets the modified value for this _case.
     * 
     * @return modified
     */
    public java.lang.String getModified() {
        return modified;
    }


    /**
     * Sets the modified value for this _case.
     * 
     * @param modified
     */
    public void setModified(java.lang.String modified) {
        this.modified = modified;
    }


    /**
     * Gets the status value for this _case.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this _case.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the subject value for this _case.
     * 
     * @return subject
     */
    public java.lang.String getSubject() {
        return subject;
    }


    /**
     * Sets the subject value for this _case.
     * 
     * @param subject
     */
    public void setSubject(java.lang.String subject) {
        this.subject = subject;
    }


    /**
     * Gets the body value for this _case.
     * 
     * @return body
     */
    public java.lang.String getBody() {
        return body;
    }


    /**
     * Sets the body value for this _case.
     * 
     * @param body
     */
    public void setBody(java.lang.String body) {
        this.body = body;
    }


    /**
     * Gets the owner value for this _case.
     * 
     * @return owner
     */
    public com.idega.block.process.webservice.Owner getOwner() {
        return owner;
    }


    /**
     * Sets the owner value for this _case.
     * 
     * @param owner
     */
    public void setOwner(com.idega.block.process.webservice.Owner owner) {
        this.owner = owner;
    }


    /**
     * Gets the handler value for this _case.
     * 
     * @return handler
     */
    public com.idega.block.process.webservice.Handler getHandler() {
        return handler;
    }


    /**
     * Sets the handler value for this _case.
     * 
     * @param handler
     */
    public void setHandler(com.idega.block.process.webservice.Handler handler) {
        this.handler = handler;
    }


    /**
     * Gets the metadata value for this _case.
     * 
     * @return metadata
     */
    public com.idega.block.process.webservice.Item[] getMetadata() {
        return metadata;
    }


    /**
     * Sets the metadata value for this _case.
     * 
     * @param metadata
     */
    public void setMetadata(com.idega.block.process.webservice.Item[] metadata) {
        this.metadata = metadata;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof _case)) return false;
        _case other = (_case) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.external_case_id==null && other.getExternal_case_id()==null) || 
             (this.external_case_id!=null &&
              this.external_case_id.equals(other.getExternal_case_id()))) &&
            ((this.created==null && other.getCreated()==null) || 
             (this.created!=null &&
              this.created.equals(other.getCreated()))) &&
            ((this.code==null && other.getCode()==null) || 
             (this.code!=null &&
              this.code.equals(other.getCode()))) &&
            ((this.modified==null && other.getModified()==null) || 
             (this.modified!=null &&
              this.modified.equals(other.getModified()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.subject==null && other.getSubject()==null) || 
             (this.subject!=null &&
              this.subject.equals(other.getSubject()))) &&
            ((this.body==null && other.getBody()==null) || 
             (this.body!=null &&
              this.body.equals(other.getBody()))) &&
            ((this.owner==null && other.getOwner()==null) || 
             (this.owner!=null &&
              this.owner.equals(other.getOwner()))) &&
            ((this.handler==null && other.getHandler()==null) || 
             (this.handler!=null &&
              this.handler.equals(other.getHandler()))) &&
            ((this.metadata==null && other.getMetadata()==null) || 
             (this.metadata!=null &&
              java.util.Arrays.equals(this.metadata, other.getMetadata())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getExternal_case_id() != null) {
            _hashCode += getExternal_case_id().hashCode();
        }
        if (getCreated() != null) {
            _hashCode += getCreated().hashCode();
        }
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        if (getModified() != null) {
            _hashCode += getModified().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getSubject() != null) {
            _hashCode += getSubject().hashCode();
        }
        if (getBody() != null) {
            _hashCode += getBody().hashCode();
        }
        if (getOwner() != null) {
            _hashCode += getOwner().hashCode();
        }
        if (getHandler() != null) {
            _hashCode += getHandler().hashCode();
        }
        if (getMetadata() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMetadata());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMetadata(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(_case.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("schema.request", ">case"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("schema.request", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("external_case_id");
        elemField.setXmlName(new javax.xml.namespace.QName("schema.request", "external_case_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("created");
        elemField.setXmlName(new javax.xml.namespace.QName("schema.request", "created"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code");
        elemField.setXmlName(new javax.xml.namespace.QName("schema.request", "code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modified");
        elemField.setXmlName(new javax.xml.namespace.QName("schema.request", "modified"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("schema.request", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subject");
        elemField.setXmlName(new javax.xml.namespace.QName("schema.request", "subject"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("body");
        elemField.setXmlName(new javax.xml.namespace.QName("schema.request", "body"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("owner");
        elemField.setXmlName(new javax.xml.namespace.QName("schema.request", "owner"));
        elemField.setXmlType(new javax.xml.namespace.QName("schema.request", ">owner"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("handler");
        elemField.setXmlName(new javax.xml.namespace.QName("schema.request", "handler"));
        elemField.setXmlType(new javax.xml.namespace.QName("schema.request", ">handler"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("metadata");
        elemField.setXmlName(new javax.xml.namespace.QName("schema.request", "metadata"));
        elemField.setXmlType(new javax.xml.namespace.QName("schema.request", ">metadata"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
