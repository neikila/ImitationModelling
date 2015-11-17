<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method ="html" media-type ="text/html"/>
  <xsl:template match="/">
    <html>
      <head>
        <title>People on HTML format</title>
      </head>
      <body>
        <h1>
          <center>List of persons</center>
        </h1>
        <xsl:apply-templates/>
      </body>
    </html>
  </xsl:template>
  <xsl:template match ="people">
    <xsl:apply-templates select ="person">
      <xsl:sort select ="fullName/name[@sequence='1']"/>
    </xsl:apply-templates>
  </xsl:template>
  <xsl:template match ="person">
    <xsl:apply-templates select ="fullName"/>
    <xsl:apply-templates select ="contacts/telephones"/>
    <xsl:if test ="position()=last()">
      <hr/>
      Total <xsl:value-of select ="last()"/> entries in target XML document
    </xsl:if>
  </xsl:template>
  <xsl:template match ="fullName">
    <h2>
      <xsl:apply-templates select ="name">
        <xsl:sort data-type="number" select="@sequence"/>
      </xsl:apply-templates>
    </h2>
  </xsl:template>
  <xsl:template match ="name">
    <xsl:value-of select ="."/>&#160;
  </xsl:template>
  <xsl:template match ="telephones">
    <table border="2" width="50%">
      <xsl:for-each select ="telephone">
        <tr>
          <td width="40%">
            <!--<xsl:value-of select ="@type"/>-->
            <xsl:choose>
              <xsl:when test ="@type='office'">Рабочий</xsl:when>
              <xsl:when test ="@type='mobile'">Мобильный</xsl:when>
              <xsl:when test ="@type='home'">Домашний</xsl:when>
              <xsl:when test ="@type='other'">Другой</xsl:when>
              <xsl:when test ="@type='fax'">Факс</xsl:when>
              <xsl:otherwise>Другой</xsl:otherwise>
            </xsl:choose>
          </td>
          <td width="60%">
            <xsl:value-of select ="."/>
          </td>
        </tr>
      </xsl:for-each>
    </table>
  </xsl:template>
</xsl:stylesheet> 
