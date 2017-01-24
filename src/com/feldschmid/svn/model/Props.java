package com.feldschmid.svn.model;

public class Props {

  private String  href                   = "";
  private String  version                = "";
  private String  author                 = "";

  // extended props, may not be set
  private String  creationDateString     = "";
  private String  lastModifiedDateString = "";
  private String  contentType            = "";
  private boolean collection             = false;

  public String getHref() {
    return href;
  }

  public void appendHref(String href) {
    if (!"\n".equals(href)) {
      this.href += href;
    }
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getVersion() {
    return version;
  }

  public void appendVersion(String version) {
    if (!"\n".equals(version)) {
      this.version += version;
    }
  }

  public String getAuthor() {
    return author;
  }

  public void appendAuthor(String author) {
    if (!"\n".equals(author)) {
      this.author += author;
    }
  }

  public String getCreationDateString() {
    return creationDateString;
  }

  public void appendCreationDateString(String creationDateString) {
    if (!"\n".equals(creationDateString)) {
      this.creationDateString += creationDateString;
    }
  }

  public String getLastModifiedDateString() {
    return lastModifiedDateString;
  }

  public void appendLastModifiedDateString(String lastModifiedDateString) {
    if (!"\n".equals(lastModifiedDateString)) {
      this.lastModifiedDateString += lastModifiedDateString;
    }
  }

  public String getContentType() {
    return contentType;
  }

  public void appendContentType(String contentType) {
    if (!"\n".equals(contentType)) {
      this.contentType += contentType;
    }
  }

  public void setCollection(boolean collection) {
    this.collection = collection;
  }

  public boolean isCollection() {
    return collection;
  }

  @Override
  public String toString() {
    return "Href: " + href + ", Version: " + version + ", Author: " + author + ", creationDateString: "
        + creationDateString + ", lastModifiedString: " + lastModifiedDateString + ", contentType: " + contentType
        + ", collection: " + collection + "\n";
  }

}
